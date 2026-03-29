package com.goncha.umbrella.service.impl;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goncha.umbrella.entity.User;
import com.goncha.umbrella.repository.UserRepository;
import com.goncha.umbrella.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository repository;

	@Override
	public Iterable<User> getAllUser() {
		return repository.findAll();
	}
	
	@Override
	public User createUser(@Valid User user) throws Exception {
		if(checkUsernameAvailale(user) && checkPassworodValid(user)) {
			repository.save(user);
		}
		return null;
	}
	
	@Override
	public User updateUser(@Valid User fromUser) throws Exception {
		
		User toUser = getUserById(fromUser.getId());
		mapUser(fromUser, toUser);
		return repository.save(toUser);
	}
	
	@Override
	public User getUserById(Long id) throws Exception {
		
		return repository.findById(id).orElseThrow(()-> new Exception("El usuario no existe"));
		
	
	}
	
	@Override
	public void deleteUser(Long id) throws Exception {
		
		User user = getUserById(id);
		repository.delete(user);
		
	}

	private boolean checkUsernameAvailale(User user) throws Exception{

		Optional<User> userFound = repository.findByUsername(user.getUsername());

		if(userFound.isPresent()) {
			
			throw new Exception("Username not available");
			
		}

		return !userFound.isPresent();
	}

	private boolean checkPassworodValid(User user) throws Exception {

		if(user.getConfirmPassword() == null || user.getConfirmPassword().isEmpty()) 
			throw new Exception("Corfirm password can not be empty");
		
		boolean isValid = user.getPassword().equals(user.getConfirmPassword());
		
		if(!isValid) {
			
			throw new Exception("Password does not match!!");
			
		}
		
		return isValid;

	}
	
	@Override
	public void changePassword(Long id, String newPassword, String confirmPassword) throws Exception {
		if (newPassword == null || newPassword.isEmpty())
			throw new Exception("Password cannot be empty");
		if (!newPassword.equals(confirmPassword))
			throw new Exception("Passwords do not match");
		User user = getUserById(id);
		user.setPassword(newPassword);
		repository.save(user);
	}

	protected void mapUser(User from, User to) {
		to.setUsername(from.getUsername());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setEmail(from.getEmail());
		to.setRoles(from.getRoles());
		to.setEnabled(from.getEnabled());
	}
	
}
