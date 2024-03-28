package com.example.happeningsapp.ui.userSettings;

import android.os.Bundle;
import android.util.Log;
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
                Log.i("UserSettingsFragment","this is in onClick");
                //volleyPostAddAccount(email.getText().toString(),password.getText().toString());
                String postUrl="http://10.0.2.2:8080/account/add";
                RequestQueue requestQueue = Volley.newRequestQueue(root.getContext());

                JSONObject postData = new JSONObject();
                try{
                    postData.put("email",email.getText().toString());
                    postData.put("password", password.getText().toString());

                    Log.i("UserSettingsFragment","this is try");
                } catch (JSONException e){
                    Log.i("UserSettingsFragment","this is catch");
                    e.printStackTrace();
                }
//                JSONObject postData = JSONObject.quote({ "email": "VScode" , "password": "testing from VScode" });

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,postUrl,postData, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //add toast for success
                        //Toast.makeText(UserSettingsFragment.getContext(), "Post Successful",Toast.LENGTH_SHORT).show();
                        Log.i("Volley",response.toString());
                    }
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        error.printStackTrace();
                    }
                });
                requestQueue.add(jsonObjectRequest);
            }
        });
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