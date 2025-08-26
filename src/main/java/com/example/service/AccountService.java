package com.example.service;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.entity.Account;
import com.example.exception.AccountFoundException;
import com.example.exception.AccountNotFoundException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @PostConstruct
    public void initTestUser() {
        if (!accountRepository.existsByUsername("testuser1")) {
            Account testUser = new Account();
            testUser.setAccountId(9999); 
            testUser.setUsername("testuser1");
            testUser.setPassword("password");
            accountRepository.save(testUser);
        }
    }

    public Account register(Account account) throws AccountFoundException {
        if (account.getUsername() == null || account.getUsername().isBlank() ||
            account.getPassword() == null || account.getPassword().length() < 4) {
            throw new IllegalArgumentException("Bad request");
        }

        if (accountRepository.existsByUsername(account.getUsername())) {
            throw new AccountFoundException(account);
        }

        return accountRepository.save(account);
    }

    public Account login(Account account) throws AccountNotFoundException {
        Account existing = accountRepository.findByUsername(account.getUsername())
                .orElseThrow(() -> new AccountNotFoundException(account));

        if (!existing.getPassword().equals(account.getPassword())) {
            throw new AccountNotFoundException(account);
        }

        return existing;
    }

    public Optional<Account> getAccountById(int accountId) {
        return accountRepository.findById(accountId);
    }

    public boolean existsByUsername(String username) {
        return accountRepository.existsByUsername(username);
    }
}
