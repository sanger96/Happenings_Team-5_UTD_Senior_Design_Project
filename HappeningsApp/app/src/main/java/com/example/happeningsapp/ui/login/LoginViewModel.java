package com.example.happeningsapp.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {

    private final MutableLiveData<String> topPrompt;
    private final MutableLiveData<String> username;
    private final MutableLiveData<String> password;

    public LoginViewModel() {
        topPrompt = new MutableLiveData<>();
        //sets top prompt
        topPrompt.setValue("Login settings page\nFor successful login attempt use default values");
        username = new MutableLiveData<>();
        //sets username
        username.setValue("Enter username");
        password = new MutableLiveData<>();
        //sets password
        password.setValue("Enter password");

    }

    public LiveData<String> getText() {
        return topPrompt;
    }
    public LiveData<String> getUsername() {
        return username;
    }
    public LiveData<String> getPassword() {
        return password;
    }
}