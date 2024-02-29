package com.services.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.services.api.entity.Interest;

public interface InterestRepository extends JpaRepository<Interest, Integer> {
    public Interest findByName(String name);
}
    

