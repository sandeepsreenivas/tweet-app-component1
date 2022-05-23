package com.tweetapp.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweetapp.entity.Tweet;
import com.tweetapp.repository.TweetRepository;
import com.tweetapp.service.TweetService;

@Service
public class TweetServiceImpl implements TweetService {
	
	@Autowired
	private TweetRepository tweetRepository; 
	
	@Override
	public void postTweet(Tweet tweet) {
		String id = UUID.randomUUID().toString();
		tweet.setId(id);
		tweetRepository.save(tweet);
	}

	@Override
	public List<Tweet> findAllTweetsByUser(String tweetedUser) {
		return tweetRepository.findByTweetedUser(tweetedUser);
	}

	@Override
	public List<Tweet> getAllTweets() {
		return tweetRepository.findAll();
	}

}
