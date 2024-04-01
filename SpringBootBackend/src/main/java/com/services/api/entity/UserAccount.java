package com.services.api.entity;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import com.services.api.entity.Interest;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "useraccount")
@PrimaryKeyJoinColumn(name = "useraccountID")
public class UserAccount extends Account{


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "attends",
            joinColumns = @JoinColumn(name = "useraccount_id", referencedColumnName = "useraccountID"),
            inverseJoinColumns = @JoinColumn(name = "event_id", referencedColumnName = "eventID"))
    private Set<Event> events = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "hasinterest",
            joinColumns = @JoinColumn(name = "useraccount_id", referencedColumnName = "useraccountID"),
            inverseJoinColumns = @JoinColumn(name = "interest_id", referencedColumnName = "interestID"))
    private Set<Interest> interests = new HashSet<>();

   /*
    @ManyToMany
    @JoinTable(name = "hasinterest",
        joinColumns = @JoinColumn(name = "useraccountid"),
        inverseJoinColumns = @JoinColumn(name = "interestid"))
    private Set<Interest> interests; 
*/



    public void addEvent(Event event)
    {
        events.add(event);
    }

    public void delEvent(Event event)
    {
        events.remove(event);
    }

    public void addInterest(Interest interest)
    {
        interests.add(interest);
    }

    public void delInterest(Interest interest)
    {
        interests.remove(interest);
    }
}




