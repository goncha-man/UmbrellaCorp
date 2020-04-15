package com.goncha.umbrella.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.goncha.umbrella.entity.User;
import com.goncha.umbrella.repository.RoleRepository;
import com.goncha.umbrella.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private final String TAB_FORM = "formTab";
	private final String TAB_LIST = "listTab";
	
	@Autowired
	UserService userService;
	
	@Autowired
	RoleRepository roleRepository;

	@GetMapping("/view")
	public String view(Model model) {
		baseAttributerForUserForm(model, new User(), TAB_LIST );
		return "patient/view";
	}
	
	@PostMapping("/view")
	public String createUser(@Valid @ModelAttribute("user")User user, BindingResult result, Model model) {
		if(result.hasErrors()) {
			baseAttributerForUserForm(model, user, TAB_FORM);
		}else {
			try {
				userService.createUser(user);
				baseAttributerForUserForm(model, new User(), TAB_LIST);
			} catch (Exception e) {
				model.addAttribute("formErrorMessage",  e.getMessage());
				baseAttributerForUserForm(model, new User(), TAB_FORM);
			}
		}
			model.addAttribute("userList", userService.getAllUser());
			model.addAttribute("roles", roleRepository.findAll());
		
		return "patient/view";
	}
	
	private void baseAttributerForUserForm(Model model, User user,String activeTab) {
		model.addAttribute("user", user);
		model.addAttribute("userList", userService.getAllUser());
		model.addAttribute("roles",roleRepository.findAll());
		model.addAttribute(activeTab,"active");
	}
}
