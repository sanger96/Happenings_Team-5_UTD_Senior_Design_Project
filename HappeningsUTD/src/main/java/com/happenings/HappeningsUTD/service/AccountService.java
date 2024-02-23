package com.happenings.HappeningsUTD.service;

import com.happenings.HappeningsUTD.entity.Account;
import com.happenings.HappeningsUTD.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
