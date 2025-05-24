package com.demo.backend.infrastructure.helper;

import com.demo.backend.business.Deposit;
import com.demo.backend.infrastructure.persistence.entities.Account;
import com.demo.backend.infrastructure.persistence.repositories.AccountRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Data
public class DepositHelper implements Deposit {
    private final AccountRepository accountRepository;
    public Account calculateBalance(String accountNumber, double depositAmount) {
        Optional<Account> account = accountRepository.findByAccountNumber(accountNumber);
        if (account.isPresent()) {
            Account existingAccount = account.get();
            double newBalance = existingAccount.getBalance() + depositAmount;
            existingAccount.setBalance(newBalance);
            accountRepository.save(existingAccount);
            return existingAccount;
        } else {
            throw new IllegalArgumentException("Account not found");
        }
    }
}
