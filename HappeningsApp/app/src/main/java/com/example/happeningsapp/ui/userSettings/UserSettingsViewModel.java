package com.example.happeningsapp.ui.userSettings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserSettingsViewModel extends ViewModel {

    private final MutableLiveData<String> pageTitle;
    private final MutableLiveData<String> email;
    private final MutableLiveData<String> password;

    public UserSettingsViewModel() {
        //instantiates pageTitle as a MutableLiveData, I know this should not be mutable, but for now following a known working method.
        pageTitle = new MutableLiveData<>();
        //sets pageTitle
        pageTitle.setValue("Account Creation");
        //the comments below are for reference only, this is from another page
        email = new MutableLiveData<>();
        //sets username
        email.setValue("Enter username");
        password = new MutableLiveData<>();
        //sets password
        password.setValue("Enter password");

    }

    public LiveData<String> getText() {
        return pageTitle;
    }
//    public LiveData<String> getUsername() {
//        return username;
//    }
//    public LiveData<String> getPassword() {
//        return password;
//    }
}