package com.services.api.entity;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CLUB")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "events"})
public class Club {

    @Id
    @GeneratedValue
    private int clubID;

    @Column(unique = true)
    private String name;

    private String description;
    
    private int leaderID;
    
    // One club has many events
    @JsonIgnoreProperties("CLUB")
    @OneToMany
    @JoinColumn(name = "clubID")
    private List<Event> events;
    
    // Many clubs have many favorite users

    @ManyToMany
    @JoinTable(name = "hasfavorite",
        joinColumns = @JoinColumn(name = "clubID"),
        inverseJoinColumns = @JoinColumn(name = "userAccountID"))
    private Set<UserAccount> userAccounts;

    public List<Event> getEvents(){ return events; }

}
