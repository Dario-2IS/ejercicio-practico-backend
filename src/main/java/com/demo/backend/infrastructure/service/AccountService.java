package com.demo.backend.infrastructure.service;

import com.demo.backend.domain.Account;
import com.demo.backend.infrastructure.service.dto.AccountDto;

import java.util.List;

public interface AccountService {
    void createAccount(AccountDto accountDto);

    Account getAccountByNumber(String accountNumber);

    List<Account> getAllAccounts();

    void updateAccount(AccountDto accountDto);

    void deleteAccount(String accountNumber);

    List<Account> getAccountsByClientId(Long clientId);
}
