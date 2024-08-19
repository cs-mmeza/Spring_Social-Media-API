package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.UsernameAlreadyExistsException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account findAccountByUsername(String username) throws UsernameAlreadyExistsException {
        return accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameAlreadyExistsException("Account with username " + username + " was not found."));
    }
    
    public Account register(Account newAccount) throws IllegalArgumentException {
        if (newAccount.getUsername() == null || newAccount.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username cannot be blank");
        }
        if (newAccount.getPassword() == null || newAccount.getPassword().length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters long");
        }
        if (accountRepository.findByUsername(newAccount.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }
        return accountRepository.save(newAccount);
    }

    public Account login(String username, String password) {
        return accountRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));
    }
        
}
