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
import android.widget.TextView;
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
        });//end of adding action on button click

        //TODO:(Gaurav) add stuff from profileSettings here and remove profile settings from the side bar

        //start of update email and password stuff
        // bind default text at top of page
        TextView email = binding.profileEmail;
        TextView password = binding.profilePassword;

        // these commands will make sure the variables are observed for their "life cycle"
        AppSettingsViewModelProvider.getEmail().observe(getViewLifecycleOwner(), email::setText);
        AppSettingsViewModelProvider.getPassword().observe(getViewLifecycleOwner(), password::setText);

        //set globalVars
        email.setText(globalVars.getUsername());
        password.setText(globalVars.getPassword());

        Button showPassword = (Button) root.findViewById(R.id.button_profile_show);
        showPassword.setOnClickListener(new View.OnClickListener() {
            private boolean isPasswordShown = false;

            @Override
            public void onClick(View view) {

                if (isPasswordShown) {
                    // Hide the password
                    password.setTransformationMethod(new android.text.method.PasswordTransformationMethod());
                    showPassword.setText("Show");
                    isPasswordShown = false;
                } else {
                    // Show the password
                    password.setTransformationMethod(null);
                    showPassword.setText("Hide");
                    isPasswordShown = true;
                }
            }
        }); // end of onClick listener for button to show password

        // binding submit button
        Button updateEmailAndPass = (Button) root.findViewById(R.id.button_profile_update);
        updateEmailAndPass.setOnClickListener(new View.OnClickListener(){

            // These are for usage when updating the GlobalVars after successful profile update
            String tmpUsername;
            String tmpPassword;


            @Override
            public void onClick(View view){

//                Log.i("UserSettingsFragment","this is in onClick");
                //url we are posting to, uses 10.0.2.2 instead of local host, this is what android studio will need to use local host.
                // if you type local host it will automatically map to 127.0.0.1 aka the wrong place.
                com.example.happeningsapp.GlobalVars server =  com.example.happeningsapp.GlobalVars.getInstance();
                String postUrl= server.getServerUrl() + "/useraccount/update";
                RequestQueue requestQueue = Volley.newRequestQueue(root.getContext());

                //initializing the JSONObject that will be posted
                JSONObject postData = new JSONObject();
                try{
                    //This is how we will add elements to build the JSONObject post data
                    postData.put("accountID", com.example.happeningsapp.GlobalVars.getInstance().getUserID());
                    postData.put("email",email.getText().toString());
                    postData.put("password", password.getText().toString());

                    // Save so we can update GlobalVars if profile update succeeds.
                    tmpUsername = email.getText().toString();
                    tmpPassword = password.getText().toString();

                    //this log method will appear in logcat
                    Log.i("UserSettingsFragment","JSONObject postData is built");
                } catch (JSONException e){
                    Log.i("UserSettingsFragment","JSONObject postData FAILED to build");
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT,postUrl,postData, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        // Update GlobalVars since profile info has changed.
                        com.example.happeningsapp.GlobalVars tmp =  com.example.happeningsapp.GlobalVars.getInstance();
                        tmp.setUsername(tmpUsername);
                        tmp.setPassword(tmpPassword);


                        //add toast for success
                        Toast.makeText(root.getContext(), "Profile Updated",Toast.LENGTH_SHORT).show();
                        Log.i("Volley",response.toString());
                        Navigation.findNavController(view).navigate(R.id.action_nav_settings_to_nav_login);
                    }
                } , new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        error.printStackTrace();
                        Log.i("onErrorResponse", "an error has occurred");
                    }

                });
                requestQueue.add(jsonObjectRequest);
            } //end of onClick
        });//end of post request
//        end of adding action on button click


        //end of update email and password stuff



        return root;
    }

}