package com.demo.backend.controller;

import com.demo.backend.domain.Movement;
import com.demo.backend.infrastructure.persistence.entities.Transaction;
import com.demo.backend.infrastructure.persistence.repositories.TransactionRepository;
import com.demo.backend.infrastructure.service.impl.PdfReportservice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final TransactionRepository transactionRepository;
    private final PdfReportservice pdfReportService;

    @GetMapping("/transactions/{accountNumber}")
    public ResponseEntity<byte[]> downloadTransactionReport(@PathVariable String accountNumber) {
        List<Transaction> transactions = transactionRepository.findByAccount_AccountNumber(accountNumber);
        List<Movement> movements = transactions.stream()
                .map(transaction -> new Movement(
                        transaction.getId(),
                        transaction.getDate(),
                        transaction.getAccount().getClient().getFirstName().concat(" ").concat(transaction.getAccount().getClient().getLastName()),
                        transaction.getAccount().getAccountType(),
                        transaction.getAccount().getAccountNumber(),
                        transaction.getInitialBalance(),
                        transaction.getAmount(),
                        Objects.equals(transaction.getTransactionType(), "DEPOSIT") ? "+" : "-",
                        transaction.getFinalBalance()
                )).toList();
        ByteArrayInputStream pdf = pdfReportService.generateTransactionReport(movements);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=transactions.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf.readAllBytes());
    }
}
