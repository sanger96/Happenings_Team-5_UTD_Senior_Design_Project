package com.services.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.services.api.entity.Club;
import com.services.api.repository.ClubRepository;

@Service
public class ClubService {
    @Autowired
    private ClubRepository repository;

    public Club saveClub(Club club) {
        return repository.save(club);
    }

    public Club getClubById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Club updateClub(Club club) {
       return repository.save(club);
    }

    public void deleteClubById(int id) {
        repository.deleteById(id);
     }
}