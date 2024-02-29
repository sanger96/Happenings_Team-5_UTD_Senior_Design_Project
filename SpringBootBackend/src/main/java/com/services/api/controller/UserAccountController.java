package com.services.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.services.api.entity.UserAccount;
import com.services.api.service.UserAccountService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
public class UserAccountController {
    
    @Autowired
    private UserAccountService service;

    @PostMapping("/addUserAccount")
    public UserAccount addUserAccount(@RequestBody UserAccount userAccount) {
        return service.saveUserAccount(userAccount);
    }

    @GetMapping("/getUserAccountById/{id}")
    public UserAccount getUserAccountById(@PathVariable int id) {
        return service.getUserAccountById(id);
    }

    @GetMapping("/getAllUserAccounts")
    public List<UserAccount> getAllUserAccounts() {
        return service.getAllUserAccounts();
    }

    @PutMapping("/updateUserAccount")
    public UserAccount updateUserAccount(@RequestBody UserAccount userAccount) {
        return service.saveUserAccount(userAccount);
    }

    @DeleteMapping("/deleteUserAccountById/{id}")
    public String deleteUserAccountById(@PathVariable int id) {
        return service.deleteUserAccountById(id);
    }

    @DeleteMapping("/deleteUserAccount")
    public String deleteUserAccount(@RequestBody UserAccount userAccount) {
        return service.deleteUserAccount(userAccount);
    }
}
