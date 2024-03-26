package com.services.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.services.api.entity.Appointment;
import com.services.api.repository.AppointmentRepository;

import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository repository;

    // Create/Update an Appointment
    public Appointment save(Appointment appointment) {

        // Check for conflicting appointments (prev in AppointmentController.java)

        /* TODO:
        For some appointments that are scraped from the UTD website, the start and end times
        are identical (00:00:00) because there really is no start or end time. Thus, I think 
        checking for a minimum event length must happen in the front end.
         */ 
        List<Appointment> allCurrAppointments = getAll();
        for (Appointment apt : allCurrAppointments) {

            if ((appointment.getLocation().getName().equals(apt.getLocation().getName()) &&
            appointment.getLocation().getRoom().equals(apt.getLocation().getRoom())) && 
            !doAppointmentsOverlap(appointment, apt)) {
            
                System.out.println(appointment.toString() + "\nCONFLICTS WITH: \n" + apt.toString());
                return null;
            }
        }

        return repository.save(appointment);
    }

    private boolean doAppointmentsOverlap(Appointment appointment1, Appointment appointment2) {

        // Check if the start time of appointment1 falls within the time range of appointment2
        if (appointment1.getStartTime().isAfter(appointment2.getStartTime()) && appointment1.getStartTime().isBefore(appointment2.getEndTime())) {
            return true;
        }
    
        // Check if the end time of appointment1 falls within the time range of appointment2
        if (appointment1.getEndTime().isAfter(appointment2.getStartTime()) && appointment1.getEndTime().isBefore(appointment2.getEndTime())) {
            return true;
        }
    
        // Check if the start time of appointment1 is before the start time of appointment2 and the end time of appointment1 is after the start time of appointment2
        if (appointment1.getStartTime().isBefore(appointment2.getStartTime()) && appointment1.getEndTime().isAfter(appointment2.getStartTime())) {
            return true;
        }
    
        // Check if the start time of appointment1 is before the end time of appointment2 and the end time of appointment1 is after the end time of appointment2
        if (appointment1.getStartTime().isBefore(appointment2.getEndTime()) && appointment1.getEndTime().isAfter(appointment2.getEndTime())) {
            return true;
        }
    
        return false;
    }

    public Appointment quickSave(Appointment appointment) {
        return repository.save(appointment);
    }

    public void flush(){
        repository.flush();
    }

    // Get an Appointment
    public Appointment getById(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<Appointment> getAll() {
        return repository.findAll();
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
        Appointment entityExists = repository.findById(id).orElse(null);
        String toBeDeleted = "DELETE " + id + ": " + (entityExists == null? "ERROR, does not exist" : entityExists.toString());
        repository.deleteById(id);
        return toBeDeleted;
    }
    
}
