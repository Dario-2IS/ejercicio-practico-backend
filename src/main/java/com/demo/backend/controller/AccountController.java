package com.demo.backend.controller;

import com.demo.backend.domain.Account;
import com.demo.backend.infrastructure.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
     private final AccountService accountService;

     public AccountController(AccountService accountService) {
         this.accountService = accountService;
     }

     @GetMapping
     public List<Account> getAll() {
         return accountService.getAllAccounts();
     }
}
