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

    public Event createFromForm
    (String eventName, Optional<Integer> clubLeaderID,
     String startTime, String endTime, String locationName, Optional<String> room){
        // Create a new Location, flush to DB
        Location eventLocation;
        if(room.isPresent())
            eventLocation = new Location(locationName, room.get());
        else
            eventLocation = new Location(locationName);
        eventLocation = locationService.save(eventLocation);
        locationService.flush();

        // Create a new Appointment, flush to DB
        LocalDateTime newStartTime = LocalDateTime.parse(startTime);
        LocalDateTime newEndTime = LocalDateTime.parse(endTime);
        Appointment eventAppointment = new Appointment(newStartTime, newEndTime, "event", eventLocation);
        eventAppointment = appointmentService.save(eventAppointment);
        appointmentService.flush();

        // Create a new Event, save to DB
        Event newEvent;
        if(clubLeaderID.isPresent())
        {
            List<Club> tmp = clubService.getByLeaderId(clubLeaderID.get());
            Club eventClub = tmp.get(0);
            newEvent = new Event(eventName, eventName + "Gallery", eventAppointment, eventClub);
        }
        else
            newEvent = new Event(eventName, eventName + "Gallery", eventAppointment);

        return repository.save(newEvent);
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
