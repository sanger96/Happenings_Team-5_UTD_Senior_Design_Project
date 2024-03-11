package com.example.happeningsapp.ui.userSettings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast; //lets us have pop ups for user

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.happeningsapp.MainActivity;
import com.example.happeningsapp.R;
import com.example.happeningsapp.databinding.FragmentUserSettingsBinding;
import com.google.android.material.snackbar.Snackbar;

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
        // bind username and password
        EditText username = binding.inTextUserName;
        EditText password = binding.inTextPassword;
        // get text for top of page
        photoGalleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        // i want to get these again after the button is clicked
        photoGalleryViewModel.getUsername().observe(getViewLifecycleOwner(), username::setText);
        photoGalleryViewModel.getPassword().observe(getViewLifecycleOwner(), password::setText);


        //start of adding action on button click
        //binding submit button
        Button submit = (Button) root.findViewById(R.id.button_submitUserSettings);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //if statement for seeing if username and password is accepted
                if(username.getText().toString().equals("Enter Username")&& password.getText().toString().equals("Enter Password")){
                    Toast.makeText(root.getContext(), "Login Successful",Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(root.getContext(), "Login Failed Miserably",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //end of adding action on button click


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}