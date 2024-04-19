package com.example.happeningsapp.ui.CreateAccount;


import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.happeningsapp.GlobalVars;
import com.example.happeningsapp.MainActivity;
import com.example.happeningsapp.R;
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
import com.example.happeningsapp.databinding.FragmentCreateAccountBinding;


import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class CreateAccountFragment extends Fragment {

    private FragmentCreateAccountBinding binding;

    private View toastLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CreateAccountViewModel CreateAccountViewModelProvider =
                new ViewModelProvider(this).get(CreateAccountViewModel.class);

        binding = FragmentCreateAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        LayoutInflater toastInflater = getLayoutInflater();
        toastLayout = toastInflater.inflate(R.layout.custom_toast, (ViewGroup) root.findViewById(R.id.custom_toast_layout));

        // bind default text at top of page
        TextView pageTitle = binding.textCreateAccount;

        // bind email and password
        // bind interests, get list of interests from math department club chooser page utd
        TextView email = binding.inTextEmail;
        TextView password = binding.inTextPassword;

        // get text for top of page if coming from account creation button
        if(!(getArguments()==null)){
            CreateAccountViewModelProvider.getText().observe(getViewLifecycleOwner(), pageTitle::setText);
            pageTitle.setText(getArguments().getString("pageTitle"));
        }

        // these commands will make sure the variables are observed for their "life cycle"
        CreateAccountViewModelProvider.getEmail().observe(getViewLifecycleOwner(), email::setText);
        CreateAccountViewModelProvider.getPassword().observe(getViewLifecycleOwner(), password::setText);


//        start of adding action on button click
//        binding submit button
        Button submit = (Button) root.findViewById(R.id.button_createAccount2);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
//                Log.i("UserSettingsFragment","this is in onClick");
                //url we are posting to, uses 10.0.2.2 instead of local host, this is what android studio will need to use local host.
                // if you type local host it will automatically map to 127.0.0.1 aka the wrong place.
                com.example.happeningsapp.GlobalVars server =  com.example.happeningsapp.GlobalVars.getInstance();
                String postUrl= server.getServerUrl() + "/useraccount/add";
                RequestQueue requestQueue = Volley.newRequestQueue(root.getContext());

                //initializing the JSONObject that will be posted
                JSONObject postData = new JSONObject();
                try{
                    //This is how we will add elements to build the JSONObject post data
                    postData.put("email",email.getText().toString());
                    postData.put("password", password.getText().toString());

                    //this log method will appear in logcat
                    Log.i("UserSettingsFragment","JSONObject postData is built");
                } catch (JSONException e){
                    Log.i("UserSettingsFragment","JSONObject postData FAILED to build");
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,postUrl,postData, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //add toast for success
                        GlobalVars.getCustomToast(toastLayout, "Account Creation Successful", root, Toast.LENGTH_SHORT).show();
//                        Toast.makeText(root.getContext(), "Account Creation Successful",Toast.LENGTH_SHORT).show();
                        Log.i("Volley",response.toString());
                        Navigation.findNavController(view).navigate(R.id.action_createAccountFragment_to_nav_login);
                        com.example.happeningsapp.GlobalVars gVars = com.example.happeningsapp.GlobalVars.getInstance();


                        // This block saves the user account data in the global variables class
                        try {
                            gVars.setUsername(response.getString("email"));
                            gVars.setPassword(response.getString("password"));
                            gVars.setUserID(response.getInt("accountID"));
                        }
                        catch(Exception e)
                        {

                        }
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
        com.example.happeningsapp.GlobalVars server =  com.example.happeningsapp.GlobalVars.getInstance();
        String postUrl= server.getServerUrl() + "/useraccount/add";
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
                Log.i("CreateAccountFragment","this is onResponse");
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