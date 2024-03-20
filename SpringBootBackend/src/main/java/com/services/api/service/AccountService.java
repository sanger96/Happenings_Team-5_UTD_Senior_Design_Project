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

    public Account add(Account account) {
        return repository.save(account);
    }

    public Account getById(int id) {
        return repository.findById(id).orElse(null);
    }
    
    public Account getByEmail(String email) {
        return repository.findByEmail(email);
    }

    public List<Account> getAll() {
        return repository.findAll();
    }

    public String deleteById(int id) {
        Account entityExists = repository.findById(id).orElse(null);
        String toBeDeleted = "DELETE " + id + ": " + (entityExists == null? "ERROR, does not exist" : entityExists.toString());
        repository.deleteById(id);
        return toBeDeleted;
    }

    public String delete(Account account) {
        repository.delete(account);
        return "DELETE: "  + account.toString();
    }
}
