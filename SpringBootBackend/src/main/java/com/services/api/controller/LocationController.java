package com.services.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.services.api.entity.Club;
import com.services.api.entity.Location;
import com.services.api.service.LocationService;

import org.springframework.web.bind.annotation.PostMapping;
import java.util.*;


@RestController
@RequestMapping("location")
public class LocationController {
    
    @Autowired
    private LocationService service;

    @PostMapping("/add")
    public Location add(@RequestBody Location location) {
        return service.save(location);
    }

    @GetMapping("/getAll")
    public List<Location> getAll() {
        return service.getAll();
    }

    @GetMapping("/getById/{id}")
    public Location getById(@PathVariable int id) {
        return service.getById(id);
    }

    @GetMapping("/getByName/{name}")
    public List<Location> getByName(@PathVariable String name) {
        return service.getByName(name);
    }

    @GetMapping("/getByBuilding/{building}")
    public List<Location> getByBuilding(@PathVariable String building) {
        return service.getByBuilding(building);
    }

    @GetMapping("/getByRoom/{room}")
    public List<Location> getByRoom(@PathVariable String room) {
        return service.getByRoom(room);
    }

    @PostMapping("/update")
    public Location update(@RequestBody Location location){
       return service.update(location);

    }

    @PostMapping("/delete")
    public void delete(@RequestBody Location location){
        service.delete(location);
    }

    @PostMapping("/deleteById/{id}")
    public String deleteById(@PathVariable int id){
         return service.deleteById(id);

    }
}