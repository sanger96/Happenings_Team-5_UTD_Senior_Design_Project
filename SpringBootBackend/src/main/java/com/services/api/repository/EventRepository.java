package com.services.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.services.api.entity.Event;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {

        // Get all events by club ID
    @Query(value = "SELECT * FROM event e WHERE e.clubID = ?1", nativeQuery = true)
    List<Event> getEventsByID(Integer id);
    
}
