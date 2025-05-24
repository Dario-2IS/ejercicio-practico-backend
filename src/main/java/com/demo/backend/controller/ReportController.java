package com.demo.backend.controller;

import com.demo.backend.infrastructure.helper.ReportHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportHelper reportHelper;

    @GetMapping("/transactions/{accountNumber}")
    public ResponseEntity<byte[]> downloadTransactionReport(@PathVariable String accountNumber) {
        byte[] pdf = reportHelper.generateTransactionReport(accountNumber);

        HttpHeaders headers = reportHelper.getResponseHeaders();

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
