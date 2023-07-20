package com.example.rewardsproject.dao;

import com.example.rewardsproject.entity.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {
    public Optional<CustomerEntity> findByCustomerId(Long customerId);
}
