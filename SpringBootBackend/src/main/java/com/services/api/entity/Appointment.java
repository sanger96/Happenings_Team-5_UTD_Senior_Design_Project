package com.services.api.entity;

import java.util.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
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
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime startTime;
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime endTime;

    // This type could be an Enumeration in future
    private String type;

    /* An Appointment has a ManyToOne relationship with a UserAccount */
    @ManyToOne
    @JoinColumn(name = "userAccountID")
    private UserAccount userAccount;

    /* An Appointment has a OneToOne relationship with a Location */
    @OneToOne
    @JoinColumn(name = "locationID")
    private Location location;

}
