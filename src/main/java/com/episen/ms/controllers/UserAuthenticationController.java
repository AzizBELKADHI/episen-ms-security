package com.episen.ms.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.episen.ms.models.AuthenticationResponse;
import com.episen.ms.models.UserContext;
import com.episen.ms.security.JwTokenGenerator;
import com.episen.ms.security.JwTokenValidator;
import com.episen.ms.services.UserServiceImpl;

@RestController
public class UserAuthenticationController {

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Autowired
	private JwTokenGenerator jwtTokenUtil;

	@Autowired
	private JwTokenValidator jwtTokenUtilValidator;

	/**
	 * First endpoint
	 */
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> generateToken(@RequestBody UserContext userContext) throws Exception {

		if (userServiceImpl.getUser(userContext) != null) {
			String jwt = jwtTokenUtil.generateToken(userContext.getUsername(), new ArrayList<>());
			System.out.println(jwt);
			return ResponseEntity.ok(new AuthenticationResponse(jwt));

		}
		System.out.println("coucou");
		return ResponseEntity.ok("Incorrect username or password");
	}

	/**
	 * Second endpoint
	 */
	@RequestMapping(value = "/verify", method = RequestMethod.POST)
	public String recoverUsername(@RequestBody AuthenticationResponse authenticationResponse) {
		return "username : " + jwtTokenUtilValidator.transforme(authenticationResponse.getJwt()).getUsername();
	}
}
