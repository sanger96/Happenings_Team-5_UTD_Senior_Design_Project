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

    @PostMapping("/addAppointment")
    public Appointment addAppointment(@RequestBody Appointment appointment) {
        return service.saveAppointment(appointment);
    }

    @PutMapping("/updateAppointment")
    public Appointment updateAppointment(@RequestBody Appointment appointment) {
        return service.saveAppointment(appointment);
    }

    @GetMapping("/getAppointmentById/{id}")
    public Appointment getAppointmentById(@PathVariable int id) {
        return service.getAppointmentById(id);
    }

    @DeleteMapping("/deleteAppointment")
    public void deleteAppointment(@RequestBody Appointment appointment){
        service.deleteAppointment(appointment);
        /* TODO
            Could return String explaining success/failure
            How do we check if it was successful or failed to delete?
        */
    }

    @DeleteMapping("/deleteAppointmentById/{id}")
    public void deleteAppointmentById(@PathVariable int id){
        service.deleteAppointmentById(id);
        /* TODO
            Could return String explaining success/failure
            How do we check if it was successful or failed to delete?
        */
    }

    
}
