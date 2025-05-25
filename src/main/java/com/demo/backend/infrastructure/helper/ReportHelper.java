package com.demo.backend.infrastructure.helper;

import com.demo.backend.domain.Movement;
import com.demo.backend.domain.Transaction;
import com.demo.backend.infrastructure.service.TransactionService;
import com.demo.backend.infrastructure.service.impl.PdfReportservice;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Objects;

@Service
@Data
public class ReportHelper {

    private final TransactionService transactionService;
    private final PdfReportservice pdfReportService;

    public byte[] generateTransactionReport(String accountNumber, String startDate, String endDate) {
        List<Transaction> transactions = transactionService.getTransactionsReport(accountNumber, startDate, endDate);
        if (transactions.isEmpty()) {
            throw new IllegalArgumentException("No transactions found for the given account number and date range.");
        }
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
        return pdf.readAllBytes();
    }

    public HttpHeaders getResponseHeaders() {
        return setResponseHeaders();
    }

    private HttpHeaders setResponseHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=transactions.pdf");
        return headers;
    }
}
