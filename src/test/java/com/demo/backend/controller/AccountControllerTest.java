package com.demo.backend.controller;

import com.demo.backend.domain.Account;
import com.demo.backend.infrastructure.service.AccountService;
import com.demo.backend.infrastructure.service.dto.AccountDto;
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

class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    private Account testAccount1;
    private Account testAccount2;
    private AccountDto testAccountDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testAccount1 = new Account();
        testAccount1.setAccountNumber("123");
        testAccount1.setAccountType("Savings");

        testAccount2 = new Account();
        testAccount2.setAccountNumber("456");
        testAccount2.setAccountType("Current");

        testAccountDto = AccountDto.builder()
                .accountNumber("123")
                .accountType("Savings")
                .balance(1000.0)
                .state(true)
                .build();
    }

    @Test
    void getAll() {
        when(accountService.getAllAccounts())
                .thenReturn(Arrays.asList(testAccount1, testAccount2));

        ResponseEntity<ApiResponse<List<Account>>> response = accountController.getAll();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().getData().size());
        assertEquals("123", response.getBody().getData().get(0).getAccountNumber());
        assertEquals("456", response.getBody().getData().get(1).getAccountNumber());

        verify(accountService).getAllAccounts();
    }

    @Test
    void getAccountByNumber() {
        when(accountService.getAccountByNumber("123"))
                .thenReturn(testAccount1);

        ResponseEntity<ApiResponse<Account>> response = accountController.getAccountByNumber("123");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("123", response.getBody().getData().getAccountNumber());
        assertEquals("Savings", response.getBody().getData().getAccountType());

        verify(accountService).getAccountByNumber("123");
    }

    @Test
    void createAccount() {
        doNothing().when(accountService).createAccount(testAccountDto);

        ResponseEntity<ApiResponse<Void>> response = accountController.createAccount(testAccountDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Account created successfully", response.getBody().getMessage());

        verify(accountService).createAccount(testAccountDto);
    }

    @Test
    void updateAccount() {
        ResponseEntity<ApiResponse<Void>> response = accountController.updateAccount(testAccountDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Account updated successfully", response.getBody().getMessage());

        verify(accountService).updateAccount(testAccountDto);
    }

    @Test
    void deleteAccount() {
        ResponseEntity<ApiResponse<Void>> response = accountController.deleteAccount("123");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody().getData());

        verify(accountService).deleteAccount("123");
    }
}