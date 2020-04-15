package com.goncha.umbrella.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.goncha.umbrella.entity.User;

@Controller
@RequestMapping("/patient")
public class PatientController {
	
	
	@GetMapping("/view")
	public String view() {
		return "patient/view";
	}
	
	@GetMapping("/form")
	public String form(Model model) {
		model.addAttribute("userForm", new User());
		
		return "patient/form";
	}
	
	@GetMapping("/list")
	public String list() {
		return "patient/list";
	}

}
