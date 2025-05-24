package com.demo.backend.business;

import com.demo.backend.infrastructure.persistence.entities.Account;

public interface Withdrawal {
    Account calculateBalance(String accountNumber, double debitAmount);
    double getBalance(String accountNumber);
}
