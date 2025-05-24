package com.demo.backend.infrastructure.service;

import com.demo.backend.domain.Transaction;

import java.util.List;

public interface TransactionService {
    void createTransaction(String accountNumber, String transactionType, double amount);

    List<Transaction> getTransactionsByAccountNumber(String accountNumber);

    List<Transaction> getAllTransactions();

    void deleteTransaction(Long transactionId);

    void deleteAllTransactionsByAccountNumber(String accountNumber);
}
