package com.services.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.services.api.entity.Club;
import com.services.api.entity.Location;
import java.util.*;

public interface LocationRepository extends JpaRepository<Location, Integer> {

    // Get all locations
    @Query(value = "SELECT * FROM location", nativeQuery = true)
    List<Location> getAll();

    // Get a location by name
    @Query(value = "SELECT * FROM location l WHERE l.name = ?1", nativeQuery = true)
    List<Location> getByName(String name);

    // Get a location by room
    @Query(value = "SELECT * FROM location l WHERE l.room = ?1", nativeQuery = true)
    List<Location> getByRoom(String room);

    @Query(value = "SELECT * FROM location l WHERE l.name = ?1 AND l.room = ?2 LIMIT 1", nativeQuery = true)
    Location getByNameAndRoom(String name, String room);
}
    

