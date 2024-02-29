package com.services.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.services.api.repository.UserAccountRepository;
import com.services.api.entity.UserAccount;

@Service
public class UserAccountService {
    
    @Autowired
    private UserAccountRepository repository;

    public UserAccount saveUserAccount(UserAccount userAccount) {
        return repository.save(userAccount);
    }

    public UserAccount getUserAccountById(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<UserAccount> getAllUserAccounts() {
        return repository.findAll();
    }

    public String deleteUserAccountById(int id) {
        String toBeDeleted = "DELETE " + id + ": " + repository.findById(id).orElse(null).toString();
        repository.deleteById(id);
        return toBeDeleted;
    }

    public String deleteUserAccount(UserAccount userAccount) {
        repository.delete(userAccount);
        return "DELETE: "  + userAccount.toString();
    }
}
