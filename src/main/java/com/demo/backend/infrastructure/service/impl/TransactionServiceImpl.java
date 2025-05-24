package com.demo.backend.infrastructure.service.impl;

import com.demo.backend.domain.Account;
import com.demo.backend.domain.Transaction;
import com.demo.backend.infrastructure.persistence.repositories.TransactionRepository;
import com.demo.backend.infrastructure.service.TransactionService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    @Override
    public void createTransaction(String accountNumber, String transactionType, double amount) {

    }

    @Override
    public List<Transaction> getTransactionsByAccountNumber(String accountNumber) {
        return List.of();
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll().stream().map(
                transaction -> {
                    Transaction transactionDomain = new Transaction();
                    Account account = new Account();
                    transactionDomain.setId(transaction.getId());
                    transactionDomain.setTransactionType(transaction.getTransactionType());
                    transactionDomain.setAmount(transaction.getAmount());
                    transactionDomain.setCurrency(transaction.getCurrency());
                    transactionDomain.setDate(transaction.getDate());
                    transactionDomain.setTime(transaction.getTime());
                    account.setAccountNumber(transaction.getAccount().getAccountNumber());
                    account.setAccountType(transaction.getAccount().getAccountType());
                    transactionDomain.setAccount(account);
                    return transactionDomain;
                }
        ).toList();
    }

    @Override
    public void deleteTransaction(Long transactionId) {

    }

    @Override
    public void deleteAllTransactionsByAccountNumber(String accountNumber) {

    }
}
