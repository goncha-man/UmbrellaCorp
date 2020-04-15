package com.goncha.umbrella.service;

import javax.validation.Valid;

import com.goncha.umbrella.entity.User;

public interface UserService {
	
	public Iterable<User> getAllUser();

	public User createUser(@Valid User user) throws Exception;
	
	public User getUserById(Long id) throws Exception;

	public User updateUser(@Valid User user) throws Exception;
	
}
