package com.uniovi.services;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Invitation;
import com.uniovi.entities.Post;
import com.uniovi.entities.User;

@Service
public class InsertSampleDataService {
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private InvitationService peticionService;

	@Autowired
	private RolesService rolesService;
	
	@PostConstruct
	public void init() {
		User user1 = new User("jorge@prueba.es", "Jorge Fernández");
		user1.setPassword("123456");
		user1.setRole(rolesService.getRoles()[0]);
		User user2 = new User("lucas@prueba.es", "Lucas Fernández");
		user2.setPassword("123456");
		user2.setRole(rolesService.getRoles()[0]);
		User user3 = new User("maria@prueba.es", "María Fernández");
		user3.setPassword("123456");
		user3.setRole(rolesService.getRoles()[0]);
		User user4 = new User("marta@prueba.es", "Marta Fernández");
		user4.setPassword("123456");
		user4.setRole(rolesService.getRoles()[0]);
		User user5 = new User("adrian@prueba.es", "Adrián Fernández");
		user5.setPassword("123456");
		user5.setRole(rolesService.getRoles()[0]);
		User user6 = new User("vicente@prueba.es", "Vicente Fernández");
		user6.setPassword("123456");
		user6.setRole(rolesService.getRoles()[0]);
		User user7 = new User("admin@prueba.es", "Admin");
		user7.setPassword("123456");
		user7.setRole(rolesService.getRoles()[1]);
		
		Set<Post> user1Posts = new HashSet<Post>() {
			private static final long serialVersionUID = 1L;

			{
				Post p1 = new Post("Post 1", "My first post", user1);
				p1.setDate(LocalDate.of(2018, 2, 25));
				add(p1);
				add(new Post("Post 2", "My second post", user1));
			}
		};
		user1.setPosts(user1Posts);
		
		Set<Post> user2Posts = new HashSet<Post>() {
			private static final long serialVersionUID = 1L;

			{
				add(new Post("Post 3", "My first post", user2));
				add(new Post("Post 4", "My second post", user2));
			}
		};
		user2.setPosts(user2Posts);
		
		Set<Post> user3Posts = new HashSet<Post>() {
			private static final long serialVersionUID = 1L;

			{
				add(new Post("Post 5", "My first post", user3));
				add(new Post("Post 6", "My second post", user3));
			}
		};
		user3.setPosts(user3Posts);
		usersService.addUser(user1);
		usersService.addUser(user2);
		usersService.addUser(user3);
		usersService.addUser(user4);
		usersService.addUser(user5);
		usersService.addUser(user6);
		usersService.addUser(user7);
		
		Invitation peticion = new Invitation(user1, user2);
		peticionService.addPeticion(peticion);
		user1.getEnviadas().add(peticion);
		user2.getRecibidas().add(peticion);
		

		user3.getFriends().add(user4);
		user4.getFriends().add(user3);
		usersService.save(user3);
		usersService.save(user4);
		
	}

}

