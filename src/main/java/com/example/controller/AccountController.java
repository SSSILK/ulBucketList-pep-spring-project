package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.exception.AccountFoundException;
import com.example.exception.AccountNotFoundException;
import com.example.service.AccountService;

@RestController
@RequestMapping("/")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        try {
            Account saved = accountService.register(account);
            return ResponseEntity.ok(saved); 
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); 
        } catch (AccountFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); 
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        try {
            Account loggedIn = accountService.login(account);
            return ResponseEntity.ok(loggedIn); 
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); 
        }
    }
}
