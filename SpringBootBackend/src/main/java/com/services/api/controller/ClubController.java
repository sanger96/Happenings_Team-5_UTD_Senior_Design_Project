package com.services.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.services.api.entity.Club;
import com.services.api.entity.Event;
import com.services.api.repository.EventRepository;
import com.services.api.service.ClubService;
import com.services.api.service.EventService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.*;


@RestController
@RequestMapping("club")
public class ClubController {
    
    @Autowired
    private ClubService service;

    @GetMapping("/getEventsById/{id}")
    public List<Event> getEvents(@PathVariable int id) {
        return service.getEventsById(service.getById(id));
    }

    @PostMapping("/add")
    public Club add(@RequestBody Club club) {
        return service.add(club);
    }

    @GetMapping("/getAll")
    public List<Club> getAll() {
        return service.getAll();
    }

    @GetMapping("/getById/{id}")
    public Club getById(@PathVariable int id) {
        return service.getById(id);
    }

    @GetMapping("/getByLeaderId/{id}")
    public List<Club> getByLeaderId(@PathVariable int id) {
        return service.getByLeaderId(id);
    }

    
    @PutMapping("/update")
    public Club update(@RequestBody Club club){
       return service.update(club);

    }

    @DeleteMapping("/delete")
    public String delete(@RequestBody Club club){
        return service.delete(club);
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable int id){
         service.deleteById(id);

    }

    

}