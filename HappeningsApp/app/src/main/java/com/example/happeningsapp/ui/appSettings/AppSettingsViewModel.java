package com.example.happeningsapp.ui.appSettings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AppSettingsViewModel extends ViewModel {
    private final MutableLiveData<String> timeWindow;
    public AppSettingsViewModel() {
        timeWindow = new MutableLiveData<>();
    }
    public LiveData<String> getText() {
        return timeWindow;
    }
}