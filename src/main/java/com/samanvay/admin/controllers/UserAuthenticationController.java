package com.samanvay.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.samanvay.admin.entity.User;
import com.samanvay.admin.entity.UserAuth;
import com.samanvay.admin.entity.DTO.AuthDTO;
import com.samanvay.admin.entity.DTO.UserAuthDTO;
import com.samanvay.admin.services.AuthenticationService;
import com.samanvay.admin.services.UserService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class UserAuthenticationController {

  @Autowired
  private AuthenticationService authService;

  @Autowired
  private UserService userService;

  @PostMapping("/register")
  public UserAuth register(@RequestBody UserAuthDTO user) {
    User userExists = userService.findByEmail(user.getEmail());

    UserAuth updatedUser = UserAuth
      .builder()
      .username(user.getUsername())
      .password(user.getPassword())
      .user(userExists)
      .build();

    return authService.registerUser(updatedUser);
  }

  @PostMapping("/login")
  public AuthDTO login(@RequestBody UserAuth user) {
    return authService.verifyUser(user);
  }
}
