package com.services.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.services.api.entity.Appointment;
import com.services.api.service.AppointmentService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("appointment")
public class AppointmentController {
    
    @Autowired
    private AppointmentService service;

    @PostMapping("/add")
    public Appointment add(@RequestBody Appointment appointment) {
        return service.save(appointment);
    }

    @PutMapping("/update")
    public Appointment update(@RequestBody Appointment appointment) {
        return service.save(appointment);
    }

    @GetMapping("/getById/{id}")
    public Appointment getById(@PathVariable int id) {
        return service.getById(id);
    }

    @DeleteMapping("/delete")
    public String delete(@RequestBody Appointment appointment){
        return service.delete(appointment);
    }

    @DeleteMapping("/deleteById/{id}")
    public String deleteById(@PathVariable int id){
        return service.deleteById(id);
    }
}
