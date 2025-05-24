package com.demo.backend.infrastructure.service.impl;

import com.demo.backend.domain.Account;
import com.demo.backend.domain.Transaction;
import com.demo.backend.infrastructure.helper.DepositHelper;
import com.demo.backend.infrastructure.helper.WithdrawalHelper;
import com.demo.backend.infrastructure.mapper.MapperProfile;
import com.demo.backend.infrastructure.persistence.repositories.AccountRepository;
import com.demo.backend.infrastructure.persistence.repositories.TransactionRepository;
import com.demo.backend.infrastructure.service.TransactionService;
import com.demo.backend.infrastructure.service.dto.TransactionDto;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final MapperProfile mapperProfile;
    private final DepositHelper depositHelper;
    private final WithdrawalHelper withdrawalHelper;
    @Override
    public void createTransaction(TransactionDto transactionDto) {
        com.demo.backend.infrastructure.persistence.entities.Account accountEntity = new com.demo.backend.infrastructure.persistence.entities.Account();
        if(transactionDto.getTransactionType().equals("DEPOSIT")) {
            accountEntity = depositHelper.calculateBalance(transactionDto.getAccountNumber(), transactionDto.getAmount());
        } else if(transactionDto.getTransactionType().equals("WITHDRAWAL")) {
            accountEntity = withdrawalHelper.calculateBalance(transactionDto.getAccountNumber(), transactionDto.getAmount());
        } else {
            throw new IllegalArgumentException("Invalid transaction type");
        }

        com.demo.backend.infrastructure.persistence.entities.Transaction transactionEntity = mapperProfile.toEntityTransaction(transactionDto);
        transactionEntity.setAccount(accountEntity);

        transactionRepository.save(transactionEntity);
    }

    @Override
    public List<Transaction> getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId).stream().map(
                transaction -> {
                    Transaction transactionDomain = mapperProfile.toDomainTransaction(transaction);
                    Account account = mapperProfile.toDomainAccount(transaction.getAccount());
                    transactionDomain.setAccount(account);
                    return transactionDomain;
                }
        ).toList();
    }

    @Override
    public List<Transaction> getTransactionsByAccountNumber(String accountNumber) {
        return transactionRepository.findByAccount_AccountNumber(accountNumber).stream().map(
                transaction -> {
                    Transaction transactionDomain = mapperProfile.toDomainTransaction(transaction);
                    Account account = mapperProfile.toDomainAccount(transaction.getAccount());
                    transactionDomain.setAccount(account);
                    return transactionDomain;
                }
        ).toList();
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll().stream().map(
                transaction -> {
                    Transaction transactionDomain = mapperProfile.toDomainTransaction(transaction);
                    Account account = mapperProfile.toDomainAccount(transaction.getAccount());
                    transactionDomain.setAccount(account);
                    return transactionDomain;
                }
        ).toList();
    }

    @Override
    public void deleteTransaction(Long transactionId) {
        transactionRepository.deleteById(transactionId);
    }
}
