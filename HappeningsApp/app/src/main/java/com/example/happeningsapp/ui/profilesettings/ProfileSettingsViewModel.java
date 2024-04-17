package com.example.happeningsapp.ui.profilesettings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileSettingsViewModel extends ViewModel {
    private final MutableLiveData<String> pageTitle;
    private final MutableLiveData<String> email;
    private final MutableLiveData<String> password;

    public ProfileSettingsViewModel() {
        //set variables to MutableLiveData type
        pageTitle = new MutableLiveData<>();
        email = new MutableLiveData<>();
        password = new MutableLiveData<>();


    }

    public LiveData<String> getText() {
        return pageTitle;
    }
    public LiveData<String> getEmail() {
        return email;
    }
    public LiveData<String> getPassword() {
        return password;
    }

}