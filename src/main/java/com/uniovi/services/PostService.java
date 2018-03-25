package com.uniovi.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

	public String saveImagen(MultipartFile image, Post post) {
		String url ="static/fotossubidas/" +post.getId()+"."+FilenameUtils.getExtension(image.getOriginalFilename());
		try {
			InputStream is = image.getInputStream();
			Files.copy(is, Paths.get("src/main/resources/"+url),
					 StandardCopyOption.REPLACE_EXISTING);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}
	
	
}
