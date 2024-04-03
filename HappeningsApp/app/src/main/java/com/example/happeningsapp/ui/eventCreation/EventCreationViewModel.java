package com.example.happeningsapp.ui.eventCreation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EventCreationViewModel extends ViewModel {

    //create instance of variables for event creation
    private final MutableLiveData<String> pageTitle;
    private final MutableLiveData<String> name;
    private final MutableLiveData<String> description;
    private final MutableLiveData<String> startTime;
    private final MutableLiveData<String> endTime;
    private final MutableLiveData<String> location;
    private final MutableLiveData<String> room;

    public EventCreationViewModel() {
        //set variables to MutableLiveData type
        pageTitle = new MutableLiveData<>();
        name = new MutableLiveData<>();
        description = new MutableLiveData<>();
        startTime = new MutableLiveData<>();
        endTime = new MutableLiveData<>();
        location = new MutableLiveData<>();
        room = new MutableLiveData<>();

    }

    public LiveData<String> getText() {
        return pageTitle;
    }
    public LiveData<String> getName() {
        return name;
    }
    public LiveData<String> getDescription() {
        return description;
    }
    public LiveData<String> getStartTime() {
        return startTime;
    }
    public LiveData<String> getEndTime() {
        return endTime;
    }
    public LiveData<String> getLocation() {
        return location;
    }
    public LiveData<String> getRoom() {
        return room;
    }
}