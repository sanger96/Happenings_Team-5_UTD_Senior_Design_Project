package com.example.happeningsapp.ui.CreateAccount;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreateAccountViewModel extends ViewModel {

    private final MutableLiveData<String> pageTitle;
    private final MutableLiveData<String> email;
    private final MutableLiveData<String> password;

    public CreateAccountViewModel() {
        //instantiates pageTitle as a MutableLiveData, I know this should not be mutable, but for now following a known working method.
        pageTitle = new MutableLiveData<>();
        //sets pageTitle
        //pageTitle.setValue("Account Creation");
        //the comments below are for reference only, this is from another page
        email = new MutableLiveData<>();
        //sets username, don't want to set it, so hint in fragment will be used
        //email.setValue("Enter username");
        password = new MutableLiveData<>();
        //sets password, don't want to set it, so hint in fragment will be used
        //password.setValue("Enter password");

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