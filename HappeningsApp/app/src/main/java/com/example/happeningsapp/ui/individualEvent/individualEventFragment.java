package com.example.happeningsapp.ui.individualEvent;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.happeningsapp.R;
import com.example.happeningsapp.databinding.FragmentIndividualClubBinding;
import com.example.happeningsapp.databinding.FragmentIndividualEventBinding;
import com.example.happeningsapp.ui.clubSettings.ClubSettingsViewModel;

public class individualEventFragment extends Fragment {

    private FragmentIndividualEventBinding binding;

    public static individualEventFragment newInstance() {
        return new individualEventFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        IndividualEventViewModel IndividualEventViewModelProvider =
                new ViewModelProvider(this).get(IndividualEventViewModel.class);

        binding = com.example.happeningsapp.databinding.FragmentIndividualEventBinding.inflate(inflater, container, false);
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