package com.demo.backend.controller;

import com.demo.backend.domain.Transaction;
import com.demo.backend.infrastructure.service.TransactionService;
import com.demo.backend.infrastructure.service.dto.TransactionDto;
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
     public ResponseEntity<List<Transaction>> getAll() {
         return ResponseEntity.ok(transactionService.getAllTransactions());
     }
     @GetMapping("/id/{transactionId}")
     public ResponseEntity<List<Transaction>> getTransactionById(@PathVariable Long transactionId) {
         return ResponseEntity.ok(transactionService.getTransactionById(transactionId));
     }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<List<Transaction>> getTransactionByAccountNumber(@PathVariable String accountNumber) {
        return ResponseEntity.ok(transactionService.getTransactionsByAccountNumber(accountNumber));
    }

     @PostMapping
     public ResponseEntity<String> createTransaction(@RequestBody TransactionDto transactionDto) {
         transactionService.createTransaction(transactionDto);
         return ResponseEntity.created(null).body("Transaction created successfully");
     }

     @DeleteMapping("/{transactionId}")
     public ResponseEntity<Void> deleteTransaction(@PathVariable Long transactionId) {
         transactionService.deleteTransaction(transactionId);
         return ResponseEntity.noContent().build();
     }
}
