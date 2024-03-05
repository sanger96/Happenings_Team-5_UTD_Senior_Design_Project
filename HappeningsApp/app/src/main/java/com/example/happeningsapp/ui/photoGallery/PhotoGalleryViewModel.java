package com.example.happeningsapp.ui.photoGallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PhotoGalleryViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PhotoGalleryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}