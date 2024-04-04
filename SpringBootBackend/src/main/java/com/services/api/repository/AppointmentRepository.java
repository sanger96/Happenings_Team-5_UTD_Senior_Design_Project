package com.services.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import com.services.api.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    
    @Query(value = "SELECT * FROM appointment a WHERE a.type = ?1", nativeQuery = true)
    List<Appointment> getByType(String type);
}
