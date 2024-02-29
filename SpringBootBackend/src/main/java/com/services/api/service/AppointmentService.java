package com.services.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.services.api.entity.Appointment;
import com.services.api.repository.AppointmentRepository;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository repository;

    // Create/Update an Appointment
    public Appointment save(Appointment appointment) {
        return repository.save(appointment);
    }

    // Get an Appointment
    public Appointment getById(int id) {
        return repository.findById(id).orElse(null);
    }

    // Delete by Appointment
    public String delete(Appointment appointment){
        repository.delete(appointment);
        return "DELETE: " + appointment.toString();
        /* TODO
            Could return String explaining success/failure
            How do we check if it was successful or failed to delete?
        */
    }

    // Delete by Appointment ID
    public String deleteById(int id){
        String entityExists = repository.findById(id).orElse(null).toString();
        String toBeDeleted = "DELETE " + id + ": " + (entityExists == null? "ERROR, does not exist" : entityExists);
        repository.deleteById(id);
        return toBeDeleted;
    }
    
}
