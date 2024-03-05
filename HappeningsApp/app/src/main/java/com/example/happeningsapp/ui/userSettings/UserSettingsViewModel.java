package com.example.happeningsapp.ui.userSettings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserSettingsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public UserSettingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is User Settings fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}