package com.services.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.services.api.entity.Interest;
import com.services.api.service.InterestService;

@RestController
@RequestMapping("interest")
public class InterestController {
    
    @Autowired
    private InterestService service;

    @PostMapping("/add")
    public Interest add(@RequestBody Interest interest) {
        return service.add(interest);
    }

    @PostMapping("/addMany")
    public List<Interest> addMany(@RequestBody List<Interest> interests) {
        return service.addMany(interests);
    }

    @GetMapping("/getById/{id}")
    public Interest getById(@PathVariable int id) {
        return service.getById(id);
    }

    @GetMapping("/getByName/{name}")
    public Interest getByName(@PathVariable String name) {
        return service.getByName(name);
    }

    @GetMapping("/getAll")
    public List<Interest> getAll() {
        return service.getAll();
    }

    @PutMapping("/update")
    public Interest update(@RequestBody Interest interest) {
        return service.add(interest);
    }

    @DeleteMapping("/deleteById/{id}")
    public String deleteById(@PathVariable int id) {
        return service.deleteById(id);
    }

    @DeleteMapping("/delete")
    public String delete(@RequestBody Interest interest) {
        return service.delete(interest);
    }
}
