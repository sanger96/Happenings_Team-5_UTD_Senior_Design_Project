package com.example.happeningsapp.ui.photoGallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.happeningsapp.databinding.FragmentPhotoGalleryBinding;

public class PhotoGalleryFragment extends Fragment {

    private FragmentPhotoGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PhotoGalleryViewModel photoGalleryViewModel =
                new ViewModelProvider(this).get(PhotoGalleryViewModel.class);

        binding = FragmentPhotoGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        photoGalleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}