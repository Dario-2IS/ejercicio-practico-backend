package com.demo.backend.infrastructure.service.impl;

import com.demo.backend.domain.Account;
import com.demo.backend.domain.Client;
import com.demo.backend.infrastructure.mapper.MapperProfile;
import com.demo.backend.infrastructure.persistence.repositories.AccountRepository;
import com.demo.backend.infrastructure.service.AccountService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final MapperProfile mapperProfile;
    @Override
    public Account createAccount(Account account) {
        return null;
    }

    @Override
    public Account getAccountByNumber(String accountNumber) {
        return null;
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll().stream().map(
                account -> {
                    Account accountDomain = mapperProfile.toDomainAccount(account);
                    Client client = mapperProfile.toDomainClient(account.getClient());
                    accountDomain.setClient(client);
                    return accountDomain;
                }
        ).toList();
    }

    @Override
    public Account updateAccount(String accountNumber, Account account) {
        return null;
    }

    @Override
    public void deleteAccount(String accountNumber) {

    }

    @Override
    public List<Account> getAccountsByClientId(Long clientId) {
        return List.of();
    }

    @Override
    public void deleteAllAccountsByClientId(Long clientId) {

    }
}
