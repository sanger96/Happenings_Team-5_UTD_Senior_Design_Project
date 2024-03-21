package com.services.api.DTO;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Basic;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import jakarta.annotation.Nonnull;

@Data
public class EventCreationDTO {
    @Nonnull
    private String eventName;

    /* Club info may not be applicable */
    private Integer clubLeaderID;
    private String clubName;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Nonnull
    private LocalDateTime startTime;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Nonnull
    private LocalDateTime endTime;

    @Nonnull
    private String locationName;

    private String room;
}
