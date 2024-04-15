package com.example.happeningsapp;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.app.*;
import android.webkit.WebView;


public class GlobalVars extends Application {

    //Time window, on either side it of current time it will dictate relevant events for MapsActivity
    int timeWindow=1;

    // Server URL
    String serverUrl = "http://108.215.179.57:4884";

    // User account globals
    int userID = -1;
    String username = "";
    String password = "";

    private static final GlobalVars ourInstance = new GlobalVars();
    public static GlobalVars getInstance() {
        return ourInstance;
    }

    public void setServerUrl(String url){ this.serverUrl = url; }
    public String getServerUrl(){ return serverUrl; }
    public void setUserID(int id){ this.userID = id; }
    public int getUserID(){ return userID; }
    public void setUsername(String username){ this.username = username; }
    public String getUsername(){ return username; }
    public void setPassword(String password){ this.password = password; }
    public String getPassword(){ return password; }
    public void setTimeWindow(int timeWindow){ this.timeWindow = timeWindow; }
    public int getTimeWindow(){ return timeWindow; }
}
