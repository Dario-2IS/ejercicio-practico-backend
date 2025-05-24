package com.demo.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movement {
    private Long id;
    private String date;
    private String clientName;
    private String accountType;
    private String accountNumber;
    private double initialBalance;
    private double amount;
    private String transactionType;
    private double finalBalance;
}
