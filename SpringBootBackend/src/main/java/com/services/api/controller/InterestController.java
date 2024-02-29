package com.services.api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.services.api.entity.Interest;
import com.services.api.service.InterestService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
public class InterestController {
    
    @Autowired
    private InterestService service;

    @PostMapping("/addInterest")
    public Interest addInterest(@RequestBody Interest interest) {
        return service.saveInterest(interest);
    }

    @PostMapping("/addInterests")
    public List<Interest> addInterests(@RequestBody List<Interest> interests) {
        return service.saveInterests(interests);
    }

    @GetMapping("/getInterestById/{id}")
    public Interest getInterestById(@PathVariable int id) {
        return service.getInterestById(id);
    }

    @GetMapping("/getInterestByName/{name}")
    public Interest getInterestByName(@PathVariable String name) {
        return service.getInterestByName(name);
    }

    @GetMapping("/getAllInterests")
    public List<Interest> getAllInterests() {
        return service.getAllInterests();
    }

    @PutMapping("/updateInterest")
    public Interest updateInterest(@RequestBody Interest interest) {
        return service.saveInterest(interest);
    }

    @DeleteMapping("/deleteInterestById/{id}")
    public String deleteInterestById(@PathVariable int id) {
        return service.deleteInterestById(id);
    }

    @DeleteMapping("/deleteInterest")
    public String deleteInterest(@RequestBody Interest interest) {
        return service.deleteInterest(interest);
    }
}
