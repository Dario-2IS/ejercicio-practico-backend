package com.demo.backend.controller;

import com.demo.backend.domain.Transaction;
import com.demo.backend.infrastructure.service.TransactionService;
import com.demo.backend.infrastructure.service.dto.TransactionDto;
import com.demo.backend.infrastructure.service.template.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionControllerTest {

    
    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    private Transaction testTransaction1;
    private Transaction testTransaction2;
    private TransactionDto testTransactionDto;
    private List<Transaction> transactionList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testTransaction1 = new Transaction();
        testTransaction1.setTransactionType("DEPOSIT");
        testTransaction1.setAmount(100.0);

        testTransaction2 = new Transaction();
        testTransaction2.setTransactionType("WITHDRAWAL");
        testTransaction2.setAmount(50.0);

        transactionList = Arrays.asList(testTransaction1, testTransaction2);

        testTransactionDto = TransactionDto.builder()
                .transactionType("DEPOSIT")
                .amount(100.0)
                .accountNumber("123456789")
                .build();
    }

    @Test
    void getAll() {
        ResponseEntity<ApiResponse<List<Transaction>>> response = transactionController.getAll();

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void getTransactionById() {
        when(transactionService.getTransactionById(1L))
                .thenReturn(transactionList);

        ResponseEntity<ApiResponse<List<Transaction>>> response = transactionController.getTransactionById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transactionList, response.getBody().getData());
        verify(transactionService).getTransactionById(1L);
    }

    @Test
    void getTransactionByAccountNumber() {
        when(transactionService.getTransactionsByAccountNumber("123"))
                .thenReturn(transactionList);

        ResponseEntity<ApiResponse<List<Transaction>>> response = transactionController.getTransactionByAccountNumber("123");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transactionList, response.getBody().getData());
        verify(transactionService).getTransactionsByAccountNumber("123");
    }

    @Test
    void createTransaction() {
        doNothing().when(transactionService).createTransaction(testTransactionDto);

        ResponseEntity<ApiResponse<Void>> response = transactionController.createTransaction(testTransactionDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Transaction created successfully", response.getBody().getMessage());
        verify(transactionService).createTransaction(testTransactionDto);
    }

    @Test
    void deleteTransaction() {
        ResponseEntity<ApiResponse<Void>> response = transactionController.deleteTransaction(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(transactionService).deleteTransaction(1L);
    }

}