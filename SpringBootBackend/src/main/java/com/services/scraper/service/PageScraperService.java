package com.services.scraper.service;

import com.services.api.entity.Event;
import com.services.api.entity.Appointment;
import com.services.api.entity.Location;
import com.services.api.service.AppointmentService;
import com.services.api.service.EventService;
import com.services.api.service.LocationService;
import com.services.scraper.entity.PageItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

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

    private static final HashSet<String> BUILDINGS = new HashSet<>(Arrays.asList(
        "AB", "AD", "AH1", "AH2", "BE", "BSB", "CR", "CRA", "GR", "CB", 
        "CB3", "DGA", "ATC", "ECSN", "ECSS", "ECSW", "JO", "MC", "FM", 
        "FA", "FO", "FN", "HH", "ML1", "ML2", "RL", "JSOM", "NL", "NB", 
        "PS3", "PHA", "PHY", "PD", "ROC", "ROW", "SG", "SLC", "SCI", "SB", 
        "SSB", "SSA", "SU", "SPN", "SP2", "TH", "VCB", "WSTC"
    ));

    private final int MAX_PAGES = 32;

    /**
     * scrapePageItems
     * Uses Jsoup library to retrieve events title
     * and calendar url from each item in html list
     * @return ArrayList<PageItem> contains all the
     * scraped event items to be added to the database
     */
    private ArrayList<PageItem> scrapePageItems(){

            String thisWeekURLChangeable = "";
            int currentPageNumber = 1;

            // Init list to store event items, and set to not store duplicate events
            ArrayList<PageItem> pageItems = new ArrayList<PageItem>();
            HashSet<String> knownEventNames = new HashSet<String>();
            try {
            
                while (currentPageNumber <= MAX_PAGES) {
                    
                    thisWeekURLChangeable = (currentPageNumber == 1? thisWeekURL : thisWeekURL + "/" + currentPageNumber);
                    // Retrieve document using URL, and get element containing list of events
                    Document doc = Jsoup.connect(thisWeekURLChangeable).get();
                    //Elements eventList = doc.getElementsByClass("summary");
                    Elements links = doc.getElementsByTag("a");

                    // Store the url and name of each event
                    for (Element link : links) {
                        String eventURL = link.attr("href");
                        String eventName = "";      

                        if(eventURL.startsWith("https://calendar.utdallas.edu/event/") && !eventURL.endsWith("/event/create")) {
                            // Don't store duplicate events or events that already exist by name
                            
                            // If the first characters of the link after the baseUrl are numbers, disregard this link
                            try {
                                Integer.parseInt(eventURL.substring(36,41));
                                continue;
                            }
                            catch(Exception e) {
                                
                            } 

                            eventName = link.text();
                            if (eventName.isEmpty()) continue;
                            System.out.println("EventName: " + eventName);

                            if(knownEventNames.contains(eventName) || eventService.getByName(eventName) != null) {
                                System.out.println("EventName Not added: "+ eventName);
                                continue;
                            }
                            
                            // Add new event item and update known set
                            PageItem newPageItem = new PageItem(eventName, eventURL);
                            pageItems.add(newPageItem);
                            System.out.println("Just Added: " + newPageItem.getTitle());
                            knownEventNames.add(eventName);
                            System.out.println("To known: " + eventName);
                        }
                    }
                    currentPageNumber++;
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
     * addNewEvents
     * Uses helper methods and Jsoup to retrieve necessary data for an Event
     * and stores Events in a list
     * @return String indicates how many new events were stored or no new events
     */
    public String addNewEvents(){
        int numNewEvents = -99;
        try{
            // Call helper method to extract pageItems, and init new list to populate
            ArrayList<PageItem> eventItems = this.scrapePageItems();
            
            // Exit if no new events retrieved
            if(eventItems.size() == 0)
                return "No new events added";

            numNewEvents = eventItems.size();
            

            for (PageItem eventItem : eventItems) {
                Document doc = Jsoup.connect(eventItem.getUrl()).get();

                Elements paragraphs = doc.select("p");
                String building, roomNumber;
                building = roomNumber = "";
                String previous = "";
                for(Element p : paragraphs)
                {
                    ArrayList<String> locData = tryParseLocation(p);
                    if(isBuilding(locData.get(0)) && !locData.get(0).equals(previous)) {
                        building = locData.get(0);
                        roomNumber = locData.get(1);
                        previous = locData.get(0);
                    }
                }

                Element eventJson = doc.select("script[type=application/ld+json]").first();
                String jsonString = eventJson.html();

                // Parse JSON string to JSON object
                Gson gson = new Gson();

                // Get the first JSON object of the list
                JsonObject [] jsonObjects = gson.fromJson(jsonString, JsonObject[].class);
                JsonObject jsonObject = jsonObjects[0];
                
                // Fields that we can possibly extract (not all can be, not all are used)
                String name, description, startDate, endDate, eventStatus, imageUrl, locationName, locationAddress, locationUrl;
                name = description = startDate = endDate = eventStatus = imageUrl = locationName = locationAddress = locationUrl = "";
                
                // Sometimes there is no location 
                JsonObject locationObj = null;

                name = eventItem.getTitle();
    
                try {
                    description = jsonObject.get("description").getAsString();
                } catch (Exception e) {}
                
                try {
                    startDate = jsonObject.get("startDate").getAsString();
                } catch (Exception e) {}
                
                try {
                    endDate = jsonObject.get("endDate").getAsString();
                } catch (Exception e) {}

                try {
                    eventStatus = jsonObject.get("eventStatus").getAsString();
                    imageUrl = jsonObject.get("image").getAsString();
                } catch (Exception e) {}
                
                try {
                    locationObj = jsonObject.getAsJsonObject("location");
                } catch (Exception e) {}

                if (locationObj != null) {
                    try {
                        locationName = locationObj.get("name").getAsString();
                        locationAddress = locationObj.get("address").getAsString();
                        locationUrl = locationObj.get("url").getAsString();
                    } catch (Exception E) {}
                }
                
                if (building.isEmpty()) {
                    building = locationName;
                }           
                 
                Location locationToAdd = new Location(building, roomNumber);

                // Check for dupe location
                locationToAdd = locationService.save(locationToAdd);
                
                Appointment appointmentToAdd = new Appointment(dateFormatter(startDate), dateFormatter(endDate), "event", locationToAdd);

                // Quick Save to bypass checking
                appointmentService.quickSave(appointmentToAdd);
                
                Event eventToAdd = new Event(name, description, null, appointmentToAdd);
            
                eventService.save(eventToAdd);

            }
        }
        catch(Exception e){
            System.out.println("Exception thown:" + e.getMessage());
            System.out.println("\nStack Trace:");
            for(int i = 0; i < e.getStackTrace().length; i++){
                System.out.println(e.getStackTrace()[i].toString());
            }
            return null;
        }

        return "[There are a total of " + numNewEvents + " events]\n";
        
    }

    private LocalDateTime dateFormatter(String date) {
        
        try {
            // Define a DateTimeFormatter for the input format
            DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

            // Parse the original date-time string into a ZonedDateTime object
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(date, formatter);

            // Convert ZonedDateTime to LocalDateTime (without time zone information)
            LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();

            // Print the converted LocalDateTime object
            return localDateTime;
        } catch (Exception e) {
            
        }

        try {
            // Parse the original date string into a LocalDate object
            LocalDate originalDate = LocalDate.parse(date);

            // Combine the LocalDate with a default LocalTime to create a LocalDateTime object
            LocalDateTime localDateTime = LocalDateTime.of(originalDate, LocalTime.MIN);

            return localDateTime;
        } catch (Exception e) {
            
        }

        try {
            // Define a DateTimeFormatter for the input format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

            // Parse the original date-time string into a LocalDateTime object
            LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);

            // Print the parsed LocalDateTime object
            return localDateTime;
        } catch (Exception e) {
            
        }

        return null;

    }

    private ArrayList<String> tryParseLocation(Element p) {
        ArrayList<String> ret = new ArrayList<String>();

        String location = p.text();    

        // Find something in parenthesis
        int startIndex = location.indexOf("("); 
        int endIndex = location.indexOf(")");
 
        String building = "";
        String roomNumber = "";
 
        // Get all the location tokens
        String locTokens [] = location.split(" |, ");

        // If something was found in parenthesis
        if (startIndex != -1) {

            // Assume that the parenthesis has the building
            building = location.substring(startIndex+1, endIndex);

            // If the building length is longer than 4, there is more information (potentially useless) here
            if (building.length() > 4) {
                String inParen [] = building.split(" ");

                // If the second value in the parenthesis contains a period, then we probably have a building-room combo
                if (inParen.length > 1 && inParen[1].contains(".")) {
                    building = inParen[0];
                    roomNumber = inParen[1];    
                } else {

                    // Else we probably did not have a building in the first place
                    building = "";
                }
            } 
        }

        // If we have no room number, go through and find one
        if (roomNumber.isEmpty()) {
            for (int i=0; i < locTokens.length; i++) {
                if (locTokens[i].contains(".") && locTokens[i].length() > 3) {
                    roomNumber = locTokens[i];
                    if (building.isEmpty() && i>0) {
                        building = locTokens[i-1]; // guess about the building 
                    }
                }
            }
        }

        // If we still have no building, good chance we can get the building code from the first token
        if (building.isEmpty() && location.length() > 0 && Character.isUpperCase(location.charAt(1))) {
            building = locTokens[0];
        }

        ret.add(building);
        ret.add(roomNumber);
        return ret;
        
    }

    private static boolean isBuilding(String building) {
        return BUILDINGS.contains(building);
    }
   
}