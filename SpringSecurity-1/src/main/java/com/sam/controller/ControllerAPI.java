package com.sam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sam.dao.MyUserDao;
import com.sam.model.MyUser;

@RestController
@RequestMapping("/api")
public class ControllerAPI {
	
	@Autowired
	private MyUserDao myUserDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/register")
	public ResponseEntity<String> createUser(@RequestBody MyUser myUser)
	{
		myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
		Long userId = myUserDao.save(myUser).getId();
		String message = myUser.getRole()+" Registered SuccessFully Having Id "+":"+userId;	
		return new ResponseEntity<String>(message, HttpStatus.CREATED);
	}

}
