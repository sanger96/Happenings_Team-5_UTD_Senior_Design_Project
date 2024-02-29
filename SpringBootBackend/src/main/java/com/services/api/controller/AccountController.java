package com.services.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.services.api.entity.Account;
import com.services.api.service.AccountService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

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

    @GetMapping("/getAccountByEmail/{email}")
    public Account getAccountByEmail(@PathVariable String email) {
        return service.getAccountByEmail(email);
    }

    @GetMapping("/getAllAccounts")
    public List<Account> getAllAccounts() {
        return service.getAllAccounts();
    }

    @PutMapping("/updateAccount")
    public Account updateAdminAccount(@RequestBody Account account) {
        return service.saveAccount(account);
    }
    
    @DeleteMapping("/deleteAccountById/{id}")
    public String deleteAccountById(@PathVariable int id) {
        return service.deleteAccountById(id);
    }

    @DeleteMapping("/deleteAccount")
    public String deleteAccount(@RequestBody Account account) {
        return service.deleteAccount(account);
    }
}
