package com.services.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.services.api.entity.Event;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {

    // Get all events by club ID
    @Query(value = "SELECT * FROM event e WHERE e.clubID = ?1", nativeQuery = true)
    List<Event> getEventsByID(Integer id);
    
    // Check if event exists by name
    @Query(value = "SELECT 1 FROM event WHERE name = ?1", nativeQuery = true)
    Integer existsByName(String name);

    // Get number of useraccounts attending an event by event ID
    @Query(value = "SELECT COUNT(*) FROM attends a WHERE a.eventID = ?1", nativeQuery = true)
    Integer getRSVPcount(Integer id);

    @Query(value = "SELECT * FROM event WHERE name = ?1 LIMIT 1", nativeQuery = true)
    Event findByName(String name);

   

}
