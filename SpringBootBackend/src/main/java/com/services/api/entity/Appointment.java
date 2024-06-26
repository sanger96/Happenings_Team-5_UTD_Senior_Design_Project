package com.services.api.entity;

import java.util.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue
    private int appointmentID;

    // TODO: A name field might be necessary to know what course a UserAccount has

    @Basic
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime startTime;

    @Basic
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime endTime;

    // This type could be an Enumeration in future
    @Column(nullable = false)
    private String type;

    /* An Appointment has a ManyToOne relationship with a UserAccount */
    @ManyToOne
    @JoinColumn(name = "userAccountID")
    private UserAccount userAccount;

    /* An Appointment has a OneToOne relationship with a Location */
    // @JsonIgnoreProperties("appointment")
    @ManyToOne
    @JoinColumn(name = "locationID")
    private Location location;

    public Appointment(LocalDateTime startTime, LocalDateTime endTime, String type, Location location){
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
        this.location = location;
    }
}
