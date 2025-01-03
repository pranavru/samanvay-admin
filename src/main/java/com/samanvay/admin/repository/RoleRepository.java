package com.samanvay.admin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.samanvay.admin.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
}
