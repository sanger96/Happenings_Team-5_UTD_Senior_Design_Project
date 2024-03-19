package com.example.happeningsapp.ui.login;

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
import androidx.navigation.NavController;

import com.example.happeningsapp.R;
import com.example.happeningsapp.databinding.FragmentLoginBinding;

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
        EditText username = binding.inTextUserName;
        EditText password = binding.inTextPassword;
        // get text for top of page
        LoginViewModelProvider.getText().observe(getViewLifecycleOwner(), textView::setText);
        // these commands will make sure the variables are observed for their "life cycle"
        LoginViewModelProvider.getUsername().observe(getViewLifecycleOwner(), username::setText);
        LoginViewModelProvider.getPassword().observe(getViewLifecycleOwner(), password::setText);


        //start of adding action on button click
        //binding submit button
        Button submit = (Button) root.findViewById(R.id.button_submitLogin);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //if statement for seeing if username and password is accepted
                if(username.getText().toString().equals("Enter username")&& password.getText().toString().equals("Enter password")){
                    Toast.makeText(root.getContext(), "Login Successful",Toast.LENGTH_SHORT).show();
                    //the below line should make the app go to that page on successful login
                    //NavController.navigate(R.id.action_nav_login_to_nav_eventList);

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