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

	private boolean checkUsernameAvailale(User user) throws Exception{

		Optional<User> userFound = repository.findByUsername(user.getUsername());

		if(userFound.isPresent()) {
			
			throw new Exception("Username not available");
			
		}

		return !userFound.isPresent();
	}

	private boolean checkPassworodValid(User user) throws Exception {

		boolean isValid = user.getPassword().equals(user.getConfirmPassword());
		
		if(!isValid) {
			
			throw new Exception("Password does not match!!");
			
		}
		
		return isValid;

	}
	
}
