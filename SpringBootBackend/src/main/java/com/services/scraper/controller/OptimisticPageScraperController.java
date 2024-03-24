package com.services.scraper.controller;

import com.services.scraper.service.OptimisticPageScraperService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("optimisticpagescraper")
public class OptimisticPageScraperController {

    @Autowired
    OptimisticPageScraperService pageScraperService;

    @PostMapping("/checkForEvents")
    public String checkForEvents(){
        /* Check for new events on UTD calendar page
         * Add new ones
         * return string indicating success/failure (how many new events added)
         */

        return pageScraperService.addNewEvents();
    }

}
