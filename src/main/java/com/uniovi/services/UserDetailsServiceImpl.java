package com.uniovi.services;

import com.uniovi.entities.User;
import com.uniovi.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Override
	public UserDetails loadUserByUsername(String dni) throws UsernameNotFoundException{
		User user = usersRepository.findByEmail(dni);

		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ESTUDIANTE"));

		return new org.springframework.security.core.userdetails.User(
				user.getEmail(), user.getPassword(), grantedAuthorities);
	}
}