package com.uniovi.controllers;

import java.security.Principal;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.uniovi.entities.Invitation;
import com.uniovi.entities.User;
import com.uniovi.services.InvitationService;
import com.uniovi.services.RolesService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.SignUpFormValidator;

@Controller
@SessionAttributes("admin")
public class UsersController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private InvitationService invitationService;
	
	@Autowired
	private SignUpFormValidator signUpFormValidator;

	@Autowired
	private RolesService rolesService;

	@RequestMapping("/user/list")
	public String getList(Model model, Pageable pageable, Principal principal, 
			@RequestParam(value = "", required=false) String searchText,HttpServletRequest request){
		if (getActiveUserRole().equals("ROLE_ADMIN") && request.getSession().getAttribute("admin") != null 
				&& (boolean) request.getSession().getAttribute("admin")) {
			return "redirect:/admin/edit";
		}
		if(request.getSession().getAttribute("admin") != null 
				&& (boolean) request.getSession().getAttribute("admin")) {
			SecurityContextHolder.clearContext();
			return "redirect:/admin/login?error";
		}
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		if (searchText != null && !searchText.isEmpty()) {
			users = usersService.searchUsersByNameAndEmail(pageable, searchText);
		} else {
			users = usersService.getUsers(pageable);
		}
		model.addAttribute("usersList", users.getContent());
		model.addAttribute("page", users);
		return "user/list";
	}

	@RequestMapping(value="/user/add")
	public String getUser(Model model){
		model.addAttribute("usersList", usersService.getUsers());
		return "user/add";
	}

	@RequestMapping(value="/user/add", method=RequestMethod.POST )
	public String setUser(@ModelAttribute User user){
		usersService.addUser(user);
		return "redirect:/user/list";
	}

	@RequestMapping("/user/delete/{id}" )
	public String delete(@PathVariable Long id){
		usersService.deleteUser(id);
		return "redirect:/user/list";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String setUser(@Validated User user, BindingResult result, Model
			model) {
		signUpFormValidator.validate(user, result);
		if (result.hasErrors()) {
			return "signup";
		}
		user.setRole(rolesService.getRoles()[0]);
		usersService.addUser(user);
		securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
		return "redirect:home";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, @RequestParam(value = "error", required = false) String error,
			HttpServletRequest request) {
		if (error != null)
			model.addAttribute("loginError", true);
		else	
			model.addAttribute("loginError", false);
		
		return "login";
	}

	@RequestMapping(value="/admin/login")
	public String login(HttpServletRequest request,Model model, 
			@RequestParam(value = "error", required = false) String error){
		HttpSession session = request.getSession(true);
		if (session.getAttribute("admin") == null) {
			session.setAttribute("admin", true);
		}
		if (error != null)
			model.addAttribute("adminLoginError", true);
		else	
			model.addAttribute("adminLoginError", false);
		return "/admin/login";
	}

	@RequestMapping(value="/admin/edit")
	public String login(Model model){
		model.addAttribute("usersList", usersService.getUsers());
		return "/admin/edit";
	}

	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Model model) {
		model.addAttribute("usersList", usersService.getUsers());
		return "home";
	}
	
	@RequestMapping(value="/user/{id}/send", method=RequestMethod.GET)
	public String setResendTrue(Model model, @PathVariable Long id){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = usersService.getUserByEmail(email);
		User enviar = usersService.getUser(id);
		Invitation peticion = new Invitation(activeUser,enviar);
		invitationService.addPeticion(peticion);
		activeUser.getEnviadas().add(peticion);
		enviar.getRecibidas().add(peticion);
		return "redirect:/user/list";
	}
	
	@RequestMapping("/user/list/update")
	public String updateList(Model model, Pageable pageable, Principal principal){
		Page<User> users = usersService.getUsers(pageable);
		model.addAttribute("usersList", users.getContent() );
		model.addAttribute("page", users);
		return "user/list :: tableUsers";
	}

	private String getActiveUserRole() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = usersService.getUserByEmail(email);
		return activeUser.getRole();
	}
}