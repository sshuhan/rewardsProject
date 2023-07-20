package com.example.rewardsproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "transaction")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class TransactionEntity {
    @Id
    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(name = "transaction_date")
    private Timestamp transactionDate;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "amount")
    private Double transactionAmount;
}
