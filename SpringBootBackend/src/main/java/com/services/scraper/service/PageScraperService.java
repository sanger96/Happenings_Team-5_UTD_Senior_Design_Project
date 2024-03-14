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
            Element content = doc.getElementsByClass("content-top grid_container").first()
                                    .getElementsByClass("box_content vevent grid_8").first();


            /* Element containing title of event */
            Elements titleElement = content.getElementsByClass("summary");
            String title = titleElement.first().text();

            /**
             * The description element, and/or children
             * A description element may contain no text or no children
             */
            Elements descriptionElement = content.getElementsByClass("description").first().getAllElements();
            String description = "";
            if (descriptionElement.first().hasText()){
                description = descriptionElement.first().text();
            }
            

            /**
             * The location element, and/or children
             * A location element may contain no text or no children
             * If it does contain text, get the text from the <a> tag if it exists, if not get any text
             * (location may be virtual, on campus, off campus, or empty(maybe))
             */
            Elements locationElement = content.getElementsByClass("location").first().getAllElements();
            String location = "";
            if(locationElement.first().hasText()){
                location = locationElement.first().text();

                if(locationElement.first().childrenSize() != 0){
                    if(locationElement.first().getElementsByTag("a").first() != null && locationElement.first().getElementsByTag("a").first().hasText())
                        location = locationElement.first().getElementsByTag("a").first().text();
                }
            }

            /**
             * The datetime element, and/or children
             * There are usually two child <abbr> elements containing a title attribute, title = "dtstart"/"dtend", with the exact date and time format needed
             *      Both children can be missing, "dtend" child can be missing, (best to check if there is any children too)
             * (worth noting that there are sometimes extra dates under the <div id="x-all-dates" style="display: none"> tag)
             */
            Elements datetimeElement = content.getElementsByClass("dateright").first().children();
            String datetime = "";
            if(datetimeElement.first().hasText()){
                int numChildren = datetimeElement.first().childrenSize();
                
                // if(numChildren == 2){
                //     datetime = datetimeElement.first().attribute("title").getValue() + "\t" + datetimeElement.first().nextElementSibling().attribute("title").getValue();
                // }
                // else if(numChildren == 1){
                //     datetime = datetimeElement.first().attribute("title").getValue();
                // }
            }
            

            
            testOutput += "---------------------------TITLE---------------------------\n" + title + "\n";
            testOutput += "---------------------------DESCRIPTION---------------------------\n" + description + "\n";
            testOutput += "---------------------------LOCATION---------------------------\n" + location + "\n";
            testOutput += "---------------------------DATE/TIME---------------------------\n" + datetime + "\n\n\n";

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
            System.out.println("Stack Trace:\n");
            for(int i = 0; i < e.getStackTrace().length; i++){
                System.out.println(e.getStackTrace()[i].toString());
            }
            return null;
        }

        return testOutput;
        // return null;
    }

    
}
