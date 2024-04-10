package com.services.api.entity;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.Data;

@Data
public class EventDTO {
    private String eventName;
    private String description;
    private Optional<Integer> clubID;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String locationName;
    private Optional<String> room;

    public EventDTO(String eventName, String description, Optional<Integer> clubID,
                    LocalDateTime startTime, LocalDateTime endTime, String locationName,
                    Optional<String> room) {
        this.eventName = eventName;
        this.description = description;
        this.clubID = clubID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.locationName = locationName;
        this.room = room;
    }
}
