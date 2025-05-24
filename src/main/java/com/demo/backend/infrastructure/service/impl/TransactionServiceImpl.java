package com.demo.backend.infrastructure.service.impl;

import com.demo.backend.domain.Account;
import com.demo.backend.domain.Transaction;
import com.demo.backend.infrastructure.mapper.MapperProfile;
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
    private final MapperProfile mapperProfile;
    @Override
    public void createTransaction(TransactionDto transactionDto) {
        com.demo.backend.infrastructure.persistence.entities.Transaction transactionEntity = mapperProfile.toEntityTransaction(transactionDto);
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
