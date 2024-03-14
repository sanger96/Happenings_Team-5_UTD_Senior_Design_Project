package com.services.scraper.controller;

import com.services.scraper.entity.PageItem;
import com.services.scraper.service.PageScraperService;
import com.services.api.service.AppointmentService;
import com.services.api.service.EventService;
import com.services.api.service.LocationService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Set;

@RestController
@RequestMapping("pagescraper")
public class PageScraperController {
    @Autowired
    LocationService locationService;

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    EventService eventService;

    @Autowired
    PageScraperService pageScraperService;

    @PostMapping("/checkForEvents")
    public String checkForEvents(){
        /*
        call didPageUpdate() return string if it didnt
        call getNewEvents() and store in List of Events
        for each event in list
            post location, flush
            post appointment, flush
            post event, flush
        */

        return pageScraperService.getNewEvents();
       
        //pageScraperService.getNewEvents();
        //return "test";
        
        
    }

}
