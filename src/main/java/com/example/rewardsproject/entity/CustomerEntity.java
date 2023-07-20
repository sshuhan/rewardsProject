package com.example.rewardsproject.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id", updatable = false, nullable = false)
    private Long customerId;

    @Column(name = "customer_name")
    private String customerName;
}
