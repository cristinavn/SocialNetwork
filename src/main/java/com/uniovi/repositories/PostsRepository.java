package com.uniovi.repositories;

import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Post;

public interface PostsRepository extends CrudRepository<Post, Long>{

}
