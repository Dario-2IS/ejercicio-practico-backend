package com.demo.backend.infrastructure.service.impl;

import com.demo.backend.domain.Account;
import com.demo.backend.infrastructure.mapper.MapperProfile;
import com.demo.backend.infrastructure.persistence.entities.Client;
import com.demo.backend.infrastructure.persistence.repositories.AccountRepository;
import com.demo.backend.infrastructure.persistence.repositories.ClientRepository;
import com.demo.backend.infrastructure.service.dto.AccountDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private ClientRepository clientRepository;

    @Mock
    private MapperProfile mapperProfile;

    @InjectMocks
    private AccountServiceImpl accountService;

    private com.demo.backend.infrastructure.persistence.entities.Account accountEntity1;
    private com.demo.backend.infrastructure.persistence.entities.Account accountEntity2;
    private Account domainAccount1;
    private Account domainAccount2;
    private AccountDto accountDto1;
    private Client clientEntity1;

    @BeforeEach
    void setUp() {
        accountEntity1 = new com.demo.backend.infrastructure.persistence.entities.Account();
        accountEntity1.setAccountNumber("123");
        accountEntity1.setAccountType("Savings");
        accountEntity1.setBalance(1000.0);
        accountEntity1.setState(true);

        accountEntity2 = new com.demo.backend.infrastructure.persistence.entities.Account();
        accountEntity2.setAccountNumber("456");
        accountEntity2.setBalance(2000.0);

        domainAccount1 = new Account();
        domainAccount1.setAccountNumber("123");
        domainAccount1.setBalance(1000.0);

        domainAccount2 = new Account();
        domainAccount2.setAccountNumber("456");
        domainAccount2.setBalance(2000.0);

        accountDto1 = AccountDto.builder()
                .accountNumber("123")
                .accountType("Savings")
                .balance(1000.0)
                .state(true)
                .clientIdentificationNumber("123")
                .build();

        clientEntity1 = new Client();
        clientEntity1.setIdentificationNumber("123");
        clientEntity1.setFirstName("Juan");
        clientEntity1.setLastName("Doe");
        clientEntity1.setGender(true);
        clientEntity1.setAge(30);
        clientEntity1.setPhoneNumber("123456789");
        clientEntity1.setAddress("123456789");
        clientEntity1.setPassword("<PASSWORD>");
        clientEntity1.setState(true);
    }

    @Test
    void getAllAccounts() {
        when(accountRepository.findAll())
                .thenReturn(Arrays.asList(accountEntity1, accountEntity2));
        when(mapperProfile.toDomainAccount(accountEntity1))
                .thenReturn(domainAccount1);
        when(mapperProfile.toDomainAccount(accountEntity2))
                .thenReturn(domainAccount2);

        List<Account> result = accountService.getAllAccounts();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("123", result.get(0).getAccountNumber());
        assertEquals("456", result.get(1).getAccountNumber());

        verify(accountRepository).findAll();
        verify(mapperProfile, times(2))
                .toDomainAccount(any());
    }

    @Test
    void getAccountByNumber() {
        when(accountRepository.findByAccountNumber("123"))
                .thenReturn(java.util.Optional.of(accountEntity1));
        when(mapperProfile.toDomainAccount(accountEntity1))
                .thenReturn(domainAccount1);

        Account result = accountService.getAccountByNumber("123");

        assertNotNull(result);
        assertEquals("123", result.getAccountNumber());
        assertEquals(1000.0, result.getBalance());

        verify(accountRepository).findByAccountNumber("123");
        verify(mapperProfile).toDomainAccount(accountEntity1);
    }

    @Test
    void createAccount() {
        when(mapperProfile.toEntityAccount(accountDto1))
                .thenReturn(accountEntity1);
        when(clientRepository.findByIdentificationNumber("123"))
                .thenReturn(java.util.Optional.of(clientEntity1));
        when(accountRepository.save(accountEntity1))
                .thenReturn(any(com.demo.backend.infrastructure.persistence.entities.Account.class));

        accountService.createAccount(accountDto1);

        verify(mapperProfile).toEntityAccount(accountDto1);
        verify(clientRepository).findByIdentificationNumber("123");
        verify(accountRepository).save(any());
    }

    @Test
    void updateAccount() {
        when(mapperProfile.toEntityAccount(accountDto1))
                .thenReturn(accountEntity1);
        when(accountRepository.findByAccountNumber("123"))
                .thenReturn(java.util.Optional.of(accountEntity1));
        when(clientRepository.findByIdentificationNumber("123"))
                .thenReturn(java.util.Optional.of(clientEntity1));
        when(accountRepository.save(any()))
                .thenReturn(accountEntity1);

        accountService.updateAccount(accountDto1);

        verify(accountRepository).findByAccountNumber(any());
        verify(accountRepository).save(any());
    }

    @Test
    void deleteAccount() {
        when(accountRepository.findByAccountNumber("123"))
                .thenReturn(java.util.Optional.of(accountEntity1));
        doNothing().when(accountRepository).delete(any());

        accountService.deleteAccount("123");

        verify(accountRepository).findByAccountNumber("123");
        verify(accountRepository).delete(accountEntity1);
    }
}