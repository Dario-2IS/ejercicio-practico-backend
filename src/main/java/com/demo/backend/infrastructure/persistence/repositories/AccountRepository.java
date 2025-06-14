package com.demo.backend.infrastructure.persistence.repositories;

import com.demo.backend.infrastructure.persistence.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String accountNumber);

    Optional<Account> findByClientId(Long clientId);

    void deleteByAccountNumber(String accountNumber);
}
