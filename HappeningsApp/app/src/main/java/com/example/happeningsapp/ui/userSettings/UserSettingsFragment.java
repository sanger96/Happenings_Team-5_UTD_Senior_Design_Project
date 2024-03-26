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

import com.example.happeningsapp.R;
import com.example.happeningsapp.databinding.FragmentUserSettingsBinding;

import org.w3c.dom.Text;

public class UserSettingsFragment extends Fragment {

    private FragmentUserSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        UserSettingsViewModel UserSettingsViewModelProvider =
                new ViewModelProvider(this).get(UserSettingsViewModel.class);

        binding = FragmentUserSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // bind default text at top of page
        final TextView pageTitle = binding.textUserSettings;

        // bind email and password
        // bind interests, get list of interests from math department club chooser page utd
        EditText email = binding.inTextEmail;
        EditText password = binding.inTextPassword;
        // get text for top of page
        UserSettingsViewModelProvider.getText().observe(getViewLifecycleOwner(), pageTitle::setText);
        // these commands will make sure the variables are observed for their "life cycle"
        UserSettingsViewModelProvider.getText().observe(getViewLifecycleOwner(), email::setText);
        UserSettingsViewModelProvider.getText().observe(getViewLifecycleOwner(), password::setText);


//        start of adding action on button click
//        binding submit button
        Button submit = (Button) root.findViewById(R.id.button_createAccount);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //TODO add Post method here
                //if statement for passing email and password to back end to create account
                //http://localhost:<3306>/useraccount/add


            }
        });
//        end of adding action on button click


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}