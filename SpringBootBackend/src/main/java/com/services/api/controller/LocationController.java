package com.services.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.services.api.entity.Location;
import com.services.api.service.LocationService;

import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class LocationController {
    
    @Autowired
    private LocationService service;

    @PostMapping("/addLocation")
    public Location addAccount(@RequestBody Location location) {
        return service.saveLocation(location);
    }

    @GetMapping("/getLocationById/{id}")
    public Location getAccountById(@PathVariable int id) {
        return service.getLocationById(id);
    }
}