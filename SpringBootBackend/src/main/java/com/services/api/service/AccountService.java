package com.services.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.services.api.entity.Account;
import com.services.api.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    private AccountRepository repository;

    public Account saveAccount(Account account) {
        return repository.save(account);
    }

    public Account getAccountById(int id) {
        return repository.findById(id).orElse(null);
    }
    
    public Account getAccountByEmail(String email) {
        return repository.findByEmail(email);
    }

    public List<Account> getAllAccounts() {
        return repository.findAll();
    }

    public String deleteAccountById(int id) {
        String toBeDeleted = "DELETE " + id + ": " + repository.findById(id).orElse(null).toString();
        repository.deleteById(id);
        return toBeDeleted;
    }

    public String deleteAccount(Account account) {
        repository.delete(account);
        return "DELETE: "  + account.toString();
    }
}
