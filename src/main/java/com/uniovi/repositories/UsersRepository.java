package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.User;

public interface UsersRepository extends CrudRepository<User, Long>{
	
	User findByEmail(String email);
	
	@Query("SELECT u FROM User u WHERE (LOWER(u.name) LIKE LOWER(?1) OR "
			+ "LOWER(u.email) LIKE LOWER(?1))")
	Page<User> searchByNameAndEmail(Pageable pageable, String seachtext);
	
	Page<User> findAll(Pageable pageable);

	@Query("SELECT p.enviada FROM User u join u.peticionesRecibidas p where u.email=?1")
	Page<User> getInvitationsReceibed(String email, Pageable pageable); 
}