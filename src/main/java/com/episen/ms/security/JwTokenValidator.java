package com.episen.ms.security;

import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.episen.ms.models.UserContext;
import com.episen.ms.setting.InfraSettings;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.SignedJWT;

@Service
public class JwTokenValidator {

	private InfraSettings setting;

	private JWSVerifier verifier;

	public JwTokenValidator(InfraSettings setting) {
		this.setting = setting;
	}

	@PostConstruct
	public void init() {
		verifier = new RSASSAVerifier((RSAPublicKey) setting.keyPairLoader().getPublic());
	}

	public UserContext transforme(String token) {

		try {

			SignedJWT signedJWT = SignedJWT.parse(token);

			if (!signedJWT.verify(verifier)) {
				throw new RuntimeException("Token cannot be verified. Invalide Token");
			}

			UserContext user = new UserContext();
			user.setUsername(signedJWT.getJWTClaimsSet().getSubject());

			return user;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}
