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

import com.services.api.entity.AdminAccount;
import com.services.api.service.AdminAccountService;

@RestController
@RequestMapping("adminaccount")
public class AdminAccountController {
    
    @Autowired
    private AdminAccountService service;

    @PostMapping("/add")
    public AdminAccount add(@RequestBody AdminAccount adminAccount) {
        return service.add(adminAccount);
    }

    @GetMapping("/getById/{id}")
    public AdminAccount getById(@PathVariable int id) {
        return service.getById(id);
    }

    @GetMapping("/getAll")
    public List<AdminAccount> getAll() {
        return service.getAll();
    }
    
    @PutMapping("/update")
    public AdminAccount update(@RequestBody AdminAccount adminAccount) {
        return service.add(adminAccount);
    }
    
    @DeleteMapping("/deleteById/{id}")
    public String deleteById(@PathVariable int id) {
        return service.deleteById(id);
    }

    @DeleteMapping("/delete")
    public String delete(@RequestBody AdminAccount adminAccount) {
        return service.delete(adminAccount);
    }
}
