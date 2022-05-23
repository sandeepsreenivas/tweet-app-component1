package com.tweetapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tweetapp.entity.User;

public interface UserRepository extends MongoRepository<User, String> {
	
	User findByEmail(String email);

}
