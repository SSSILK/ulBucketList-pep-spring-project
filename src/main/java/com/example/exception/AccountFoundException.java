package com.example.exception;

import com.example.entity.Account;

public class AccountFoundException extends Exception {
    public Account account;
    
   public AccountFoundException(Account account){
   super("The Account " + account + " already exists");
  
    
   }
    

    
    
}
