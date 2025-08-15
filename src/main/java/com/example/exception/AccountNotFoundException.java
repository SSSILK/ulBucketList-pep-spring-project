package com.example.exception;
import com.example.entity.Account;

public class AccountNotFoundException extends Exception {

    public Account account;

    public AccountNotFoundException(Account account) {
        super("The Account " + account + " does not exist within our database");
        this.account = account;
    }
}
