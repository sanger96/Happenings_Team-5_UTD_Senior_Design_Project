package com.example.happeningsapp.ui.clubSearch;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ClubSearchModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ClubSearchModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Clubs");
    }

    public LiveData<String> getText() {
        return mText;
    }
}