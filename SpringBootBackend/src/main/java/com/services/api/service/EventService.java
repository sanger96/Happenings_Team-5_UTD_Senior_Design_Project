package com.services.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.services.api.entity.Event;
import com.services.api.repository.EventRepository;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Basic;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Service
public class EventService {
    @Autowired
    private EventRepository repository;

    // Create/Update an Event
    public Event save(Event event) {
        return repository.save(event);
    }

    public Event createFromForm
    (String eventName, Optional<Integer> clubLeaderID, Optional<String> clubName,
     String startTime, String endTime, String locationName, Optional<String> room){
        return new Event();
    }

    // Get an Event
    public Event getById(int id) {
        return repository.findById(id).orElse(null);
    }

    // Get all Events
    public List<Event> getAll() {
        return repository.findAll();
    }

    // Get number of attending users at event by ID
    public Integer getRSVPcount(Integer id){
        return repository.getRSVPcount(id);
    }

    // Check if event exists by name
    public Integer existsByName(String name){
        Integer response = repository.existsByName(name);
        if(response == null){
            return 0;
        }
        return response;
    }

    // Delete by Event
    public String delete(Event event){
        repository.delete(event);
        return "DELETE: " + event.toString();
        /* TODO
            Could return String explaining success/failure
            How do we check if it was successful or failed to delete?
        */
    }

    // Delete by Event ID
    public String deleteById(int id){
        Event entityExists = repository.findById(id).orElse(null);
        String toBeDeleted = "DELETE " + id + ": " + (entityExists == null? "ERROR, does not exist" : entityExists.toString());
        repository.deleteById(id);
        return toBeDeleted;
    }
    
}
