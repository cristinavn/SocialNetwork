package com.uniovi.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.services.UsersService;

@Controller
public class AdminController {
	
	@Autowired
	private UsersService usersService;


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
	

	@RequestMapping(value = "/admin/edit/update")
	public String update(Model model) {
		model.addAttribute("usersList", usersService.getUsers());
		return "/admin/edit :: tableUsers";
	}

	@RequestMapping(value = "/admin/edit/{id}/delete")
	public String delete(@PathVariable Long id) {
		usersService.deleteUser(id);
		return "redirect:/admin/edit";
	}
}
