package com.services.scraper.controller;

import com.services.scraper.service.PageScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("pagescraper")
@ComponentScan({"com.services"})
public class PageScraperController {

    @Autowired
    PageScraperService pageScraperService;

    // Depricated
    @PostMapping("/checkForEvents")
    public String checkForEvents(){
        /* Check for new events on UTD calendar page
         * Add new ones
         * return string indicating success/failure (how many new events added)
         */
        return null;
      //  return pageScraperService.addNewEvents();
    }

}
