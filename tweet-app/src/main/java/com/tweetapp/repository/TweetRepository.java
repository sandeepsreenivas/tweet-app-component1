package com.tweetapp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tweetapp.entity.Tweet;

public interface TweetRepository extends MongoRepository<Tweet, String> {

	List<Tweet> findByTweetedUser(String tweetedUser);
	
}
