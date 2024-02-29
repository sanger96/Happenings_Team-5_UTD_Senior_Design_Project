package com.services.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.services.api.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    
}
