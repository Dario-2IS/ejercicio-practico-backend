package com.demo.backend.controller;

import com.demo.backend.domain.Transaction;
import com.demo.backend.infrastructure.service.TransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
     private final TransactionService transactionService;

     public TransactionController(TransactionService transactionService) {
         this.transactionService = transactionService;
     }

     @GetMapping
     public List<Transaction> getAll() {
         return transactionService.getAllTransactions();
     }
}
