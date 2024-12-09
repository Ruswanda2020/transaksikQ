package com.Onedev.transaksiQ.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String invoiceNumber;
    private String transactionType;
    private Integer totalAmount;
    private String serviceCode;
    private String serviceName;
    private LocalDateTime createdOn = LocalDateTime.now();
}
