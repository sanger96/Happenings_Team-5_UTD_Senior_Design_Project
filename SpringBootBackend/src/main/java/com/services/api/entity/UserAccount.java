package com.services.api.entity;
import java.util.Set;


import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "useraccount")
@PrimaryKeyJoinColumn(name = "useraccountID")
public class UserAccount extends Account{

    @ManyToMany
    @JoinTable(name = "hasinterest",
        joinColumns = @JoinColumn(name = "useraccountID"),
        inverseJoinColumns = @JoinColumn(name = "interestID"))
    private Set<Interest> interests; 
}

