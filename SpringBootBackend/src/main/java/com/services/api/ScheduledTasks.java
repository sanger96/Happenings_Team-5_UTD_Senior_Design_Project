package com.services.api;

import java.time.format.DateTimeFormatter;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private static final DateTimeFormatter dateTimeFormatter 
        = DateTimeFormatter.ofPattern("HH:mm:ss");

        @Scheduled(fixedRate = 86400000)
        public void dailyEventScraper() {
            
        }
}
