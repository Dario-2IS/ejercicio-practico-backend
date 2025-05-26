package com.demo.backend.infrastructure.service.impl;

import com.demo.backend.domain.Account;
import com.demo.backend.domain.Transaction;
import com.demo.backend.infrastructure.helper.DepositHelper;
import com.demo.backend.infrastructure.helper.WithdrawalHelper;
import com.demo.backend.infrastructure.mapper.MapperProfile;
import com.demo.backend.infrastructure.persistence.repositories.TransactionRepository;
import com.demo.backend.infrastructure.service.TransactionService;
import com.demo.backend.infrastructure.service.dto.TransactionDto;
import com.demo.backend.infrastructure.utils.DateUtils;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Data
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final MapperProfile mapperProfile;
    private final DepositHelper depositHelper;
    private final WithdrawalHelper withdrawalHelper;
    @Override
    public void createTransaction(TransactionDto transactionDto) {
        com.demo.backend.infrastructure.persistence.entities.Account accountEntity = new com.demo.backend.infrastructure.persistence.entities.Account();
        double initialBalance = 0.0;

        if(transactionDto.getTransactionType().equals("DEPOSIT")) {
            initialBalance = depositHelper.getBalance(transactionDto.getAccountNumber());
            accountEntity = depositHelper.calculateBalance(transactionDto.getAccountNumber(), transactionDto.getAmount());
        } else if(transactionDto.getTransactionType().equals("WITHDRAWAL")) {
            initialBalance = withdrawalHelper.getBalance(transactionDto.getAccountNumber());
            accountEntity = withdrawalHelper.calculateBalance(transactionDto.getAccountNumber(), transactionDto.getAmount());
        } else {
            throw new IllegalArgumentException("Invalid transaction type");
        }

        com.demo.backend.infrastructure.persistence.entities.Transaction transactionEntity = new com.demo.backend.infrastructure.persistence.entities.Transaction();
        transactionEntity = mapperProfile.toEntityTransaction(transactionDto);
        transactionEntity.setDate(DateUtils.getCurrentDate());
        transactionEntity.setTime(DateUtils.getCurrentTimeWithOffset());
        transactionEntity.setInitialBalance(initialBalance);
        transactionEntity.setFinalBalance(accountEntity.getBalance());
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
    public List<Transaction> getTransactionsReport(String accountNumber, String startDate, String endDate) {
        LocalDate startLocalDate = DateUtils.parseDate(startDate);
        LocalDate endLocalDate = DateUtils.parseDate(endDate);

        return transactionRepository.findByAccount_AccountNumber(accountNumber)
                .stream()
                .filter(transaction -> {
                    LocalDate transactionDate = DateUtils.parseDate(transaction.getDate());
                    return (transactionDate.isEqual(startLocalDate) || transactionDate.isAfter(startLocalDate)) &&
                            (transactionDate.isEqual(endLocalDate) || transactionDate.isBefore(endLocalDate));
                })
                .map(transaction -> {
                    Transaction transactionDomain = mapperProfile.toDomainTransaction(transaction);
                    Account account = mapperProfile.toDomainAccount(transaction.getAccount());
                    transactionDomain.setAccount(account);
                    return transactionDomain;
                })
                .toList();
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
