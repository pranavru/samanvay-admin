package com.samanvay.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.samanvay.admin.entity.MandalEvent;

@Repository
public interface MandalEventRepository extends JpaRepository<MandalEvent, Long> {

}
