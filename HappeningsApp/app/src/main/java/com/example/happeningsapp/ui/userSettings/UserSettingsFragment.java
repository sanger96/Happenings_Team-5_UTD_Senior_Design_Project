package com.example.happeningsapp.ui.userSettings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.happeningsapp.databinding.FragmentUserSettingsBinding;

public class UserSettingsFragment extends Fragment {

    private FragmentUserSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        UserSettingsViewModel photoGalleryViewModel =
                new ViewModelProvider(this).get(UserSettingsViewModel.class);

        binding = FragmentUserSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // bind default text at top of page
        final TextView textView = binding.textUserSettings;
        //get username and password
        final TextView username = binding.inTextUserName;
        final TextView password = binding.inTextPassword;
        textView.setText("testing");
        // get text for top of page
        photoGalleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        // i want to get these again after the button is clicked
        photoGalleryViewModel.getUsername().observe(getViewLifecycleOwner(), username::setText);
        photoGalleryViewModel.getPassword().observe(getViewLifecycleOwner(), password::setText);

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}