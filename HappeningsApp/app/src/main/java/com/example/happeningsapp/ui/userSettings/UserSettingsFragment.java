package com.example.happeningsapp.ui.userSettings;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast; //lets us have pop ups for user

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.happeningsapp.R;
import com.example.happeningsapp.databinding.FragmentUserSettingsBinding;

import org.json.JSONException;
import org.json.JSONObject;
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
        TextView pageTitle = binding.textUserSettings;

        // bind email and password
        // bind interests, get list of interests from math department club chooser page utd
        TextView email = binding.inTextEmail;
        TextView password = binding.inTextPassword;



        com.example.happeningsapp.GlobalVars gVars = com.example.happeningsapp.GlobalVars.getInstance();
        email.setText(gVars.getUsername());
        password.setText(gVars.getPassword());

        // get text for top of page if coming from account creation button
        if(!(getArguments()==null)){
            UserSettingsViewModelProvider.getText().observe(getViewLifecycleOwner(), pageTitle::setText);
            pageTitle.setText(getArguments().getString("pageTitle"));
        }

        // these commands will make sure the variables are observed for their "life cycle"
        UserSettingsViewModelProvider.getEmail().observe(getViewLifecycleOwner(), email::setText);
        UserSettingsViewModelProvider.getPassword().observe(getViewLifecycleOwner(), password::setText);

        Button showPassword = (Button) root.findViewById(R.id.button_showPassword);
        showPassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                password.setTransformationMethod(null);


            }
        });
//        start of adding action on button click
//        binding submit button
        Button submit = (Button) root.findViewById(R.id.button_createAccount);
        submit.setOnClickListener(new View.OnClickListener(){

            // These are for usage when updating the GlobalVars after successful profile update
            String tmpUsername;
            String tmpPassword;


            @Override
            public void onClick(View view){
//                Log.i("UserSettingsFragment","this is in onClick");
                //url we are posting to, uses 10.0.2.2 instead of local host, this is what android studio will need to use local host.
                // if you type local host it will automatically map to 127.0.0.1 aka the wrong place.
                String postUrl="http://10.0.2.2:8080/useraccount/update";
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
                        Navigation.findNavController(view).navigate(R.id.action_nav_userProfileSetting_to_nav_eventList);
                    }
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        error.printStackTrace();
                    }
                });
                requestQueue.add(jsonObjectRequest);
            } //end of onClick
        });//end of post request
//        end of adding action on button click

        return root;
    }

    public void volleyPostAddAccount(String email, String pass){
        String postUrl="http://localhost:8080/account/add";
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
                Log.i("UserSettingsFragment","this is onResponse");
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