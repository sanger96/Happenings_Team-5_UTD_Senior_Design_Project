package com.example.happeningsapp.ui.login;

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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.happeningsapp.R;
import com.example.happeningsapp.databinding.FragmentLoginBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class loginFragment extends Fragment {

   private FragmentLoginBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LoginViewModel LoginViewModelProvider =
                new ViewModelProvider(this).get(LoginViewModel.class);

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // bind default text at top of page
        final TextView textView = binding.textLogin;
        // bind username and password
        EditText email = binding.inTextUserName;
        EditText password = binding.inTextPassword;
        // get text for top of page
        LoginViewModelProvider.getText().observe(getViewLifecycleOwner(), textView::setText);
        // these commands will make sure the variables are observed for their "life cycle"
        LoginViewModelProvider.getUsername().observe(getViewLifecycleOwner(), email::setText);
        LoginViewModelProvider.getPassword().observe(getViewLifecycleOwner(), password::setText);


        //start of adding action on button click
        //binding submit button
        Button submit = (Button) root.findViewById(R.id.button_submitLogin);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //if statement for seeing if username and password is accepted
                //need to add get method statement to send this to backend.
                String getUrl="http://10.0.2.2:8080/useraccount/checkLogin";

                //holds request queue
                RequestQueue requestQueue = Volley.newRequestQueue(root.getContext());

                //for JSONObject to be sent as request
                JSONObject emailAndPass = new JSONObject();
                //try to put the email and password in emailAndPass
                try{
                    emailAndPass.put("email",email.getText().toString());
                    emailAndPass.put("password", password.getText().toString());
                } catch (JSONException e){
                    e.printStackTrace();
                }

                JsonObjectRequest auth = new JsonObjectRequest(Request.Method.GET, getUrl, emailAndPass, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        //log method for debugging
                        Log.d("Volley PASS onResponse", "This is before the if statement");

                        // Authentication should take place in back end and will verify, then pass a boolean pass/fail back here
                        if(response.toString().equals("1")){
                            Toast.makeText(root.getContext(), "Login Successful",Toast.LENGTH_SHORT).show();
                            //the below line should make the app go to that page on successful login
                            Navigation.findNavController(view).navigate(R.id.action_nav_login_to_nav_eventList);

                            //log method for debugging
                            Log.d("Volley PASS onResponse", "This is inside the if statement; if true");

                        }else{
                            Toast.makeText(root.getContext(), "Incorrect email and password combination",Toast.LENGTH_SHORT).show();

                            //log method for debugging
                            Log.d("Volley PASS onResponse", "This is inside the if statement; if false");
                        }

                    }//end of onResponse
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.wtf("Volley Fail onErrorResponse",error.toString());

                    }
                });
                //add to queue
                requestQueue.add(auth);

                //add retry policy, seconds * millisec to sec conversion, number of retries, multiply  last timeout by this on the retry
                auth.setRetryPolicy(new DefaultRetryPolicy(10*1000,3,2.0f));
            }
        });
        //end of adding action on button click

        //start of adding action on account creation button click
        //binding account creation button
        Button accountCreator = (Button) root.findViewById(R.id.button_goToAccountCreation);
        accountCreator.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View lambda){
                //go to account creation page
                Navigation.findNavController(lambda).navigate(R.id.action_nav_login_to_nav_accountCreation);

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