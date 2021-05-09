package com.example.app.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegistractionController {
	
	@Autowired
	private RegistrationService registrationService;

	@PostMapping
	public String register(@RequestBody RegistrationRequest registrationRequest) {
		return registrationService.register(registrationRequest);
	}
}
