package com.goncha.umbrella.service.impl;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.goncha.umbrella.entity.User;
import com.goncha.umbrella.repository.UserRepository;
import com.goncha.umbrella.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository repository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public Iterable<User> getAllUser() {
		return repository.findAll();
	}
	
	@Override
	public User createUser(@Valid User user) throws Exception {
		if(checkUsernameAvailable(user) && checkPassworodValid(user)) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
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

	private boolean checkUsernameAvailable(User user) throws Exception{
        repository.findByUsername(user.getUsername())
                .map(usr -> {
                    throw new IllegalStateException("Username not available");
                });
        return true;
	}

	private boolean checkPassworodValid(User user) throws Exception {

		if(user.getConfirmPassword() == null || user.getConfirmPassword().isEmpty()) 
			throw new Exception("Confirm password can not be empty");
		
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
		user.setPassword(passwordEncoder.encode(newPassword));
		repository.save(user);
	}

	protected void mapUser(User from, User to) {
		to.setUsername(from.getUsername());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setEmail(from.getEmail());
		to.setEnabled(from.getEnabled());
		to.setRole(from.getRole());
	}
	
}
