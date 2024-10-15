package com.samanvay.admin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.samanvay.admin.entity.Zone;

@Repository
public interface ZoneRepository extends CrudRepository<Zone, Long> {
}
