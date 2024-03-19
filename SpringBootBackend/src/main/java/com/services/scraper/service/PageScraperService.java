package com.services.scraper.service;

import com.services.api.entity.Event;
import com.services.api.entity.Appointment;
import com.services.api.entity.Location;
import com.services.api.service.AppointmentService;
import com.services.api.service.EventService;
import com.services.api.service.LocationService;
import com.services.scraper.entity.PageItem;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    LocationService locationService;

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    EventService eventService;

    /**
     * scrapePageItems
     * Uses Jsoup library to retrieve events title
     * and calendar url from each item in html list
     * @return ArrayList<PageItem> contains all the
     * scraped event items to be added to the database
     */
    private ArrayList<PageItem> scrapePageItems(){
        try{
            // Retrieve document using URL, and get element containing list of events
            Document doc = Jsoup.connect(thisWeekURL).get();
            Elements eventList = doc.getElementsByClass("summary");
            
            // Init list to store event items, and set to not store duplicate events
            ArrayList<PageItem> pageItems = new ArrayList<PageItem>();
            HashSet<String> knownEvents = new HashSet<String>();
            
            // Store the url and name of each event
            for (Element event : eventList)
            {
                Elements links = event.select("a[href]");
                String eventURL = links.attr("href");
                String eventName = eventURL.substring(36).replace('_', ' ');
                
                // Don't store duplicate events or events that already exist by name
                if(knownEvents.contains(eventName) || eventService.existsByName(eventName) == 1)
                    continue;
                
                // Add new event item and update known set
                PageItem newPageItem = new PageItem(eventName, eventURL);
                pageItems.add(newPageItem);
                knownEvents.add(eventName);
            }

            return pageItems;
        }
        catch(Exception e){
            System.out.println("Exception thown:" + e.getMessage());
            System.out.println("\nStack Trace:");
            for(int i = 0; i < e.getStackTrace().length; i++){
                System.out.println(e.getStackTrace()[i].toString());
            }
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
     * addNewEvents
     * Uses helper methods and Jsoup to retrieve necessary data for an Event
     * and stores Events in a list
     * @return String indicates how many new events were stored or no new events
     */
    public String addNewEvents(){
        String testOutput = "";
        try{
            // Call helper method to extract pageItems, and init new list to populate
            ArrayList<PageItem> eventItems = this.scrapePageItems();

            // Exit if no new events retrieved
            if(eventItems.size() == 0)
                return "No new events added";

            /* TESTING PURPOSES */
            testOutput = "[There are a total of " + eventItems.size() + " events]\n\n";

            for(PageItem eventItem : eventItems){
                // Get the document using URL, and get element containing needed data
                Document doc = Jsoup.connect(eventItem.getUrl()).get();
                Element content = doc.getElementsByClass("content-top grid_container").first()
                                     .getElementsByClass("box_content vevent grid_8").first();
                
                // Call helper methods to retrieve data
                String title = this.getTitle(content);
                String description = this.getDescription(content);
                String location = this.getLocation(content);
                String datetime = this.getDatetime(content);
                
                /* TESTING PURPOSES */
                testOutput += "---------------------------TITLE---------------------------\n" + title + "\n";
                testOutput += "---------------------------DESCRIPTION---------------------------\n" + description + "\n";
                testOutput += "---------------------------LOCATION---------------------------\n" + location + "\n";
                testOutput += "---------------------------DATE/TIME---------------------------\n" + datetime + "\n\n\n";
                /*                  */


                /* TODO: Create Location object, if it doesn't already exist in DB
                 * location string needs to be parsed first
                 * TODO: need to have full team discussion on Location information
                 */

                 
                // This parses the building of the location.
                int startIndex = 0;
                int endIndex = 0;

                String roomNumber = "";

                for(int i=0; i<location.length(); i++)
                {
                    if(location.charAt(i) == '(')
                        startIndex = i+1;
                    if(location.charAt(i) == ')')
                        endIndex = i;
                    if((Character.isDigit(location.charAt(i)) || location.charAt(i) == '.') && (i > endIndex))
                        roomNumber += location.charAt(i);
                }
                
                if(startIndex > endIndex)
                {
                    startIndex = 0;
                    endIndex = 0;
                }

                String building = location.substring(startIndex, endIndex);
                testOutput += "PARSED LOCATION BUILDING IS: " + building + "\n";
                testOutput += "PARSED LOCATION ROOM IS: " + roomNumber + "\n";



                /* TODO: Create Appointment object
                 * Use the given Location object in its instantiation
                 * the datetime string can include one or two dates, separated by tab
                 */
                
                /* TODO: Create Event object
                 * Use given Appointment object
                 * photoSubDirectory should utilize event's title
                 */

            }

            /* TODO: Post all new events to database and flush
             * return string indicating number of new events
             */
        }
        catch(Exception e){
            System.out.println("Exception thown:" + e.getMessage());
            System.out.println("\nStack Trace:");
            for(int i = 0; i < e.getStackTrace().length; i++){
                System.out.println(e.getStackTrace()[i].toString());
            }
            return null;
        }

        return testOutput;
        // return null;
    }

    /**
     * getTitle
     * @param content The element containing necessary content for an Event
     * @return String containing the title of an event
     */
    private String getTitle(Element content){
        Elements titleElement = content.getElementsByClass("summary");
        return titleElement.first().text();
    }

    /**
     * getDescription
     * @param content The element containing necessary content for an Event
     * @return String containing the description of an event
     */
    private String getDescription(Element content){
        Elements descriptionElement = content.getElementsByClass("description").first().getAllElements();

        String description = "";
        if (descriptionElement.first().hasText()){
            description = descriptionElement.first().text();
        }

        return description;
    }

    /**
     * getLocation
     * @param content The element containing necessary content for an Event
     * @return String containing the location of an event
     * TODO: might need to be edited once location information is decided amongst members
     */
    private String getLocation(Element content){
        /**
         * The location element, and/or children
         * A location element may contain no text or no children
         * If it does contain text, get the text from the <a> tag if it exists, if not then get any text
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

        return location;
    }

    /**
     * getDatetime
     * @param content The element containing necessary content for an Event
     * @return String containing the start and end datetimes of an event, one or both may be missing
     */
    private String getDatetime(Element content){
        /**
         * The datetime element, and/or children
         * There are usually two child <abbr> elements containing a title attribute,
         * title = "dtstart"/"dtend", with the exact date and time format needed
         *      Both children can be missing, or "dtend" child can be missing
         * TODO:(worth noting that there are sometimes extra dates under the <div id="x-all-dates" style="display: none"> tag)
         */
        Elements datetimeElement = content.getElementsByClass("dateright").first().children();
        String datetime = "";
        if(datetimeElement.first().hasText()){
            int numChildren = datetimeElement.size();

            if(numChildren == 2){
                datetime = datetimeElement.first().attribute("title").getValue() + "\t" + datetimeElement.first().nextElementSibling().attribute("title").getValue();
            }
            else if(numChildren == 1){
                datetime = datetimeElement.first().attribute("title").getValue();
            }
        }

        return datetime;
    }

    
}
