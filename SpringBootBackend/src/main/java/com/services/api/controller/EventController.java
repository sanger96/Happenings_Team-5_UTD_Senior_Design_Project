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

    @PostMapping("/add")
    public Event addEvent(@RequestBody Event event) {
        return service.save(event);
    }

    @PutMapping("/update")
    public Event updateEvent(@RequestBody Event event) {
        return service.save(event);
    }

    @GetMapping("/getById/{id}")
    public Event getById(@PathVariable int id) {
        return service.getById(id);
    }

    @DeleteMapping("/delete")
    public String delete(@RequestBody Event event){
        return service.delete(event);
    }

    @DeleteMapping("/deleteById/{id}")
    public String deleteById(@PathVariable int id){
        return service.deleteById(id);
    }
}
