package com.services.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LOCATION")
public class Location {
    @Id
    @GeneratedValue
    private int locationID;
    
    private String name;
    private String room;

    // One location has one appointment
    @OneToOne
    @JoinColumn(name = "appointmentID")
    private Appointment appointment;

    public Location(String name, String room){
        this.name = name;
        this.room = room;
    }

    public Location(String name){
        this.name = name;
    }
}
