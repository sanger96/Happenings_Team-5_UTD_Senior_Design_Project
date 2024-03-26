package com.services.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.services.api.entity.Club;
import com.services.api.entity.Location;
import com.services.api.repository.LocationRepository;
import java.util.*;

@Service
public class LocationService {
    @Autowired
    private LocationRepository repository;

    public void flush(){
        repository.flush();
    }

    // Create a location
    public Location save(Location location) {
        return repository.save(location);
    }

    // Get all locations
    public List<Location> getAll() {
        return repository.getAll();
    }

    // Get location by ID
    public Location getById(int id) {
        return repository.findById(id).orElse(null);
    }

    // Get a location by name
    public List<Location> getByName(String name) {
        return repository.getByName(name);
    }

    // Get a location by room
    public List<Location> getByRoom(String room) {
        return repository.getByRoom(room);
    }

    // Update a location
    public Location update(Location location) {
       return repository.save(location);
    }

     // Delete a location
     public String delete(Location location) {
        repository.delete(location);
        return "DELETE: " + location.toString();
     }

    // Delete a location by ID
    public String deleteById(int id) {
        Location entityExists = repository.findById(id).orElse(null);
        String toBeDeleted = "DELETE " + id + ": " + (entityExists == null? "ERROR, does not exist" : entityExists).toString();
        repository.deleteById(id);
        return toBeDeleted;
    }

    public Location getByNameAndRoom(String name, String room) {
        return repository.getByNameAndRoom(name, room);
    }
}
