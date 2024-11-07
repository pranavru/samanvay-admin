package com.samanvay.admin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.samanvay.admin.config.GlobalLiterals;
import com.samanvay.admin.entity.UserAuth;
import com.samanvay.admin.entity.DTO.AuthDTO;
import com.samanvay.admin.repository.UserAuthenticationRepository;

@Service
public class AuthenticationService {
  @Autowired
  private UserAuthenticationRepository userAuthenticationRepository;

  @Autowired
  AuthenticationManager authManager;

  @Autowired
  private JWTTokenService jwtTokenService;

  private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(GlobalLiterals.BCRYPT_ENCODER_STRENGTH);

  public UserAuth registerUser(UserAuth user) {
    user.setPassword(encoder.encode(user.getPassword()));

    return this.userAuthenticationRepository.save(user);
  }

  public AuthDTO verifyUser(UserAuth user) {

    Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
    
    if(authentication.isAuthenticated()) {
      return jwtTokenService.generateToken(user.getUsername());
    }

    return AuthDTO.builder().username(user.getUsername()).token("").message("User not authenticated").build();
  }
}
