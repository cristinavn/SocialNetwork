package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Invitation;
import com.uniovi.entities.User;
import com.uniovi.repositories.UsersRepository;

@Service
public class UsersService {
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private static final Logger logger = LoggerFactory.getLogger(UsersService.class);
	
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
		logger.debug(String.format("Signup %s successfully!", user.getEmail()));
	}
	
	public User getUserByEmail(String email) {
		return usersRepository.findByEmail(email);
	}
	
	public void deleteUser(Long id) {
		User user = usersRepository.findOne(id);
		for(User u: user.getFriends()) {
			u.getFriends().remove(user);
			user.getFriends().remove(u);
		}
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

	public void save(User user) {
		usersRepository.save(user);
	}

	public Page<User> getFriends(String email, Pageable pageable) {
		Page<User> friends = usersRepository.getFriends(email,pageable);
		return friends;
	}

	public void aceptInvitation(Invitation invitation) {
		User usuarioEnviada = invitation.getEnviada();
		User usuarioRecibida = invitation.getRecibida();
		usuarioEnviada.getFriends().add(usuarioRecibida);
		usuarioRecibida.getFriends().add(usuarioEnviada);
		save(usuarioEnviada);
		save(usuarioRecibida);
		usuarioEnviada.getEnviadas().remove(invitation);
		usuarioRecibida.getRecibidas().remove(invitation);
	}

}