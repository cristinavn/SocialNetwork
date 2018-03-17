package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Post;
import com.uniovi.entities.User;
import com.uniovi.repositories.PostsRepository;

@Service
public class PostService {

	@Autowired
	PostsRepository postsRepository;
	
	public void addPost(Post post){
		// Si en Id es null le asignamos el ultimo + 1 de la lista
		postsRepository.save(post);
	}

	public List<Post> getPosts() {
		List<Post> posts = new ArrayList<Post>();
		postsRepository.findAll().forEach(posts::add);
		return posts;
	}

	public List<Post> getPosts(User activeUser) {
		List<Post> posts = new ArrayList<Post>();
		postsRepository.findByUser(activeUser).forEach(posts::add);
		return posts;
	}
	
	
}
