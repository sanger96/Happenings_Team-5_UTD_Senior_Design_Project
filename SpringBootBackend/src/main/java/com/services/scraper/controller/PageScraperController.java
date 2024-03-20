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
    PageScraperService pageScraperService;

    @PostMapping("/checkForEvents")
    public String checkForEvents(){
        /* Check for new events on UTD calendar page
         * Add new ones
         * return string indicating success/failure (how many new events added)
         */

        return pageScraperService.addNewEvents();
    }

}
