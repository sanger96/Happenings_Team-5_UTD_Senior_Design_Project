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

    public AdminAccount add(AdminAccount adminAccount) {
        return repository.save(adminAccount);
    }

    public AdminAccount getById(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<AdminAccount> getAll() {
        return repository.findAll();
    }

    public String deleteById(int id) {
        AdminAccount entityExists = repository.findById(id).orElse(null);
        String toBeDeleted = "DELETE " + id + ": " + (entityExists == null? "ERROR, does not exist" : entityExists.toString());
        repository.deleteById(id);
        return toBeDeleted;
    }

    public String delete(AdminAccount adminAccount) {
        repository.delete(adminAccount);
        return "DELETE: "  + adminAccount.toString();
    }
}
