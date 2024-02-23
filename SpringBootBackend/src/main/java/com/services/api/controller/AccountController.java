package com.services.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.services.api.entity.Account;
import com.services.api.service.AccountService;

import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class AccountController {
    
    @Autowired
    private AccountService service;

    @PostMapping("/addAccount")
    public Account addAccount(@RequestBody Account account) {
        return service.saveAccount(account);
    }

    @GetMapping("/getAccountById/{id}")
    public Account getAccountById(@PathVariable int id) {
        return service.getAccountById(id);
    }
}
