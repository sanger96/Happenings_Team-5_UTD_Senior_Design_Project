package com.example.happeningsapp.ui.clubSearch;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ClubSearchModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ClubSearchModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the club search list fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}