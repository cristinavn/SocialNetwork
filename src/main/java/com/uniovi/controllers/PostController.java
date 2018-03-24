package com.uniovi.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Post;
import com.uniovi.entities.User;
import com.uniovi.services.PostService;
import com.uniovi.services.UsersService;

@Controller
public class PostController {

	@Autowired
	PostService postService;

	@Autowired 
	UsersService usersService;
	
	@RequestMapping(value="/post/add")
	public String getPost(Model model){
		model.addAttribute("postsList", postService.getPosts());
		return "post/add";
	}

	@RequestMapping(value="/post/add", method=RequestMethod.POST )
	public String setPost(@ModelAttribute Post post){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = usersService.getUserByEmail(email);
		post.setUser(activeUser);
		post.setDate(new Date());
		postService.addPost(post);
		return "redirect:/post/list";
	}

	@RequestMapping(value="/post/list") 
	public String getList(Model model)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = usersService.getUserByEmail(email);
		model.addAttribute("postsList", postService.getPosts(activeUser));
		return "post/list";
	}

}
