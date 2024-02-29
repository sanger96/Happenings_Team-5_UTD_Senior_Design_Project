package com.services.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.services.api.entity.Account;
import com.services.api.service.AccountService;

@RestController
@RequestMapping("account")
public class AccountController {
    
    @Autowired
    private AccountService service;

    @PostMapping("/add")
    public Account add(@RequestBody Account account) {
        return service.add(account);
    }

    @GetMapping("/getById/{id}")
    public Account getById(@PathVariable int id) {
        return service.getById(id);
    }

    @GetMapping("/getByEmail/{email}")
    public Account getByEmail(@PathVariable String email) {
        return service.getByEmail(email);
    }

    @GetMapping("/getAll")
    public List<Account> getAll() {
        return service.getAll();
    }

    @PutMapping("/update")
    public Account update(@RequestBody Account account) {
        return service.add(account);
    }
    
    @DeleteMapping("/deleteById/{id}")
    public String deleteById(@PathVariable int id) {
        return service.deleteById(id);
    }

    @DeleteMapping("/delete")
    public String delete(@RequestBody Account account) {
        return service.delete(account);
    }
}
