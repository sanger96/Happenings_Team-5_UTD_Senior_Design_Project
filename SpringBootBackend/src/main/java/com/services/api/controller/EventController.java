package com.services.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.services.api.entity.Event;
import com.services.api.service.EventService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("event")
public class EventController {
    
    @Autowired
    private EventService service;

    @PostMapping("/addEvent")
    public Event addEvent(@RequestBody Event event) {
        return service.saveEvent(event);
    }

    @PutMapping("/updateEvent")
    public Event updateEvent(@RequestBody Event event) {
        return service.saveEvent(event);
    }

    @GetMapping("/getEventById/{id}")
    public Event getEventById(@PathVariable int id) {
        return service.getEventById(id);
    }

    @DeleteMapping("/deleteEvent")
    public void deleteEvent(@RequestBody Event event){
        service.deleteEvent(event);
        /* TODO
            Could return String explaining success/failure
            How do we check if it was successful or failed to delete?
        */
    }

    @DeleteMapping("/deleteEventById/{id}")
    public void deleteEventById(@PathVariable int id){
        service.deleteEventById(id);
        /* TODO
            Could return String explaining success/failure
            How do we check if it was successful or failed to delete?
        */
    }

    
}
