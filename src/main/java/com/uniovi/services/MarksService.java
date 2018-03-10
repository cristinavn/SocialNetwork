package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Mark;
import com.uniovi.repositories.MarksRepository;

@Service
public class MarksService {
	
	@Autowired
	private MarksRepository marksRepository;
	
	public List<Mark> getMarks(){
		List<Mark> marks = new ArrayList<Mark>();
		marksRepository.findAll().forEach(marks::add);
		return marks;
	}
	public Mark getMark(Long id){
		return marksRepository.findOne(id);
	}
	public void addMark(Mark mark){
		// Si en Id es null le asignamos el ultimo + 1 de la lista
		marksRepository.save(mark);
	}
	public void deleteMark(Long id){
		marksRepository.delete(id);
	}
}
