package com.demo.backend.infrastructure.helper;

import com.demo.backend.business.Withdrawal;
import com.demo.backend.infrastructure.persistence.entities.Account;
import com.demo.backend.infrastructure.persistence.repositories.AccountRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Data
public class WithdrawalHelper implements Withdrawal {

    private final AccountRepository accountRepository;

    @Override
    public Account calculateBalance(String accountNumber, double debitAmount) {
        Optional<Account> account = accountRepository.findByAccountNumber(accountNumber);
        if (account.isPresent()) {
            Account existingAccount = account.get();
            double newBalance = existingAccount.getBalance() - debitAmount;
            if (newBalance < 0) {
                throw new IllegalArgumentException("Insufficient funds");
            }
            existingAccount.setBalance(newBalance);
            accountRepository.save(existingAccount);
            return existingAccount;
        } else {
            throw new IllegalArgumentException("Account not found");
        }
    }

    @Override
    public double getBalance(String accountNumber) {
        Optional<Account> account = accountRepository.findByAccountNumber(accountNumber);
        if (account.isPresent()) {
            return account.get().getBalance();
        } else {
            throw new IllegalArgumentException("Account not found");
        }
    }
}
