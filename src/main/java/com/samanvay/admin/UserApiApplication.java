package com.samanvay.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@SpringBootApplication
public class UserApiApplication {

	@GetMapping("/api")
	public String getWelcomeMessage() {
		return "Welcome to Samanvays User API";
	}
	

	public static void main(String[] args) {
		SpringApplication.run(UserApiApplication.class, args);
	}

}
