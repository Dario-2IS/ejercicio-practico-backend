package com.demo.backend.infrastructure.service.impl;

import com.demo.backend.domain.Account;
import com.demo.backend.domain.Transaction;
import com.demo.backend.infrastructure.mapper.MapperProfile;
import com.demo.backend.infrastructure.persistence.repositories.TransactionRepository;
import com.demo.backend.infrastructure.helper.DepositHelper;
import com.demo.backend.infrastructure.service.dto.TransactionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private MapperProfile mapperProfile;
    @Mock
    private DepositHelper depositHelper;
    @InjectMocks
    private TransactionServiceImpl transactionService;

    private com.demo.backend.infrastructure.persistence.entities.Transaction transactionEntity;
    private com.demo.backend.infrastructure.persistence.entities.Account accountEntity;
    private Transaction domainTransaction;
    private TransactionDto transactionDto;
    private Account domainAccount;

    @BeforeEach
    void setUp() {
        transactionEntity = new com.demo.backend.infrastructure.persistence.entities.Transaction();
        transactionEntity.setId(1L);
        transactionEntity.setTransactionType("DEPOSIT");
        transactionEntity.setAmount(100.0);
        transactionEntity.setDate("2023-10-01");
        transactionEntity.setTime("10:00:00");

        accountEntity = new com.demo.backend.infrastructure.persistence.entities.Account();
        accountEntity.setAccountNumber("123456789");
        accountEntity.setBalance(1000.0);

        domainTransaction = new Transaction();
        domainTransaction.setId(1L);
        domainTransaction.setTransactionType("DEPOSIT");
        domainTransaction.setAmount(100.0);
        domainTransaction.setDate("2023-10-01");
        domainTransaction.setTime("10:00:00");

        domainAccount = new Account();
        domainAccount.setAccountNumber("123456789");
        domainAccount.setBalance(1000.0);

        transactionDto = TransactionDto.builder()
                .transactionType("DEPOSIT")
                .amount(100.0)
                .accountNumber("123456789")
                .build();
    }

    @Test
    void getTransactionById() {
        when(transactionRepository.findById(1L))
                .thenReturn(Optional.of(transactionEntity));
        when(mapperProfile.toDomainTransaction(transactionEntity))
                .thenReturn(domainTransaction);
        when(mapperProfile.toDomainAccount(transactionEntity.getAccount()))
                .thenReturn(domainAccount);

        List<Transaction> result = transactionService.getTransactionById(1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(transactionRepository).findById(1L);
        verify(mapperProfile).toDomainTransaction(transactionEntity);
        verify(mapperProfile).toDomainAccount(transactionEntity.getAccount());
    }

    @Test
    void getAllTransactions() {
        when(transactionRepository.findAll())
                .thenReturn(List.of(transactionEntity));
        when(mapperProfile.toDomainTransaction(transactionEntity))
                .thenReturn(domainTransaction);
        when(mapperProfile.toDomainAccount(transactionEntity.getAccount()))
                .thenReturn(domainAccount);

        List<Transaction> result = transactionService.getAllTransactions();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(transactionRepository).findAll();
        verify(mapperProfile).toDomainTransaction(transactionEntity);
        verify(mapperProfile).toDomainAccount(transactionEntity.getAccount());
    }

    @Test
    void getTransactionsByAccountNumber() {
        when(transactionRepository.findByAccount_AccountNumber("123456789"))
                .thenReturn(List.of(transactionEntity));
        when(mapperProfile.toDomainTransaction(transactionEntity))
                .thenReturn(domainTransaction);
        when(mapperProfile.toDomainAccount(transactionEntity.getAccount()))
                .thenReturn(domainAccount);

        List<Transaction> result = transactionService.getTransactionsByAccountNumber("123456789");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(transactionRepository).findByAccount_AccountNumber("123456789");
        verify(mapperProfile).toDomainTransaction(transactionEntity);
        verify(mapperProfile).toDomainAccount(transactionEntity.getAccount());
    }

    @Test
    void createTransaction() {

        when(depositHelper.getBalance(transactionDto.getAccountNumber()))
                .thenReturn(1000.0);
        when(depositHelper.calculateBalance(transactionDto.getAccountNumber(), transactionDto.getAmount()))
                .thenReturn(accountEntity);
        when(mapperProfile.toEntityTransaction(transactionDto))
                .thenReturn(transactionEntity);
        when(transactionRepository.save(transactionEntity))
                .thenReturn(transactionEntity);

        transactionService.createTransaction(transactionDto);

        verify(depositHelper).getBalance(transactionDto.getAccountNumber());
        verify(depositHelper).calculateBalance(transactionDto.getAccountNumber(), transactionDto.getAmount());
        verify(mapperProfile).toEntityTransaction(transactionDto);
        verify(transactionRepository).save(transactionEntity);
    }

    @Test
    void deleteTransaction() {
        doNothing().when(transactionRepository)
                .deleteById(1L);

        transactionService.deleteTransaction(1L);

        verify(transactionRepository).deleteById(1L);
    }

}