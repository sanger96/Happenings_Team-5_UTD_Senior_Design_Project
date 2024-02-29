package com.services.api.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.services.api.repository.AdminAccountRepository;
import com.services.api.entity.AdminAccount;

@Service
public class AdminAccountService {
    
    @Autowired
    private AdminAccountRepository repository;

    public AdminAccount saveAdminAccount(AdminAccount adminAccount) {
        return repository.save(adminAccount);
    }

    public AdminAccount getAdminAccountById(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<AdminAccount> getAllAdminAccounts() {
        return repository.findAll();
    }

    public String deleteAdminAccountById(int id) {
        String toBeDeleted = "DELETE " + id + ": " + repository.findById(id).orElse(null).toString();
        repository.deleteById(id);
        return toBeDeleted;
    }

    public String deleteAdminAccount(AdminAccount adminAccount) {
        repository.delete(adminAccount);
        return "DELETE: "  + adminAccount.toString();
    }
}
