package com.demo.backend.infrastructure.service;

import com.demo.backend.domain.Transaction;
import com.demo.backend.infrastructure.service.dto.TransactionDto;

import java.util.List;

public interface TransactionService {
    void createTransaction(TransactionDto transactionDto);

    List<Transaction> getTransactionById(Long transactionId);
    List<Transaction> getTransactionsByAccountNumber(String accountNumber);
    List<Transaction> getTransactionsReport(String accountNumber, String startDate, String endDate);

    List<Transaction> getAllTransactions();

    void deleteTransaction(Long transactionId);
}
