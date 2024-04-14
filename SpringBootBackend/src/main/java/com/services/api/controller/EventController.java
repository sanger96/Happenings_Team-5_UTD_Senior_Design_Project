package com.services.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.services.api.entity.Event;
import com.services.api.entity.EventDTO;
import com.services.api.service.EventService;


@RestController
@RequestMapping("event")
public class EventController {
    
    @Autowired
    private EventService service;

    @PostMapping("/add")
    public Event add(@RequestBody Event event) {
        return service.save(event);
    }

    @PostMapping("/createFromForm")
    public Event createFromForm(@RequestBody EventDTO eventDTO) {
        return service.save(eventDTO.getEventName(), eventDTO.getDescription(), eventDTO.getClubID(),
                         eventDTO.getStartTime(), eventDTO.getEndTime(), eventDTO.getLocationName(),
                         eventDTO.getRoom(), eventDTO.getPhotoSubDirectory());
}
    @PutMapping("/update")
    public Event updateEvent(@RequestBody Event event) {
        return service.save(event);
    }

    @GetMapping("/getById/{id}")
    public Event getById(@PathVariable int id) {
        return service.getById(id);
    }

    @GetMapping("/getByName/{name}")
    public Event getByName(@PathVariable String name){
        return service.getByName(name);
    }

    @GetMapping("/getAll")
    public List<Event> getAll(){
        return service.getAll();
    }

    @GetMapping("/getCampusEvents")
    public List<Event> getCampusEvents(){
        return service.getCampusEvents();
    }

    @GetMapping("/getClubEvents")
    public List<Event> getClubEvents(){
        return service.getClubEvents();
    }

    @GetMapping("/rsvpCount/{id}")
    public int rsvpCount(@PathVariable int id)
    {
        return service.rsvpCount(id);
    }

    @PostMapping("/joinClub/{eventID}_{clubID}")
    public Event joinClub(@PathVariable int eventID, @PathVariable int clubID)
    {
        return service.joinClub(eventID, clubID);
    }

    @PostMapping("/quitClub/{eventID}")
    public Event quitClub(@PathVariable int eventID)
    {
        return service.quitClub(eventID);
    }

    @GetMapping("/existsByName/{name}")
    public Integer existsByName(@PathVariable String name){
        return service.existsByName(name);
    }

    @DeleteMapping("/delete")
    public String delete(@RequestBody Event event){
        return service.delete(event);
    }

    @DeleteMapping("/deleteById/{id}")
    public String deleteById(@PathVariable int id){
        return service.deleteById(id);
    }

    @DeleteMapping("/deleteExpired")
    public String deleteExpired(){
        return service.deleteExpired();
    }
}
