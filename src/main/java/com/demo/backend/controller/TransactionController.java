package com.demo.backend.controller;

import com.demo.backend.domain.Transaction;
import com.demo.backend.infrastructure.service.TransactionService;
import com.demo.backend.infrastructure.service.dto.TransactionDto;
import com.demo.backend.infrastructure.service.template.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
     private final TransactionService transactionService;

     @GetMapping
     public ResponseEntity<ApiResponse<List<Transaction>>> getAll() {
         ApiResponse<List<Transaction>> response = new ApiResponse<>();
         List<Transaction> transactions = transactionService.getAllTransactions();
         response.setData(transactions);
         response.setMessage("Transactions retrieved successfully");
         return ResponseEntity.ok(response);
     }
     @GetMapping("/id/{transactionId}")
     public ResponseEntity<ApiResponse<List<Transaction>>> getTransactionById(@PathVariable Long transactionId) {
         ApiResponse<List<Transaction>> response = new ApiResponse<>();
         List<Transaction> transactions = transactionService.getTransactionById(transactionId);
         response.setData(transactions);
         response.setMessage("Transaction retrieved successfully");
         return ResponseEntity.ok(response);
     }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<ApiResponse<List<Transaction>>> getTransactionByAccountNumber(@PathVariable String accountNumber) {
        ApiResponse<List<Transaction>> response = new ApiResponse<>();
        List<Transaction> transactions = transactionService.getTransactionsByAccountNumber(accountNumber);
        response.setData(transactions);
        response.setMessage("Transactions retrieved successfully");
        return ResponseEntity.ok(response);
    }

     @PostMapping
     public ResponseEntity<ApiResponse<Void>> createTransaction(@RequestBody TransactionDto transactionDto) {
         ApiResponse<Void> response = new ApiResponse<>();
         transactionService.createTransaction(transactionDto);
         response.setMessage("Transaction created successfully");
         return ResponseEntity.ok(response);
     }

     @DeleteMapping("/{transactionId}")
     public ResponseEntity<ApiResponse<Void>> deleteTransaction(@PathVariable Long transactionId) {
         ApiResponse<Void> response = new ApiResponse<>();
         transactionService.deleteTransaction(transactionId);
         response.setMessage("Transaction deleted successfully");
         return ResponseEntity.ok(response);
     }
}
