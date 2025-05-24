package com.demo.backend.infrastructure.helper;

import com.demo.backend.domain.Movement;
import com.demo.backend.infrastructure.persistence.entities.Transaction;
import com.demo.backend.infrastructure.persistence.repositories.TransactionRepository;
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

    private final TransactionRepository transactionRepository;
    private final PdfReportservice pdfReportService;

    public byte[] generateTransactionReport(String accountNumber) {
        List<Transaction> transactions = transactionRepository.findByAccount_AccountNumber(accountNumber);
        transactions.sort((t1, t2) -> t1.getId().compareTo(t2.getId()));
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
