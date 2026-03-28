package com.goncha.umbrella.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.goncha.umbrella.entity.User;
import com.goncha.umbrella.repository.RoleRepository;
import com.goncha.umbrella.service.UserService;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleRepository roleRepository;

	@GetMapping({"/", "/login"})
	public String login() {
		return "index";
	}

	@GetMapping("/home")
	public String home(Model model) {
		List<User> users = new ArrayList<>();
		userService.getAllUser().forEach(users::add);

		model.addAttribute("userCount", users.size());
		model.addAttribute("roleCount", roleRepository.count());
		model.addAttribute("recentUsers", users.subList(0, Math.min(5, users.size())));
		return "home";
	}
}
