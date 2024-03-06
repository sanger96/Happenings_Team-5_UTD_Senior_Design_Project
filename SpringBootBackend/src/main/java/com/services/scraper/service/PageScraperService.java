package com.services.scraper.service;

import com.services.api.entity.Event;
import com.services.api.entity.Appointment;
import com.services.api.entity.Location;
import com.services.scraper.entity.PageItem;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service
public class PageScraperService {
    @Value("${website.url}")
    private String thisWeekURL;
    private HashSet<PageItem> pageItems;

    /**
     * scrapePageItems
     * Uses Jsoup library to retrieve events title
     * and calendar url from each item in html list
     */
    private void scrapePageItems(){
        /*
        * iterate over page items in html
        *   gets title
        *   checks if event title already in database and skips if yes
        *   get url
        *   add object to pageItems
        */
    }

    /**
     * didPageUpdate
     * Checks if the html page added any new events
     * @return Boolean if the page updated
     */
    public Boolean didPageUpdate(){
        /*
         * retrieve event list and compare to cached event list (cached list cannot be null)
         */
    }

    /**
     * getNewEvents
     * Uses helper methods and Jsoup to retrieve necessary data for an Event
     * and stores Events in a list
     * @return List<Event> list of events from each page item
     */
    public List<Event> getNewEvents(){
        /*
         * call scrapePageItems() to populate pageItems field
         * using pageItems, get all event information from each url, store as Event type, append to list, return list
         *   how can we store a location, event, and appointment all together
         *   problems/special cases: location data, appointment data, or some event data does not exist on the web page
         */
    }
    
}
