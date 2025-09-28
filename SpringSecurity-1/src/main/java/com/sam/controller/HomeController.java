package com.sam.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
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
		model.addAttribute("title","User Page");
		model.addAttribute("username",username);
		return "admin";
	}

}
