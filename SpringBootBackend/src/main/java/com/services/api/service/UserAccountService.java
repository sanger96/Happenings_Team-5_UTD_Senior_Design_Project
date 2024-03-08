package com.services.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.services.api.repository.UserAccountRepository;
import com.services.api.entity.UserAccount;
import com.services.api.entity.Appointment;

@Service
public class UserAccountService {
    
    @Autowired
    private UserAccountRepository repository;

    public UserAccount add(UserAccount userAccount) {
        return repository.save(userAccount);
    }

    public UserAccount getById(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<UserAccount> getAll() {
        return repository.findAll();
    }

    public String deleteById(int id) {
        UserAccount entityExists = repository.findById(id).orElse(null);
        String toBeDeleted = "DELETE " + id + ": " + (entityExists == null? "ERROR, does not exist" : entityExists.toString());
        repository.deleteById(id);
        return toBeDeleted;
    }

    public String delete(UserAccount userAccount) {
        repository.delete(userAccount);
        return "DELETE: "  + userAccount.toString();
    }

    public List<Integer> getAllAppointments(int id) {
        return repository.getAllAppointments(id);
    }
}
