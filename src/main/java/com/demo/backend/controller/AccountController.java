package com.demo.backend.controller;

import com.demo.backend.domain.Account;
import com.demo.backend.infrastructure.mapper.MapperProfile;
import com.demo.backend.infrastructure.service.AccountService;
import com.demo.backend.infrastructure.service.dto.AccountDto;
import com.demo.backend.infrastructure.service.template.ApiResponse;
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
     public ResponseEntity<ApiResponse<List<Account>>> getAll() {
         ApiResponse<List<Account>> response = new ApiResponse<>();
         List<Account> accounts = accountService.getAllAccounts();
         response.setData(accounts);
         response.setMessage("Accounts retrieved successfully");
         return ResponseEntity.ok(response);
     }

     @GetMapping("/{accountNumber}")
     public ResponseEntity<ApiResponse<Account>> getAccountByNumber(@PathVariable String accountNumber) {
         ApiResponse<Account> response = new ApiResponse<>();
         Account account = accountService.getAccountByNumber(accountNumber);
         response.setData(account);
         response.setMessage("Account retrieved successfully");
         return ResponseEntity.ok(response);
     }

     @PostMapping
     public ResponseEntity<ApiResponse<Void>> createAccount(@RequestBody AccountDto accountDto) {
         ApiResponse<Void> response = new ApiResponse<>();
         accountService.createAccount(accountDto);
         response.setMessage("Account created successfully");
         return ResponseEntity.ok(response);
     }

     @PutMapping
     public ResponseEntity<ApiResponse<Void>> updateAccount(@RequestBody AccountDto accountDto) {
         ApiResponse<Void> response = new ApiResponse<>();
         accountService.updateAccount(accountDto);
         response.setMessage("Account updated successfully");
         return ResponseEntity.ok(response);
     }

     @DeleteMapping("/{accountNumber}")
     public ResponseEntity<ApiResponse<Void>> deleteAccount(@PathVariable String accountNumber) {
         ApiResponse<Void> response = new ApiResponse<>();
         accountService.deleteAccount(accountNumber);
         response.setMessage("Account deleted successfully");
         return ResponseEntity.ok(response);
     }
}
