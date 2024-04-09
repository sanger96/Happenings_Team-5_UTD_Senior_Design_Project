package com.example.happeningsapp.ui.clubSettings;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.happeningsapp.R;
import com.example.happeningsapp.databinding.FragmentAppSettingsBinding;
import com.example.happeningsapp.databinding.FragmentClubSettingsBinding;
import com.example.happeningsapp.databinding.FragmentLoginBinding;
import com.example.happeningsapp.ui.appSettings.AppSettingsViewModel;

public class clubSettingsFragment extends Fragment {

    private FragmentClubSettingsBinding binding;

    public static clubSettingsFragment newInstance() {
        return new clubSettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ClubSettingsViewModel ClubSettingsViewModelProvider =
                new ViewModelProvider(this).get(ClubSettingsViewModel.class);

        binding = FragmentClubSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //bind button
//        Button buttonName = (Button) root.findViewById(R.id.nameOfButtonInLayout);

        //set what happens when button is clicked
//        buttonName.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View view){

                    //how to navigate to next page
//                Navigation.findNavController(view).navigate(R.id.nameOfNavigationID);
//            }
//        });
//        //end of adding action on button click

        return root;
    }

}