package com.example.happeningsapp.ui.individualClub;

import androidx.annotation.ColorInt;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.happeningsapp.R;
import com.example.happeningsapp.databinding.FragmentIndividualClubBinding;


import org.json.JSONException;
import org.json.JSONObject;

public class individualClubFragment extends Fragment {

    private FragmentIndividualClubBinding binding;
    private ImageButton favoriteButton;
    private boolean isFavorite = false;

    public static individualClubFragment newInstance() {
        return new individualClubFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        IndividualClubViewModel IndividualClubViewModelProvider =
                new ViewModelProvider(this).get(IndividualClubViewModel.class);

        binding = com.example.happeningsapp.databinding.FragmentIndividualClubBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        favoriteButton = root.findViewById(R.id.favoriteClubButton);

//        favoriteButton.setBackgroundColor(0xF5427E);
//        if(!isFavorite){
//            favoriteButton.setBackgroundColor(0xF5427E);
//        }
//        else{
//            favoriteButton.setBackgroundColor(0xF5427E);
//        }

        com.example.happeningsapp.GlobalVars foo =  com.example.happeningsapp.GlobalVars.getInstance();
        int userAccountID = foo.getUserID();

        int clubId = -1;
        // Get the clubID from bundle if args not null
        Bundle args = getArguments();
        if (args != null) {
            clubId = args.getInt("clubID");

            // Get the club by using serverUrl
            com.example.happeningsapp.GlobalVars server =  com.example.happeningsapp.GlobalVars.getInstance();
            String getClubUrl= server.getServerUrl() + "/club/getById/" + clubId;

            RequestQueue requestQueue = Volley.newRequestQueue(requireContext());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET, getClubUrl, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                // Parse the JSON response to get club details
                                String clubName = response.getString("name");
                                String clubDescription = response.getString("description");

                                // Set name
                                TextView clubNameTextView = root.findViewById(R.id.clubNameTextView);
                                clubNameTextView.setText(clubName);
                                // Set description
                                TextView clubDescriptionTextView = root.findViewById(R.id.clubDescriptionTextView);
                                clubDescriptionTextView.setText(clubDescription);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("IndividualclubFragment", "Error fetching club details: " + error.getMessage());
                        }
                    }
            );

            // Add the request to the queue
            requestQueue.add(jsonObjectRequest);
        }

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