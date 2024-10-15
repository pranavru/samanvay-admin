package com.samanvay.admin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.samanvay.admin.entity.Address;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {
}
