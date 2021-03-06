package com.uniovi.controllers;

import java.security.Principal;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uniovi.entities.Invitation;
import com.uniovi.services.InvitationService;
import com.uniovi.services.UsersService;

@Controller
public class InvitationController {
	
	@Autowired
	UsersService usersService;
	
	@Autowired
	InvitationService invitationService;
	
	@RequestMapping("/invitation/list")
	public String getList(Model model, Pageable pageable, Principal principal){
		Page<Invitation> invitations = new PageImpl<Invitation>(new LinkedList<Invitation>());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		invitations = invitationService.getInvitationsReceibed(email,pageable);
		model.addAttribute("invitations", invitations.getContent());
		model.addAttribute("page", invitations);
		return "/invitation/list";
	}
	
	@RequestMapping("/invitation/{id}/acept")
	public String acept(Model model, @PathVariable Long id){
		Invitation invitation = invitationService.find(id);
		usersService.aceptInvitation(invitation);
		invitationService.delete(invitation);
		return "redirect:/invitation/list";
	}
	
	@RequestMapping("/invitation/list/update")
	public String updateList(Model model, Pageable pageable, Principal principal){
		Page<Invitation> invitations = new PageImpl<Invitation>(new LinkedList<Invitation>());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		invitations = invitationService.getInvitationsReceibed(email,pageable);
		model.addAttribute("invitations", invitations.getContent());
		model.addAttribute("page", invitations);
		return "invitation/list :: tableUsers";
	}

}
