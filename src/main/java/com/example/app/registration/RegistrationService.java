package com.example.app.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.app.appuser.AppUser;
import com.example.app.appuser.AppUserService;

import org.apache.commons.lang3.StringUtils;

@Service
public class RegistrationService {

	@Autowired
	private EmailValidator emailValidator;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AppUserService appUserService;
	
	public String register(RegistrationRequest request) {
		validateRegisterRequest(request);
		AppUser appUser = new AppUser(request.getName(), request.getEmail(), passwordEncoder.encode(request.getPassword()), request.getPhoneNo());
		return appUserService.saveUser(appUser);
	}

	private void validateRegisterRequest(RegistrationRequest request) {
		if(StringUtils.isBlank(request.getName()))
			throw new IllegalStateException("name not valid");
		
		if(StringUtils.isBlank(request.getPassword()))
			throw new IllegalStateException("password not valid");
		
		if(StringUtils.isBlank(request.getPhoneNo()) || request.getPhoneNo().length() > 10)
			throw new IllegalStateException("phoneNo not valid");
		
		if(StringUtils.isNotBlank(request.getEmail()) && !emailValidator.test(request.getEmail()))
			throw new IllegalStateException("email not valid");
	}
}
