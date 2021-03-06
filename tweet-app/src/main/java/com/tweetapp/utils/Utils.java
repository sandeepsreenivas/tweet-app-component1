package com.tweetapp.utils;

import org.apache.commons.validator.GenericValidator;

public class Utils {
	
	public static boolean isValidDate(String date) {
		return GenericValidator.isDate(date, "dd-MM-yyyy", true);
	}
	
	public static boolean isValidEmail(String email) {
		return GenericValidator.isEmail(email);
	}
 
}
