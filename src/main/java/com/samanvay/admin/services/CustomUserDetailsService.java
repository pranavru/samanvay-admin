package com.samanvay.admin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.samanvay.admin.entity.UserAuth;
import com.samanvay.admin.entity.UserPrincipal;
import com.samanvay.admin.repository.UserAuthenticationRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserAuthenticationRepository userAuthenticationRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserAuth user = this.userAuthenticationRepository.findByUsername(username);

    if(user == null) {
      throw new UsernameNotFoundException("\nUser not found with username: " + username + "\n");
    }

    return new UserPrincipal(user);
  }
}
