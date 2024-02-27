package com.services.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.services.api.entity.Club;
import com.services.api.service.ClubService;

import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class ClubController {
    
    @Autowired
    private ClubService service;

    @PostMapping("/addClub")
    public Club addClub(@RequestBody Club club) {
        return service.saveClub(club);
    }

    @GetMapping("/getClubById/{id}")
    public Club getClubById(@PathVariable int id) {
        return service.getClubById(id);
    }

    @PostMapping("/updateClub")
    public Club updateClub(@RequestBody Club club){
       return service.updateClub(club);

    }

    @PostMapping("/deleteClubById/{id}")
    public void deleteClubById(@PathVariable int id){
         service.deleteClubById(id);

    }
}