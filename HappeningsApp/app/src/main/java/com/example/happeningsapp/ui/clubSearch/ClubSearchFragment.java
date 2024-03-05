package com.example.happeningsapp.ui.clubSearch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.happeningsapp.databinding.FragmentClubSearchBinding;

public class ClubSearchFragment extends Fragment {

    private FragmentClubSearchBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ClubSearchModel clubSearchModel =
                new ViewModelProvider(this).get(ClubSearchModel.class);

        binding = FragmentClubSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        clubSearchModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

//     For when you are ready for adding input text on clubSearchFragment
//https://developer.android.com/develop/ui/views/touch-and-input/creating-input-method
//    @Override
//    public View onCreateInputView() {
//        MyKeyboardView inputView =
//                (MyKeyboardView) getLayoutInflater().inflate(R.layout.input, null);
//
//        inputView.setOnKeyboardActionListener(this);
//        inputView.setKeyboard(latinKeyboard);
//
//        return inputView;
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}