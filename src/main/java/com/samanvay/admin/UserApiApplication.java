package com.samanvay.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@SpringBootApplication
public class UserApiApplication {

	@GetMapping("/")
	public String getHome(HttpServletRequest request) {
		return "Spring Boot Application is running.";
	}

	@GetMapping("/api")
	public String getWelcomeMessage(HttpServletRequest request) {
		return "Welcome to Samanvay User API Service. Your Session ID is " + request.getSession().getId();
	}

	@GetMapping("/csrf-token")
	public CsrfToken getCSRFToken(HttpServletRequest request) {
		return (CsrfToken) request.getAttribute("_csrf");
	}

	public static void main(String[] args) {
		SpringApplication.run(UserApiApplication.class, args);
	}
}
