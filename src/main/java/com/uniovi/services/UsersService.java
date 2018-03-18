package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;
import com.uniovi.repositories.UsersRepository;

@Service
public class UsersService {
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		usersRepository.findAll().forEach(users::add);
		return users;
	}
	
	public User getUser(Long id) {
		return usersRepository.findOne(id);
	}
	
	public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		usersRepository.save(user);
	}
	
	public User getUserByEmail(String email) {
		return usersRepository.findByEmail(email);
	}
	
	public void deleteUser(Long id) {
		usersRepository.delete(id);
	}
	
	public Page<User> searchUsersByNameAndEmail(Pageable pageable,
			String searchText){
		searchText = "%"+searchText+"%";
		return usersRepository.searchByNameAndEmail(pageable, searchText);
	}
	
	public Page<User> getUsers(Pageable pageable){
		Page<User> marks = usersRepository.findAll(pageable);
		return marks;
	}

	public Page<User> getInvitationsReceibed(String email, Pageable pageable) {
		Page<User> invitations = usersRepository.getInvitationsReceibed(email,pageable);
		return invitations;
	}
}