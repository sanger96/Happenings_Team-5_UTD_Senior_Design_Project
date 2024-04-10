package com.example.happeningsapp.ui.clubCreation;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.happeningsapp.R;
import com.example.happeningsapp.databinding.FragmentClubCreationBinding;
import com.example.happeningsapp.databinding.FragmentClubSettingsBinding;
import com.example.happeningsapp.ui.appSettings.AppSettingsViewModel;

public class clubCreationFragment extends Fragment {

    private FragmentClubCreationBinding binding;

    public static clubCreationFragment newInstance() {
        return new clubCreationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ClubCreationViewModel ClubCreationViewModelProvider =
                new ViewModelProvider(this).get(ClubCreationViewModel.class);

        binding = FragmentClubCreationBinding.inflate(inflater, container, false);
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