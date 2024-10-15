package com.samanvay.admin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.samanvay.admin.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
