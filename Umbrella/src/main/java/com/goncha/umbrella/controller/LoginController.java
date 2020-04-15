package com.goncha.umbrella.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping({"/","/login"})
	public String login() {
		return "index";
	}
	
	@GetMapping("/home")
	public String home() {
		return "home";
	}
	
//	@GetMapping("/view")
//	public String form() {
//		return "patient/view";
//	}
}
