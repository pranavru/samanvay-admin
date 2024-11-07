package com.samanvay.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import com.samanvay.admin.config.GlobalLiterals;
import com.samanvay.admin.entity.UserAuth;
import com.samanvay.admin.entity.DTO.AuthDTO;
import com.samanvay.admin.services.AuthenticationService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class UserAuthenticationController {

  @Autowired
  private AuthenticationService authService;

  private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(GlobalLiterals.BCRYPT_ENCODER_STRENGTH);  

  @PostMapping("/register")
  public UserAuth register(@RequestBody UserAuth user) {
    UserAuth updatedUser = UserAuth
      .builder()
      .username(user.getUsername())
      .password(bCryptPasswordEncoder.encode(user.getPassword()))
      .build();

    return authService.registerUser(updatedUser);
  }

  @PostMapping("/login")
  public AuthDTO login(@RequestBody UserAuth user) {
    return authService.verifyUser(user);
  }
}
