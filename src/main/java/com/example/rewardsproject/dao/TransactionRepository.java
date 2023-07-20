package com.example.rewardsproject.dao;

import com.example.rewardsproject.entity.TransactionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Repository
@Transactional
public interface TransactionRepository extends CrudRepository<TransactionEntity, Long> {
    List<TransactionEntity> findAllByCustomerId(Long customerId);
    List<TransactionEntity> findAllByCustomerIdAndTransactionDateBetween(Long customerId, Timestamp from, Timestamp to);
}