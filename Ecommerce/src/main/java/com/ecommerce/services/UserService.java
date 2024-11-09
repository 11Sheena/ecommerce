package com.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.entities.User;
import com.ecommerce.repo.UserRepo;


@Service
public class UserService implements UserDetailsService{
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		UserDetails obj = (UserDetails)userRepo.findByEmail(username).get();
		return obj;
	}
	
	public User getById(Integer userid) {
		return userRepo.findById(userid).get();
	}
}
