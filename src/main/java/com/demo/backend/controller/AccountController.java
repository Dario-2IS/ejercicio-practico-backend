package com.demo.backend.controller;

import com.demo.backend.domain.Account;
import com.demo.backend.infrastructure.mapper.MapperProfile;
import com.demo.backend.infrastructure.service.AccountService;
import com.demo.backend.infrastructure.service.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
     private final AccountService accountService;

     @GetMapping
     public ResponseEntity<List<Account>> getAll() {
         return ResponseEntity.ok(accountService.getAllAccounts());
     }

     @GetMapping("/{accountNumber}")
     public ResponseEntity<Account> getAccountByNumber(@PathVariable String accountNumber) {
         return ResponseEntity.ok(accountService.getAccountByNumber(accountNumber));
     }

     @PostMapping
     public ResponseEntity<String> createAccount(@RequestBody AccountDto accountDto) {
         accountService.createAccount(accountDto);
         return ResponseEntity.created(null).body("Account created successfully");
     }

     @PutMapping
     public ResponseEntity<String> updateAccount(@RequestBody AccountDto accountDto) {
         accountService.updateAccount(accountDto);
         return ResponseEntity.ok("Account updated successfully");
     }

     @DeleteMapping("/{accountNumber}")
     public ResponseEntity<Void> deleteAccount(@PathVariable String accountNumber) {
         accountService.deleteAccount(accountNumber);
         return ResponseEntity.noContent().build();
     }
}
