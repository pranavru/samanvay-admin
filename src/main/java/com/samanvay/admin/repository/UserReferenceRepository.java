package com.samanvay.admin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.samanvay.admin.entity.UserReference;

@Repository
public interface UserReferenceRepository extends CrudRepository<UserReference, Long> {
}
