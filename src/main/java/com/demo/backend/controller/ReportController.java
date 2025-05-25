package com.demo.backend.controller;

import com.demo.backend.infrastructure.helper.ReportHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportHelper reportHelper;

    @GetMapping("/transactions/{accountNumber}")
    public ResponseEntity<byte[]> downloadTransactionReport(
            @PathVariable String accountNumber,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate ) {
        byte[] pdf = reportHelper.generateTransactionReport(accountNumber, startDate, endDate);

        HttpHeaders headers = reportHelper.getResponseHeaders();

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
