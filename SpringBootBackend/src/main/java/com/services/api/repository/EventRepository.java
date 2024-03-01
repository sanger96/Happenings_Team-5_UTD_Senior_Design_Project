package com.services.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.services.api.entity.Event;

public interface EventRepository extends JpaRepository<Event, Integer> {
    
}
