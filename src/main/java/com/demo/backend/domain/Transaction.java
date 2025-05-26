package com.demo.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private Long id;
    private String transactionType;
    private double amount;
    private double initialBalance;
    private double finalBalance;
    private String date;
    private String time;
    private Account account;
}
