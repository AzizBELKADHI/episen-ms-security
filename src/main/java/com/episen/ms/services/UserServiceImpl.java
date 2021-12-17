package com.episen.ms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.episen.ms.models.UserContext;
import com.episen.ms.repositories.UserAuthenticateRepository;

@Service
public class UserServiceImpl {

	@Autowired
	private UserAuthenticateRepository userAuthenticateRepository;

	public UserContext getUser(UserContext userContext) {
		UserContext userDetails = userAuthenticateRepository.findByUsernameAndPassword(userContext.getUsername(),
				userContext.getPassword());
		return userDetails;
	}

	public UserContext getByUsername(String username) {
		UserContext userDetails = userAuthenticateRepository.findByUsername(username);
		return userDetails;
	}

}
