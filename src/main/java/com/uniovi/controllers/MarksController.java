package com.uniovi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Mark;
import com.uniovi.services.MarksService;
import com.uniovi.services.UsersService;

@Controller
public class MarksController {

	@Autowired //Inyectar el servicio
	private MarksService marksService;

	@Autowired
	private UsersService usersService;

	@RequestMapping("/mark/list")
	public String getList(Model model){
		model.addAttribute("markList", marksService.getMarks() );
		return "mark/list";
	}

	@RequestMapping(value="/mark/add")
	public String getMark(Model model){
		model.addAttribute("usersList", usersService.getUsers());
		return "mark/add";
	}

	@RequestMapping(value="/mark/add", method=RequestMethod.POST )
	public String setMark(@ModelAttribute Mark mark){
		marksService.addMark(mark);
		return "redirect:/mark/list";
	}

	@RequestMapping("/mark/details/{id}")
	public String getDetail(Model model, @PathVariable Long id){
		model.addAttribute("mark", marksService.getMark(id));
		return "mark/details";
	}

	@RequestMapping("mark/delete/{id}" )
	public String deleteMark(@PathVariable Long id){
		marksService.deleteMark(id);
		return "redirect:/mark/list";
	}

	@RequestMapping(value="/mark/edit/{id}")
	public String getEdit(Model model, @PathVariable Long id){
		model.addAttribute("mark", marksService.getMark(id));
		model.addAttribute("usersList", usersService.getUsers());
		return "mark/edit";
	}

	@RequestMapping(value="/mark/edit/{id}", method=RequestMethod.POST)
	public String setEdit(Model model, @PathVariable Long id, @ModelAttribute Mark mark){
		Mark original = marksService.getMark(id);
		// modificar solo score y description
		original.setScore(mark.getScore());
		original.setDescription(mark.getDescription());
		marksService.addMark(original);
		return "redirect:/mark/details/"+id;
	}

	@RequestMapping("/mark/list/update")
	public String updateList(Model model){
		model.addAttribute("markList", marksService.getMarks() );
		return "mark/list :: tableMarks";
	}

}
