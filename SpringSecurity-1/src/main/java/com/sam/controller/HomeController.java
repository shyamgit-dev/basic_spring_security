package com.sam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.sam.dao.MyUserDao;
import com.sam.model.MyUser;

@Controller
public class HomeController {
	
	@Autowired
	private MyUserDao userDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/")
	public String index(Model model)
	{
		model.addAttribute("title","Home Page");
		model.addAttribute("message", "Welcome to our website");
		return "index";
	}
	
	@GetMapping("/user/home")
	public String handleUser(Model model,Authentication authentication)
	{
		String username = authentication.getName();
		model.addAttribute("title","User Page");
		model.addAttribute("username",username);
		return "user";
	}
	
	@GetMapping("/admin/home")
	public String handleAdmin(Model model,Authentication authentication)
	{
		String username = authentication.getName();
		model.addAttribute("title","Admin Page");
		model.addAttribute("username",username);
		return "admin";
	}
	
	@GetMapping("/login")
	public String loginPage()
	{
		return "login";
	}
	
	@GetMapping("/register")
	public String registrationPage(Model model)
	{
		model.addAttribute("user", new MyUser());
		return "register";
	}
	
	@PostMapping("/register/user")
	public String registerUser(@ModelAttribute("user") MyUser user)
	{
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userDao.save(user);
		return "redirect:/login?success";
	}

}
