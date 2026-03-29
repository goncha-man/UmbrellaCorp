package com.goncha.umbrella.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

	@GetMapping({"", "/"})
	public String view(Model model) {
		baseAttributerForUserForm(model, new User(), TAB_LIST);
		return "user/view";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping({"", "/"})
	public String createUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			baseAttributerForUserForm(model, user, TAB_FORM);
		} else {
			try {
				userService.createUser(user);
				baseAttributerForUserForm(model, new User(), TAB_LIST);
			} catch (Exception e) {
				model.addAttribute("formErrorMessage", e.getMessage());
				baseAttributerForUserForm(model, new User(), TAB_FORM);
			}
		}
		model.addAttribute("userList", userService.getAllUser());
		model.addAttribute("roles", roleRepository.findAll());
		return "user/view";
	}

	@GetMapping("/{id}/edit")
	public String getEditUser(Model model, @PathVariable Long id) throws Exception {
		User userEdit = userService.getUserById(id);
		baseAttributerForUserForm(model, userEdit, TAB_FORM);
		model.addAttribute("editMode", "true");
		return "user/view";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public String putEditUser(@PathVariable Long id, @Valid @ModelAttribute("user") User user,
			BindingResult result, Model model) {
		user.setId(id);
		if (result.hasErrors()) {
			baseAttributerForUserForm(model, user, TAB_FORM);
			model.addAttribute("editMode", "true");
		} else {
			try {
				userService.updateUser(user);
				baseAttributerForUserForm(model, new User(), TAB_LIST);
			} catch (Exception e) {
				model.addAttribute("formErrorMessage", e.getMessage());
				baseAttributerForUserForm(model, new User(), TAB_FORM);
				model.addAttribute("editMode", "true");
			}
		}
		model.addAttribute("userList", userService.getAllUser());
		model.addAttribute("roles", roleRepository.findAll());
		return "user/view";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id) {
		Map<String, String> result = new HashMap<>();
		try {
			userService.deleteUser(id);
			result.put("status", "ok");
		} catch (Exception e) {
			result.put("status", "error");
			result.put("message", "The user could not be deleted.");
		}
		return ResponseEntity.ok(result);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/{id}/password")
	@ResponseBody
	public ResponseEntity<Map<String, String>> changePassword(
			@PathVariable Long id,
			@RequestParam String newPassword,
			@RequestParam String confirmPassword) {
		Map<String, String> result = new HashMap<>();
		try {
			userService.changePassword(id, newPassword, confirmPassword);
			result.put("status", "ok");
		} catch (Exception e) {
			result.put("status", "error");
			result.put("message", e.getMessage());
		}
		return ResponseEntity.ok(result);
	}

	private void baseAttributerForUserForm(Model model, User user, String activeTab) {
		model.addAttribute("user", user);
		model.addAttribute("userList", userService.getAllUser());
		model.addAttribute("roles", roleRepository.findAll());
		model.addAttribute(activeTab, "active");
	}
}
