package com.uniovi.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;

@Service
public class InsertSampleDataService {
	@Autowired
	private UsersService usersService;

	@PostConstruct
	public void init() {
		User user1 = new User("jorge@prueba.es", "Jorge");
		user1.setPassword("123456");
		User user2 = new User("lucas@prueba.es", "Lucas");
		user2.setPassword("123456");
		User user3 = new User("maria@prueba.es", "María");
		user3.setPassword("123456");
		User user4 = new User("marta@prueba.es", "Marta");
		user4.setPassword("123456");
		User user5 = new User("adrian@prueba.es", "Adrián");
		user5.setPassword("123456");
		User user6 = new User("vicente@prueba.es", "Vicente");
		user6.setPassword("123456");

	/*	Set user1Marks = new HashSet<Mark>() {
			{
				add(new Mark("Nota A1", 10.0, user1));
				add(new Mark("Nota A2", 9.0, user1));
				add(new Mark("Nota A3", 7.0, user1));
				add(new Mark("Nota A4", 6.5, user1));
			}
		};
		user1.setFriends(user1Marks);
		Set user2Marks = new HashSet<Mark>() {
			{
				add(new Mark("Nota B1", 5.0, user2));
				add(new Mark("Nota B2", 4.3, user2));
				add(new Mark("Nota B3", 8.0, user2));
				add(new Mark("Nota B4", 3.5, user2));
			}
		};
		user2.setFriends(user2Marks);
		Set user3Marks = new HashSet<Mark>() {
			{
				;
				add(new Mark("Nota C1", 5.5, user3));
				add(new Mark("Nota C2", 6.6, user3));
				add(new Mark("Nota C3", 7.0, user3));
			}
		};
		user3.setFriends(user3Marks);
		Set user4Marks = new HashSet<Mark>() {
			{
				add(new Mark("Nota D1", 10.0, user4));
				add(new Mark("Nota D2", 8.0, user4));
				add(new Mark("Nota D3", 9.0, user4));
			}
		};
		user4.setFriends(user4Marks);*/
		usersService.addUser(user1);
		usersService.addUser(user2);
		usersService.addUser(user3);
		usersService.addUser(user4);
		usersService.addUser(user5);
		usersService.addUser(user6);
	}

}

