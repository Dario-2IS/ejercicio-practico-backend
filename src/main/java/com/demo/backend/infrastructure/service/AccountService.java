package com.demo.backend.infrastructure.service;

import com.demo.backend.domain.Account;

import java.util.List;

public interface AccountService {
    Account createAccount(Account account);

    Account getAccountByNumber(String accountNumber);

    List<Account> getAllAccounts();

    Account updateAccount(String accountNumber, Account account);

    void deleteAccount(String accountNumber);

    List<Account> getAccountsByClientId(Long clientId);

    void deleteAllAccountsByClientId(Long clientId);
}
