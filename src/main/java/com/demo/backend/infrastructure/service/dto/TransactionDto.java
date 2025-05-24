package com.demo.backend.infrastructure.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionDto {
    private Long id;
    private String transactionType;
    private double amount;
    private String currency;
    private String date;
    private String time;
    private AccountDto account;
}
