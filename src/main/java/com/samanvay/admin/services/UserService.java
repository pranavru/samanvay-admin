package com.samanvay.admin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samanvay.admin.entity.User;
import com.samanvay.admin.repository.UserRepository;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public User findByEmail(String email) {
    return userRepository.findByEmail(email);
  }
}
