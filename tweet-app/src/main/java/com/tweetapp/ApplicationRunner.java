package com.tweetapp;

import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.tweetapp.entity.Tweet;
import com.tweetapp.entity.User;
import com.tweetapp.model.CreateUserRequest;
import com.tweetapp.service.TweetService;
import com.tweetapp.service.UserService;
import com.tweetapp.utils.Utils;

@Component
public class ApplicationRunner implements CommandLineRunner {

	private static final Scanner sc = new Scanner(System.in);

	@Autowired
	private UserService userService;

	@Autowired
	private TweetService tweetService;

	@Override
	public void run(String... args) throws Exception {
		System.out.println();
		System.out.println("Tweet app");
		System.out.println("-----------------------------------------------");
		System.out.println("1. Register");
		System.out.println("2. Login");
		System.out.println("3. Forgot Password");
		System.out.println("Enter your option : ");
		int choice = sc.nextInt();
		sc.nextLine();
		if (choice == 1) {
			registerUser();
		} else if (choice == 2) {
			loginUser();
		} else if (choice == 3) {
			forgotPassword();
		}
	}

	public void registerUser() {
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println("Enter First Name");
		String firstName = sc.nextLine();
		if (StringUtils.isBlank(firstName)) {
			System.out.println("First name cannot be empty");
			registerUser();
		} else {
			System.out.println("Enter Last Name");
			String lastName = sc.nextLine();
			System.out.println("Enter gender (M/F)");
			String gender = sc.nextLine();
			if (!gender.equals("M") && !gender.equals("F")) {
				System.out.println("Gender should be M/F");
				registerUser();
			} else {
				System.out.println("Enter Date of Birth (dd-MM-yyyy)");
				String dateOfBirth = sc.nextLine();
				if (!Utils.isValidDate(dateOfBirth)) {
					System.out.println("Enter valid date of birth");
					registerUser();
				} else {
					System.out.println("Enter e-mail");
					String email = sc.nextLine();
					if (!Utils.isValidEmail(email)) {
						System.out.println("Enter valid email");
						registerUser();
					} else {
						if (userService.isEmailExists(email)) {
							System.out.println("Email already exists !! Try again with new email");
							registerUser();
						} else {
							System.out.println("Enter password");
							String password = sc.nextLine();
							CreateUserRequest userRequest = new CreateUserRequest(firstName, lastName, gender,
									dateOfBirth, email, password);
							userService.addUser(userRequest);
							System.out.println("User Registered successfully !!");
						}
					}
				}
			}
		}
	}

	public void loginUser() {
		boolean isLoginSuccessful = false;
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println("Enter username");
		String username = sc.nextLine();
		if (!Utils.isValidEmail(username)) {
			System.out.println("Enter valid email");
			loginUser();
		} else {
			System.out.println("Enter password");
			String password = sc.nextLine();
			isLoginSuccessful = userService.isUserNameAndPasswordExists(username, password);
			if (!isLoginSuccessful) {
				System.out.println("Invalid username or password !!");
				loginUser();
			} else {
				System.out.println();
				System.out.println("Login Successful !!!");
				homeOptions(username);
			}
		}
	}

	public void homeOptions(String email) {
		System.out.println("-----------------------------------------------");
		System.out.println("1. Post a tweet");
		System.out.println("2. View my tweets");
		System.out.println("3. View all tweets");
		System.out.println("4. View all users");
		System.out.println("5. Reset password");
		System.out.println("6. Logout");
		System.out.println("Enter your option : ");
		int option = sc.nextInt();
		sc.nextLine();
		if (option == 1) {
			postTweet(email);
		} else if (option == 2) {
			viewMyTweets(email);
		} else if (option == 3) {
			viewAllTweets(email);
		} else if (option == 4) {
			viewAllUsers(email);
		} else if (option == 5) {
			resetPassword(email);
		} else if (option == 6) {
			try {
				logout();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void postTweet(String email) {
		System.out.println("Enter tweet");
		String tweetMessage = sc.nextLine();
		if (StringUtils.isBlank(tweetMessage)) {
			System.out.println("Tweet cannot be empty");
			postTweet(email);
		} else {
			Tweet tweet = new Tweet();
			tweet.setTweetedUser(email);
			tweet.setTweetMessage(tweetMessage);
			tweetService.postTweet(tweet);
			System.out.println("Tweet posted successfully !!");
			homeOptions(email);
		}
	}

	public void viewMyTweets(String email) {
		System.out.println("All tweets");
		System.out.println("-----------------------------------------------");
		List<Tweet> allTweets = tweetService.findAllTweetsByUser(email);
		if (allTweets.size() == 0) {
			System.out.println("No tweets found");
		} else {
			for (Tweet tweet : allTweets) {
				System.out.println(tweet.getTweetMessage());
				System.out.println();
			}
		}
		homeOptions(email);
	}

	public void viewAllTweets(String email) {
		System.out.println("All tweets");
		System.out.println("-----------------------------------------------");
		List<Tweet> allTweets = tweetService.getAllTweets();
		if (allTweets.size() == 0) {
			System.out.println("No tweets found");
		} else {
			for (Tweet tweet : allTweets) {
				User user = userService.findUserByEmail(tweet.getTweetedUser());
				String name = user.getFirstName() + " " + user.getLastName();
				System.out.println("Name : " + name + "  Tweet : " + tweet.getTweetMessage());
				System.out.println();
			}
		}
		homeOptions(email);
	}

	public void viewAllUsers(String email) {
		System.out.println("All users");
		System.out.println("-----------------------------------------------");
		List<User> allUsers = userService.getAllUsers();
		if (allUsers.size() == 0) {
			System.out.println("No users found");
		} else {
			for (int i = 0; i <= allUsers.size(); i++) {
				User user = allUsers.get(i);
				System.out.println(" User " + (i + 1));
				System.out.println(user.toString());
				System.out.println();
			}
		}
		homeOptions(email);
	}

	public void resetPassword(String email) {
		System.out.println("Reset Password");
		System.out.println("-----------------------------------------------");
		System.out.println("Enter current password");
		String currentPassword = sc.nextLine();
		System.out.println("Enter new password");
		String newPassword = sc.nextLine();
		User user = userService.findUserByEmail(email);
		if (!user.getPassword().equals(currentPassword)) {
			System.out.println("Incorrect current password");
			resetPassword(email);
		} else {
			userService.updatePassword(email, newPassword);
			System.out.println("Updated password successfully");
		}
		homeOptions(email);
	}

	public void logout() throws Exception {
		System.out.println("Logout Successful !!");
		run();
	}

	public void forgotPassword() throws Exception {
		System.out.println("Forgot password");
		System.out.println("-----------------------------------------------");
		System.out.println("Enter e-mail");
		String email = sc.nextLine();
		User user = userService.findUserByEmail(email);
		if (user == null) {
			System.out.println("User does not exist !! Enter correct email");
			forgotPassword();
		} else {
			System.out.println("Enter new password");
			String newPassword = sc.nextLine();
			userService.updatePassword(email, newPassword);
			System.out.println("Password updated successfully");
			run();
		}
	}

}
