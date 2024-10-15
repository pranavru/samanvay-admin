package com.samanvay.admin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.samanvay.admin.entity.Mandal;

@Repository
public interface MandalRepository extends CrudRepository<Mandal, Long> {
}
