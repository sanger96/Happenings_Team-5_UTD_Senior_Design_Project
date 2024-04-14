package com.services.api.entity;

import java.util.List;
import java.util.Set;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    // @Column(unique = true)
    private String photoSubDirectory;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(length = 10000)
    private String description;

    /* An Event has a OneToOne relationship with an Appointment */
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "appointmentID")
    @OnDelete(action = OnDeleteAction.CASCADE) // creates the Cascade delete in the database
    private Appointment appointment;

    /* An Event has a ManyToOne relationship with a Club */
    @JsonIgnoreProperties("events")
    @ManyToOne
    @JoinColumn(name = "clubID")
    private Club club;

    /* An Event has a ManyToMany relationship with a UserAccount over the Attends table */
    // @ManyToMany
    // @JoinTable(name = "attends",
    //     joinColumns = @JoinColumn(name = "eventID"),
    //     inverseJoinColumns = @JoinColumn(name = "userAccountID"))
    // private Set<UserAccount> userAccounts;

    public void setClub(Club club)
    {
        this.club = club;
    }

    public Event(String name, String description, String photoSubDirectory, Appointment appointment){
        this.name = name;
        this.description = description;
        this.photoSubDirectory = photoSubDirectory;
        this.appointment = appointment;
    }

    public Event(String name, String description, String photoSubDirectory, Appointment appointment, Club club){
        this.name = name;
        this.description = description;
        this.photoSubDirectory = photoSubDirectory;
        this.appointment = appointment;
        this.club = club;
    }
}
