package com.services.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.services.api.entity.Event;
import com.services.api.repository.EventRepository;

@Service
public class EventService {
    @Autowired
    private EventRepository repository;

    // Create/Update an Event
    public Event saveEvent(Event event) {
        return repository.save(event);
    }

    // Get an Event
    public Event getEventById(int id) {
        return repository.findById(id).orElse(null);
    }

    // Delete by Event
    public void deleteEvent(Event event){
        repository.delete(event);
        /* TODO
            Could return String explaining success/failure
            How do we check if it was successful or failed to delete?
        */
    }

    // Delete by Event ID
    public void deleteEventById(int id){
        repository.deleteById(id);
        /* TODO
            Could return String explaining success/failure
            How do we check if it was successful or failed to delete?
        */
    }
    
}
