package com.demo.backend.infrastructure.persistence.repositories;

import com.demo.backend.infrastructure.persistence.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccount_AccountNumber(String accountNumber);
}
