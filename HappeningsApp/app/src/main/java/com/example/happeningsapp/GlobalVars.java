package com.example.happeningsapp;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.app.*;
import android.webkit.WebView;


public class GlobalVars extends Application {
    int userID = -1;
    String username = "";
    String password = "";

    private static final GlobalVars ourInstance = new GlobalVars();
    public static GlobalVars getInstance() {
        return ourInstance;
    }

    public void setUserID(int id){ this.userID = id; }
    public int getUserID(){ return userID; }
    public void setUsername(String username){ this.username = username; }
    public String getUsername(){ return username; }
    public void setPassword(String password){ this.password = password; }
    public String getPassword(){ return password; }
}
