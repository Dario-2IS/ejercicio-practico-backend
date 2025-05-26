package com.demo.backend.infrastructure.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionDto {
    private String transactionType;
    private double amount;
    private String date;
    private String time;
    private String accountNumber;
}
