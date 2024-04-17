package com.example.happeningsapp.ui.appSettings;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.happeningsapp.R;
import com.example.happeningsapp.databinding.FragmentAppSettingsBinding;
import com.example.happeningsapp.databinding.FragmentLoginBinding;
import com.example.happeningsapp.ui.login.LoginViewModel;

import org.json.JSONException;
import org.json.JSONObject;

public class AppSettingsFragment extends Fragment {

    private FragmentAppSettingsBinding binding;

    public static AppSettingsFragment newInstance() {
        return new AppSettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        AppSettingsViewModel AppSettingsViewModelProvider =
                new ViewModelProvider(this).get(AppSettingsViewModel.class);

        binding = FragmentAppSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //bind button
        Button accountCreator = (Button) root.findViewById(R.id.FROMsettingsTOeventCreation);

        accountCreator.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Navigation.findNavController(view).navigate(R.id.action_nav_settings_to_eventCreationFragment);
            }
        });

        //create instance of golbal vars
        com.example.happeningsapp.GlobalVars globalVars =  com.example.happeningsapp.GlobalVars.getInstance();
        EditText timeWindow = binding.timeWindow;

        timeWindow.setText( String.valueOf(globalVars.getTimeWindow()) );

        Button updateTimeWindow = (Button) root.findViewById(R.id.button_timeWindow);
        updateTimeWindow.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                globalVars.setTimeWindow(Integer.parseInt(timeWindow.getText().toString()));
            }
        });

        //TODO:(Gaurav) add stuff from profileSettings here and remove profile settings from the side bar


        //end of adding action on button click

        return root;
    }

}