package com.tweetapp.service;

import java.util.List;

import com.tweetapp.entity.Tweet;

public interface TweetService {

	public void postTweet(Tweet tweet);

	public List<Tweet> findAllTweetsByUser(String tweetedUser);

	public List<Tweet> getAllTweets();
	
}
