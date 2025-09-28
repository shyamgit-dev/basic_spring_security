package com.sam.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@GetMapping("/users")
	public ResponseEntity<List<MyUser>> getAllUser() {
		return new ResponseEntity<List<MyUser>>(myUserDao.findAll(), HttpStatus.OK);
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<MyUser> fetchUserById(@PathVariable Long id) throws Exception {
		Optional<MyUser> optional = myUserDao.findById(id);

		MyUser user = optional.orElseThrow(() -> new Exception("User Not Found"));

		MyUser myUser = new MyUser();

		myUser.setId(id);

		myUser.setUsername(user.getUsername());

		myUser.setRole(user.getRole());

		return new ResponseEntity<MyUser>(myUser, HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<String> createUser(@RequestBody MyUser myUser) {
		myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
		Long userId = myUserDao.save(myUser).getId();
		String message = myUser.getRole() + " Registered SuccessFully Having Id " + ":" + userId;
		return new ResponseEntity<String>(message, HttpStatus.CREATED);
	}

}
