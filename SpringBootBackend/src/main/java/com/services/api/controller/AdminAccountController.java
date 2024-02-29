package com.services.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.services.api.entity.AdminAccount;
import com.services.api.service.AdminAccountService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
public class AdminAccountController {
    
    @Autowired
    private AdminAccountService service;

    @PostMapping("/addAdminAccount")
    public AdminAccount addAdminAccount(@RequestBody AdminAccount adminAccount) {
        return service.saveAdminAccount(adminAccount);
    }

    @GetMapping("/getAdminAccountById/{id}")
    public AdminAccount getAdminAccountById(@PathVariable int id) {
        return service.getAdminAccountById(id);
    }

    @GetMapping("/getAllAdminAccounts")
    public List<AdminAccount> getAllAdminAccounts() {
        return service.getAllAdminAccounts();
    }
    
    @PutMapping("/updateAdminAccount")
    public AdminAccount updateAdminAccount(@RequestBody AdminAccount adminAccount) {
        return service.saveAdminAccount(adminAccount);
    }
    
    @DeleteMapping("/deleteAdminAccountById/{id}")
    public String deleteAdminAccountById(@PathVariable int id) {
        return service.deleteAdminAccountById(id);
    }

    @DeleteMapping("/deleteAdminAccount")
    public String deletAdminAccount(@RequestBody AdminAccount adminAccount) {
        return service.deleteAdminAccount(adminAccount);
    }
}
