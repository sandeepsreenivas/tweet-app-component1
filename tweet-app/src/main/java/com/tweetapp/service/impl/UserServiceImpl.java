package com.tweetapp.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweetapp.entity.User;
import com.tweetapp.model.CreateUserRequest;
import com.tweetapp.repository.UserRepository;
import com.tweetapp.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void addUser(CreateUserRequest userRequest) {
		try {
			User user = new User();
			String id = UUID.randomUUID().toString();
			user.setId(id);
			user.setFirstName(userRequest.getFirstName());
			user.setLastName(userRequest.getLastName());
			user.setGender(userRequest.getGender());
			user.setEmail(userRequest.getEmail());
			Date dateOfBirth = new SimpleDateFormat("dd-MM-yyyy").parse(userRequest.getDateOfBirth());
			user.setDateOfBirth(dateOfBirth);
			user.setPassword(userRequest.getPassword());
			userRepository.save(user);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public boolean isEmailExists(String email) {
		boolean exists = false;
		List<User> allUsers = userRepository.findAll();
		for (User user : allUsers) {
			if (user.getEmail().equals(email)) {
				exists = true;
				break;
			}
		}
		return exists;
	}

	@Override
	public boolean isUserNameAndPasswordExists(String username, String password) {
		boolean canLogin = false;
		List<User> allUsers = userRepository.findAll();
		for (User user : allUsers) {
			if (user.getEmail().equals(username)) {
				if (user.getPassword().equals(password)) {
					canLogin = true;
				}
			}
		}
		return canLogin;
	}

	@Override
	public User findUserByEmail(String email) {
		User user = userRepository.findByEmail(email);
		return user;
	}

	@Override
	public List<User> getAllUsers() {
		List<User> allUsers = userRepository.findAll();
		return allUsers;
	}
	
	@Override
	public void updatePassword(String email, String password) {
		User user = userRepository.findByEmail(email);
		user.setPassword(password);
		userRepository.save(user);
	}
	

}
