package com.services.api.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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

    // TODO: Representing date and times with Strings for now
    private String startTime;
    private String endTime;

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
