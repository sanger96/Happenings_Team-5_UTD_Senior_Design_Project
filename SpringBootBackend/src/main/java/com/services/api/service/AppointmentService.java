package com.services.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.services.api.entity.Appointment;
import com.services.api.repository.AppointmentRepository;

import java.util.List;
import java.time.Duration;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository repository;

    // Create/Update an Appointment
    public Appointment save(Appointment appointment) {

        // Check for minimum duration
        if (!isMinDuration(appointment)) {
            System.out.println("Not of minimum duration");
            return null;
        }

        // Check for conflicting appointments (prev in AppointmentController.java)

        // TODO: should only get appointments whose type = 'event', we dont care about appointments that conflict with type = 'course' appointments
        List<Appointment> allCurrAppointments = getAll();
        for (Appointment apt : allCurrAppointments) {

            if (doAppointmentsOverlap(appointment, apt)) {
                System.out.println(appointment.toString() + "\nCONFLICTS WITH: \n" + apt.toString());
                return null;
            }
        }

        return repository.save(appointment);
    }

    public boolean isMinDuration(Appointment appointment) {

        Duration duration = Duration.between(appointment.getStartTime(), appointment.getEndTime());
        Duration minDuration = Duration.ofMinutes(30);

        if (duration.compareTo(minDuration) >= 0) {
            return true;
        }

        return false;
    }

    public boolean doAppointmentsOverlap(Appointment appointment1, Appointment appointment2) {
        boolean locationOverlap = appointment1.getLocation().getName().equals(appointment2.getLocation().getName()) &&
        appointment1.getLocation().getRoom().equals(appointment2.getLocation().getRoom());

        boolean timeOverlap = appointment1.getEndTime().isAfter(appointment2.getStartTime()) && appointment1.getStartTime().isBefore(appointment2.getEndTime());
        return locationOverlap && timeOverlap;
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
