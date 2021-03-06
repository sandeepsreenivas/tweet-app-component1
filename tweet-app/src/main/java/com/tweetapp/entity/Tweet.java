package com.tweetapp.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tweets")
public class Tweet {

	@Id
	private String id;
	private String tweetMessage;
	private String tweetedUser;

	public Tweet() {
		super();
	}

	public Tweet(String id, String tweetMessage, String tweetedUser) {
		super();
		this.id = id;
		this.tweetMessage = tweetMessage;
		this.tweetedUser = tweetedUser;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTweetMessage() {
		return tweetMessage;
	}

	public void setTweetMessage(String tweetMessage) {
		this.tweetMessage = tweetMessage;
	}

	public String getTweetedUser() {
		return tweetedUser;
	}

	public void setTweetedUser(String tweetedUser) {
		this.tweetedUser = tweetedUser;
	}

}
