package com.services.api.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.services.api.repository.InterestRepository;

import com.services.api.entity.Interest;

@Service
public class InterestService {
    
    @Autowired
    private InterestRepository repository;

    public Interest add(Interest interest) {
        return repository.save(interest);
    }

    public List<Interest> addMany(List<Interest> interests) {
        return repository.saveAll(interests);
    }

    public Interest getById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Interest getByName(String name) {
        return repository.findByName(name);
    }

    public List<Interest> getAll() {
        return repository.findAll();
    }

    public String deleteById(int id) {
        Interest entityExists = repository.findById(id).orElse(null);
        String toBeDeleted = "DELETE " + id + ": " + (entityExists == null? "ERROR, does not exist" : entityExists.toString());
        repository.deleteById(id);
        return toBeDeleted;
    }

    public String delete(Interest interest) {
        repository.delete(interest);
        return "DELETE: "  + interest.toString();
    }
}

