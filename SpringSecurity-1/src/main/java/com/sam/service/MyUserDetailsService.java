package com.sam.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sam.dao.MyUserDao;
import com.sam.model.MyUser;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	private MyUserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		Optional<MyUser> optional = userDao.findByUsername(username);
		
        MyUser myUser = optional.orElseThrow(()-> new UsernameNotFoundException(username));
		
		return User.builder()
				   .username(myUser.getUsername())
				   .password(myUser.getPassword())
				   .roles(myUser.getRole())
				   .build();
				
	}

}
