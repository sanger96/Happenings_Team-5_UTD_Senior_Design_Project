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
import java.util.ArrayList;
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
    private ArrayList<PageItem> scrapePageItems(){
        try{
            // Retrieve document using URL
            Document doc = Jsoup.connect(thisWeekURL).get();
 
            // Gets the element with the class "summary" and all child elements
            Elements eventList = doc.getElementsByClass("summary");
            
            ArrayList<PageItem> pageItems = new ArrayList<PageItem>();

            // Store the url and name of each event
            for (Element event : eventList)
            {
                Elements links = event.select("a[href]");
                String eventURL = links.attr("href");
                String eventName = eventURL.substring(36).replace('_', ' ');

                PageItem newPageItem = new PageItem(eventName, eventURL);

                pageItems.add(newPageItem);

            }
            return pageItems;
        }
        catch(Exception e){
            System.out.println("Exception thown:" + e.getMessage());
            return null;
        }
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
        return true;
    }

    /**
     * getNewEvents
     * Uses helper methods and Jsoup to retrieve necessary data for an Event
     * and stores Events in a list
     * @return List<Event> list of events from each page item
     */
    public String getNewEvents(){
        /*
         * call scrapePageItems() to populate pageItems field
         * using pageItems, get all event information from each url, store as Event type, append to list, return list
         *   how can we store a location, event, and appointment all together
         *   problems/special cases: location data, appointment data, or some event data does not exist on the web page
         */
        String testOutput = "";
        try{
            // Call helper method to extract pageItems
            ArrayList<PageItem> eventItems = this.scrapePageItems();

            // The new list of events that should be returned
            ArrayList<Event> events = new ArrayList<Event>();

            // Using Japan event as example
            Document doc = Jsoup.connect("https://calendar.utdallas.edu/event/japan_form_function_the_montgomery_collection").get();
            Element container = doc.getElementsByClass("content-top grid_container").first();
            Element content = container.getElementsByClass("box_content vevent grid_8").first();

            /**
             * The description element contains many child elements that could be <p>, <i>, <a>, <b>, and <span> tags
             * These tags should have their text information extracted only
             */
            Elements description = content.getElementsByClass("description").first().getAllElements();
            
            /**
             * The location element contains many child elements that could be <p>, <br>, <i>, <a>, <b>, and <span> tags
             * These tags should have their text information extracted only
             */
            Elements location = content.getElementsByClass("location").first().getAllElements();

            /**
             * The datetime element contains many child elements that could be <abbr>, and<i> tags
             * The <abbr> tags contain a title attribute which contains the exact date and time format needed
             * There can sometimes be a <abbr> tag with a class = "dtstart" or "dtend"
             * (worth noting that there are sometimes extra dates unde the <div id="x-all-dates" style="display: none"> tag)
             */
            Elements datetime = content.getElementsByClass("dateright").first().getAllElements();
            
            testOutput += "---------------------------DESCRIPTION---------------------------\n" + description.html() + "\n\n\n";
            testOutput += "---------------------------LOCATION---------------------------\n" + location.html() + "\n\n\n";
            testOutput += "---------------------------DATE/TIME---------------------------\n" + datetime.html() + "\n\n\n";

            /* Iterate through all eventItems to extract the above information */
            // for(PageItem eventItem : eventItems){
            //     // Get the document using URL
            //     Document doc = Jsoup.connect(eventItem.getUrl()).get();
            //     Element container = doc.getElementsByClass("content-top grid_container").first();
            //     Element content = container.getElementsByClass("box_content vevent grid_8").first();
                
            //     testOutput += container.html();
            // }
        }
        catch(Exception e){
            System.out.println("Exception thown:" + e.getMessage());
            return null;
        }

        return testOutput;
        // return null;
    }

    
}
