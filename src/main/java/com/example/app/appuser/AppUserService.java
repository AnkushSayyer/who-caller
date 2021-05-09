package com.example.app.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserService implements UserDetailsService {

	@Autowired
	private AppUserRepository appUserRepository;

	private final static String USER_NOT_FOUND_MSG =
            "user with phoneNo %s not found";

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return appUserRepository.findByPhoneNo(username)
			.orElseThrow(()->
				new UsernameNotFoundException(USER_NOT_FOUND_MSG));
	}

	public String saveUser(AppUser user) {
		appUserRepository.save(user);
		return String.valueOf(user.getId());
	}

}
