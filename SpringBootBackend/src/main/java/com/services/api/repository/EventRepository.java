package com.services.api.repository;

import org.antlr.v4.runtime.atn.SemanticContext.AND;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.services.api.entity.Event;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {

    // Get all events by club ID
    @Query(value = "SELECT * FROM event e WHERE e.clubID = ?1", nativeQuery = true)
    List<Event> getEventsByID(Integer id);
    
    // Check if event exists by name
    @Query(value = "SELECT 1 FROM event WHERE name = ?1", nativeQuery = true)
    Integer existsByName(String name);

    // Get number of useraccounts attending an event by event ID
    @Query(value = "SELECT COUNT(*) FROM attends a WHERE a.eventID = ?1", nativeQuery = true)
    Integer getRSVPcount(Integer id);

    @Query(value = "SELECT * FROM event WHERE name = ?1 LIMIT 1", nativeQuery = true)
    Event findByName(String name);

    /* Get all events such that the difference between the time this method is called and the endTime of
     * the event is >= 24 hours
     */
    @Query(value = "DELETE FROM event WHERE eventid IN (\n" + //
                        "SELECT eventid FROM\n" + //
                            "SELECT DISTINCT eventid\n" + // 
                            "FROM event NATURAL JOIN appointment\n" + //
                            "WHERE (unix_timestamp(ADDTIME(end_time, '1 00:00:00')) BETWEEN unix_timestamp(start_time) AND unix_timestamp(NOW()))\n" + //
                        ") AS e\n" +//
                    ");\n"
    , nativeQuery = true)
    void deleteExpired();
}
