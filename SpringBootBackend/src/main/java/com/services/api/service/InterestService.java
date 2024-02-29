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

    public Interest saveInterest(Interest interest) {
        return repository.save(interest);
    }

    public List<Interest> saveInterests(List<Interest> interests) {
        return repository.saveAll(interests);
    }

    public Interest getInterestById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Interest getInterestByName(String name) {
        return repository.findByName(name);
    }

    public List<Interest> getAllInterests() {
        return repository.findAll();
    }

    public String deleteInterestById(int id) {
        String toBeDeleted = "DELETE " + id + ": " + repository.findById(id).orElse(null).toString();
        repository.deleteById(id);
        return toBeDeleted;
    }

    public String deleteInterest(Interest interest) {
        repository.delete(interest);
        return "DELETE: "  + interest.toString();
    }
}

