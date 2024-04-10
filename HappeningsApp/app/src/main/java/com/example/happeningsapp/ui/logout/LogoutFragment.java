package com.example.happeningsapp.ui.logout;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.happeningsapp.GlobalVars;
import com.example.happeningsapp.R;
import com.example.happeningsapp.databinding.FragmentLogoutBinding;

public class LogoutFragment extends Fragment {

    private FragmentLogoutBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LogoutViewModel UserSettingsViewModelProvider =
                new ViewModelProvider(this).get(LogoutViewModel.class);

        binding = FragmentLogoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // bind default text at top of page







        Button buttonLogout = (Button) root.findViewById(R.id.button_logout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Clear account information
                GlobalVars accountDetails = com.example.happeningsapp.GlobalVars.getInstance();
                accountDetails.setUserID(-1);
                accountDetails.setUsername("");
                accountDetails.setPassword("");

                Navigation.findNavController(view).navigate(R.id.action_nav_logout_to_nav_login);

            }
        });
//
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}