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
    public Appointment saveAppointment(Appointment appointment) {
        return repository.save(appointment);
    }

    // Get an Appointment
    public Appointment getAppointmentById(int id) {
        return repository.findById(id).orElse(null);
    }

    // Delete by Appointment
    public void deleteAppointment(Appointment appointment){
        repository.delete(appointment);
        /* TODO
            Could return String explaining success/failure
            How do we check if it was successful or failed to delete?
        */
    }

    // Delete by Appointment ID
    public void deleteAppointmentById(int id){
        repository.deleteById(id);
        /* TODO
            Could return String explaining success/failure
            How do we check if it was successful or failed to delete?
        */
    }
    
}
