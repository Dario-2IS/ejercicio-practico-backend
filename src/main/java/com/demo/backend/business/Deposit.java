package com.demo.backend.business;

import com.demo.backend.infrastructure.persistence.entities.Account;

public interface Deposit {
     Account calculateBalance(String accountNumber, double depositAmount);
     double getBalance(String accountNumber);
}
