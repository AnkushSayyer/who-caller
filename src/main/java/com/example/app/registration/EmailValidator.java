package com.example.app.registration;

import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class EmailValidator implements Predicate<String>{

	private final String emailRegex = "^(.+)@(.+)$";
	
	@Override
	public boolean test(String email) {
		Pattern pattern = Pattern.compile(emailRegex);
		return pattern.matcher(email).matches();
	}
}
