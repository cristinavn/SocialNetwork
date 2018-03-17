package com.uniovi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Post;
import com.uniovi.services.PostService;

@Controller
public class PostController {

	@Autowired
	PostService postService;
	
	@RequestMapping(value="/post/add")
	public String getPost(Model model){
		model.addAttribute("usersList", postService.getPosts());
		return "mark/add";
	}
	
	@RequestMapping(value="/post/add", method=RequestMethod.POST )
	public String setPost(@ModelAttribute Post post){
		postService.addPost(post);
		return "redirect:/home";
	}
	
}
