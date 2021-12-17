package com.episen.ms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import com.episen.ms.models.UserContext;

@Component
public interface UserAuthenticateRepository extends JpaRepository<UserContext, Long> {

	UserContext findByUsername(String username);

	UserContext findByUsernameAndPassword(String username, String password);
}
