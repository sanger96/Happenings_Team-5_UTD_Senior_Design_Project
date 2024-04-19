package com.example.happeningsapp.ui.appSettings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AppSettingsViewModel extends ViewModel {
    private final MutableLiveData<String> timeWindow;

    private final MutableLiveData<String> email;
    private final MutableLiveData<String> password;
    public AppSettingsViewModel() {

        //set variables to MutableLiveData type
        timeWindow = new MutableLiveData<>();
        email = new MutableLiveData<>();
        password = new MutableLiveData<>();

    }
    public LiveData<String> getTimeWindow() {
        return timeWindow;
    }
    public LiveData<String> getEmail() {
        return email;
    }
    public LiveData<String> getPassword() {
        return password;
    }
}