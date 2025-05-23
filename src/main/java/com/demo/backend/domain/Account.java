package com.demo.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private String accountNumber;
    private String accountType;
    private double balance;
    private String currency;
    private boolean state;
    private Client client;
}
