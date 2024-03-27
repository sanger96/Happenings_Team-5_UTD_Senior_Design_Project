package com.services.api;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.services.scraper.service.PageScraperService;

@Component
public class ScheduledTasks {

    @Autowired
    PageScraperService pageScraperService;
    
    @Scheduled(fixedRate = 86400000)
    public void dailyEventScraper() {
        pageScraperService.addNewEvents();
    }
}
