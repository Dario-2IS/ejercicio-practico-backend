package com.demo.backend.infrastructure.service.dto;

import com.demo.backend.domain.Client;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDto {
    private String accountNumber;
    private String accountType;
    private double balance;
    private boolean state;
    private String clientIdentificationNumber;
}
