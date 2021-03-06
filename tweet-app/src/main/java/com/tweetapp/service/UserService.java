package com.tweetapp.service;

import java.util.List;

import com.tweetapp.entity.User;
import com.tweetapp.model.CreateUserRequest;

public interface UserService {

	public void addUser(CreateUserRequest userRequest);
	
	public boolean isEmailExists(String email);
	
	public boolean isUserNameAndPasswordExists(String username, String password);
	
	public User findUserByEmail(String email);
	
	public List<User> getAllUsers();
	
	public void updatePassword(String email, String password);
}
