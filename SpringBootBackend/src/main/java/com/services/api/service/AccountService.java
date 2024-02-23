package com.services.api.service;

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
}
