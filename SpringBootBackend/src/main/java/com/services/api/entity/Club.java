package com.services.api.entity;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CLUB")
public class Club {

    @Id
    @GeneratedValue
    private int clubID;
    private String description;
    private int leaderID;
    
}
