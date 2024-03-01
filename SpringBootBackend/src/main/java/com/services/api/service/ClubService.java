package com.services.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.services.api.entity.Club;
import com.services.api.repository.ClubRepository;
import java.util.*;


@Service
public class ClubService {
    @Autowired
    private ClubRepository repository;

    // Create a club
    public Club add(Club club) {
        return repository.save(club);
    }

    // Get all clubs
    public List<Club> getAll() {
        return repository.getAll();
    }
    // Get a club
    public Club getById(int id) {
        return repository.findById(id).orElse(null);
    }

    // Get a club by LeaderID
    public List<Club> getByLeaderId(int id) {
        return repository.getByLeaderId(id);
    }

    // Update a club
    public Club update(Club club) {
       return repository.save(club);
    }

    // Delete a club
    public void delete(Club club) {
        repository.delete(club);
     }

    // Delete a club by ID
    public String deleteById(int id) {
        Club entityExists = repository.findById(id).orElse(null);
        String toBeDeleted = "DELETE " + id + ": " + (entityExists == null? "ERROR, does not exist" : entityExists.toString());
        repository.deleteById(id);
        return toBeDeleted;
     }

}