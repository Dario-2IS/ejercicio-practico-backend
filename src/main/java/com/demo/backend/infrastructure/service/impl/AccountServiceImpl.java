package com.demo.backend.infrastructure.service.impl;

import com.demo.backend.domain.Account;
import com.demo.backend.domain.Client;
import com.demo.backend.infrastructure.mapper.MapperProfile;
import com.demo.backend.infrastructure.persistence.repositories.AccountRepository;
import com.demo.backend.infrastructure.persistence.repositories.ClientRepository;
import com.demo.backend.infrastructure.service.AccountService;
import com.demo.backend.infrastructure.service.dto.AccountDto;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;
    private final MapperProfile mapperProfile;
    @Override
    public void createAccount(AccountDto accountDto) {
        com.demo.backend.infrastructure.persistence.entities.Account accountEntity = mapperProfile.toEntityAccount(accountDto);
        Optional<com.demo.backend.infrastructure.persistence.entities.Client> clientEntity = clientRepository.findByIdentificationNumber(accountDto.getClientIdentificationNumber());

        if (clientEntity.isEmpty()) {
            throw new RuntimeException("Client not found");
        }

        accountEntity.setClient(clientEntity.get());
        accountRepository.save(accountEntity);
    }

    @Override
    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .map(account -> {
                    Account accountDomain = mapperProfile.toDomainAccount(account);
                    Client client = mapperProfile.toDomainClient(account.getClient());
                    accountDomain.setClient(client);
                    return accountDomain;
                }).orElse(null);
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
    public void updateAccount(AccountDto accountDto) {
        com.demo.backend.infrastructure.persistence.entities.Account accountEntity = mapperProfile.toEntityAccount(accountDto);
        Optional<com.demo.backend.infrastructure.persistence.entities.Account> existingAccount = accountRepository.findByAccountNumber(accountDto.getAccountNumber());

        if (existingAccount.isEmpty()) {
            throw new RuntimeException("Account not found");
        }

        com.demo.backend.infrastructure.persistence.entities.Client clientEntity = clientRepository.findByIdentificationNumber(accountDto.getClientIdentificationNumber())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        accountEntity.setId(existingAccount.get().getId());
        accountEntity.setClient(clientEntity);
        accountRepository.save(accountEntity);
    }

    @Override
    public void deleteAccount(String accountNumber) {
        Optional<com.demo.backend.infrastructure.persistence.entities.Account> accountEntity = accountRepository.findByAccountNumber(accountNumber);

        if (accountEntity.isEmpty()) {
            throw new RuntimeException("Account not found");
        }

        accountRepository.delete(accountEntity.get());
    }

    @Override
    public List<Account> getAccountsByClientId(Long clientId) {
        return accountRepository.findByClientId(clientId).stream().map(
                account -> {
                    Account accountDomain = mapperProfile.toDomainAccount(account);
                    Client client = mapperProfile.toDomainClient(account.getClient());
                    accountDomain.setClient(client);
                    return accountDomain;
                }
        ).toList();
    }
}
