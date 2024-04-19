package com.example.happeningsapp.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {

    //private final MutableLiveData<String> topPrompt;
    private final MutableLiveData<String> username;
    private final MutableLiveData<String> password;

    public LoginViewModel() {
//        topPrompt = new MutableLiveData<>();
//        //sets top prompt
//        topPrompt.setValue("Login settings page\nFor successful login just press submit");
        username = new MutableLiveData<>();
        //sets username, don't want to set it, so hint in fragment will be used
        //username.setValue("Enter username");
        password = new MutableLiveData<>();
        //sets password, don't want to set it, so hint in fragment will be used
        //password.setValue("Enter password");

    }

//    public LiveData<String> getText() {
//        return topPrompt;
//    }
    public LiveData<String> getUsername() {
        return username;
    }
    public LiveData<String> getPassword() {
        return password;
    }
}