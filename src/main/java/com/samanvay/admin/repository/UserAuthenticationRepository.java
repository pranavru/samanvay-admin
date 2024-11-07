package com.samanvay.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.samanvay.admin.entity.UserAuth;

@Repository
public interface UserAuthenticationRepository extends JpaRepository<UserAuth, Long> {
  
  UserAuth findByUsername(String username);
  
}
