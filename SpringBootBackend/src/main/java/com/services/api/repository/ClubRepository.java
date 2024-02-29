package com.services.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.services.api.entity.Club;

import java.util.*;

public interface ClubRepository extends JpaRepository<Club, Integer> {
    
    // Get a club by leader id
    @Query(value = "SELECT * FROM club c WHERE c.leaderID = ?1", nativeQuery = true)
    List<Club> getByLeaderId(Integer id);

    // Get all clubs
    @Query(value = "SELECT * FROM club", nativeQuery = true)
    List<Club> getAll();

}
    
