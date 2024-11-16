package com.samanvay.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.samanvay.admin.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User findByEmail(String email);
}
