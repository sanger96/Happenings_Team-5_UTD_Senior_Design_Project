package com.services.api.entity;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;

// import org.hibernate.mapping.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue
    private int eventID;

    @Column(unique = true)
    private String photoSubDirectory;

    @Column(unique = true, nullable = false)
    private String name;

    /* An Event has a OneToOne relationship with an Appointment */
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "appointmentID")
    private Appointment appointment;

    /* An Event has a ManyToOne relationship with a Club */
    @JsonIgnoreProperties("events")
    @ManyToOne
    @JoinColumn(name = "clubID")
    private Club club;

    /* An Event has a ManyToMany relationship with a UserAccount over the Attends table */
    @ManyToMany
    @JoinTable(name = "attends",
        joinColumns = @JoinColumn(name = "eventID"),
        inverseJoinColumns = @JoinColumn(name = "userAccountID"))
    private Set<UserAccount> userAccounts;

}
