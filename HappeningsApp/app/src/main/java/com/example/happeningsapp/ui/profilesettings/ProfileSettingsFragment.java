package com.example.happeningsapp.ui.profilesettings;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast; //lets us have pop ups for user

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.happeningsapp.GlobalVars;
import com.example.happeningsapp.R;
import com.example.happeningsapp.databinding.FragmentEventCreationBinding;
import com.example.happeningsapp.databinding.FragmentProfileSettingsBinding;
import com.example.happeningsapp.ui.eventCreation.EventCreationViewModel;
import com.example.happeningsapp.ui.individualClub.individualClubFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileSettingsFragment extends Fragment {

    private FragmentProfileSettingsBinding binding;
    private View toastLayout;

    public static ProfileSettingsFragment newInstance() {
        return new ProfileSettingsFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        ProfileSettingsViewModel ProfileSettingsViewModelProvider =
                new ViewModelProvider(this).get(ProfileSettingsViewModel.class);
        binding = FragmentProfileSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        LayoutInflater toastInflater = getLayoutInflater();
        toastLayout = toastInflater.inflate(R.layout.custom_toast, (ViewGroup) root.findViewById(R.id.custom_toast_layout));


        // Get the activity's ActionBar and set the title
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Profile Settings");
        }

        // bind default text at top of page
        TextView pageTitle = binding.textProfileSettings;
        TextView email = binding.profileEmail;
        TextView password = binding.profilePassword;


        // these commands will make sure the variables are observed for their "life cycle"
        ProfileSettingsViewModelProvider.getEmail().observe(getViewLifecycleOwner(), email::setText);
        ProfileSettingsViewModelProvider.getPassword().observe(getViewLifecycleOwner(), password::setText);






        com.example.happeningsapp.GlobalVars gVars = com.example.happeningsapp.GlobalVars.getInstance();
        email.setText(gVars.getUsername());
        password.setText(gVars.getPassword());


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
        });
//        start of adding action on button click
//        binding submit button
        Button submit = (Button) root.findViewById(R.id.button_profile_update);
        submit.setOnClickListener(new View.OnClickListener(){

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
                        GlobalVars.getCustomToast(toastLayout, "Profile Updated", root, Toast.LENGTH_SHORT).show();
//                        Toast.makeText(root.getContext(), "Profile Updated",Toast.LENGTH_SHORT).show();
                        Log.i("Volley",response.toString());
                        Navigation.findNavController(view).navigate(R.id.action_profileSettingsFragment_to_nav_eventList);
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
        return root;
    }

    public void volleyPostAddAccount(String email, String pass){
        com.example.happeningsapp.GlobalVars server =  com.example.happeningsapp.GlobalVars.getInstance();
        String postUrl= server.getServerUrl() + "/useraccount/update";
        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());

        JSONObject postData = new JSONObject();
        try{
            postData.put("email",email);
            postData.put("password", pass);
        } catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,postUrl,postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //add toast for success
                //Toast.makeText(UserSettingsFragment.getContext(), "Post Successful",Toast.LENGTH_SHORT).show();
                System.out.println(response);
                Log.i("ProfileSettingsFragment","this is onResponse");
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                error.printStackTrace();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }




}