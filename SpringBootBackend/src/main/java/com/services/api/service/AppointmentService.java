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
        
        List<Appointment> allCurrAppointments = getByType("event");
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
        boolean locationNameOverlap = appointment1.getLocation().getName().equals(appointment2.getLocation().getName());

        // Decided that if at least one of the appointments has a null room, we won't count as overlap
        // Overlap will just be specfic to same location name and same room, bc that is very specific
        boolean locationRoomOverlap = !(appointment1.getLocation().getRoom() ==  null || appointment2.getLocation().getRoom() == null) &&
                                        (appointment1.getLocation().getRoom().equals(appointment2.getLocation().getRoom()));
        boolean locationOverlap = locationNameOverlap && locationRoomOverlap;

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

    public List<Appointment> getByType(String type) {
        return repository.getByType(type);
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
