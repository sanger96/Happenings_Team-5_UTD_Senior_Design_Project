package com.services.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.services.api.entity.Location;
import com.services.api.repository.LocationRepository;

@Service
public class LocationService {
    @Autowired
    private LocationRepository repository;

    public Location saveLocation(Location location) {
        return repository.save(location);
    }

    public Location getLocationById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Location updateLocation(Location location) {
       return repository.save(location);
    }

    public void deleteLocationById(int id) {
        repository.deleteById(id);
     }
}
