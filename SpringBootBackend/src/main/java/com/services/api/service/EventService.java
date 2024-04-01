package com.services.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import com.services.api.entity.Event;
import com.services.api.entity.Location;
import com.services.api.entity.Appointment;
import com.services.api.entity.Club;
import com.services.api.repository.EventRepository;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Basic;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Service
public class EventService {
    @Autowired
    private EventRepository repository;
    @Autowired
    private LocationService locationService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private ClubService clubService;

    // Create/Update an Event
    public Event save(Event event) {
        return repository.save(event);
    }

    public Event save
    (String eventName, String description, Optional<Integer> clubID,
    LocalDateTime startTime, LocalDateTime endTime, String locationName, Optional<String> room){
        //TODO: can we send detailed error messages back without changing the return type of the methods?
        //TODO: try catch blocks for each create and save calls, returning a ResponseEntity<Event> Object containing necessary exception info

        // Create a new Location
        Location eventLocation = new Location(locationName, room.isPresent()? room.get() : null);
        eventLocation = locationService.save(eventLocation);

        // Create a new Appointment
        Appointment eventAppointment = new Appointment(startTime, endTime, "event", eventLocation);
        // TODO: Throw custom "appointment conflict" exception detailing the dates and times of conflicts
        eventAppointment = appointmentService.save(eventAppointment);
        // Temporary solution for appointment conflict resolution
        if(eventAppointment == null)
            return null;

        // Create a new Event
        Event newEvent;
        // TODO:Throw custom "club does not exist" exception if the club cannot be retrieved from DB
        Club eventClub = clubID.isPresent()? clubService.getById(clubID.get()) : null;
        newEvent = new Event(eventName, description, eventName + "Gallery", eventAppointment, eventClub);

        return repository.save(newEvent);
    }

    // Get an Event
    public Event getById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Event getByName(String name) {
        return repository.findByName(name);
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

    // Delete all expired Events
    public String deleteExpired(){
        List<Integer> expiredEventIDs = repository.getExpiredIds();

        repository.deleteAllById(expiredEventIDs);
        String result = expiredEventIDs.size() + " expired events were deleted.";

        System.out.println(result);
        return result;
    }
    
}
