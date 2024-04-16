package com.example.happeningsapp.ui.individualClub;

import androidx.annotation.ColorInt;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

//import com.example.happeningsapp.ui.eventList.EventListFragment

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.happeningsapp.R;
import com.example.happeningsapp.databinding.FragmentIndividualClubBinding;
import com.example.happeningsapp.ui.eventList.EventListFragment;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class individualClubFragment extends Fragment {

    private FragmentIndividualClubBinding binding;
    private ImageButton favoriteButton;
    private TableLayout clubEventsTable;
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

        clubEventsTable = root.findViewById(R.id.clubEventsTable);

        favoriteButton = root.findViewById(R.id.favoriteClubButton);
        favoriteButton.setImageResource(R.drawable.ic_unfavorite);

        com.example.happeningsapp.GlobalVars foo =  com.example.happeningsapp.GlobalVars.getInstance();
        int userAccountID = foo.getUserID();

        int clubId = -1;
        // Get the clubID from bundle if args not null
        Bundle args = getArguments();
        if (args != null) {
            clubId = args.getInt("clubID");

            // Get the club and display data
            com.example.happeningsapp.GlobalVars server =  com.example.happeningsapp.GlobalVars.getInstance();
            String getClubUrl= server.getServerUrl() + "/club/getById/" + clubId;

            RequestQueue requestQueue = Volley.newRequestQueue(requireContext());

            JsonObjectRequest clubRequest = new JsonObjectRequest(
                    Request.Method.GET, getClubUrl, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.d("Club Object", response.toString());
                                // Parse the JSON response to get club details
                                String clubName = response.getString("name");
                                String clubDescription = response.getString("description");

                                // Set name
                                TextView clubNameTextView = root.findViewById(R.id.clubNameTextView);
                                clubNameTextView.setText(clubName);
                                // Set description
                                TextView clubDescriptionTextView = root.findViewById(R.id.clubDescriptionTextView);
                                clubDescriptionTextView.setText(clubDescription);

                                // TODO: check if the user has already favorited the club
                                // Log.d("Club type of object ", response.getJSONArray("userAccounts").toString());
                                /*JSONArray favUserAccounts = response.getJSONArray("userAccounts");
                                for(int i = 0; i < favUserAccounts.length(); i++){
                                    JSONObject userAcc = favUserAccounts.getJSONObject(i);
                                    int userAccId = userAcc.getInt("userAccountID")
                                }*/

                                // Create Event Rows
                                JSONArray clubEvents = response.getJSONArray("events");
                                for(int i = 0; i < clubEvents.length(); i++){
                                    JSONObject event = clubEvents.getJSONObject(i);
                                    View eventRow = createEventRow(root.getContext(), event);

                                    binding.clubEventsTable.addView(eventRow);
                                }

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

            favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!isFavorite){
                        favoriteButton.setImageResource(R.drawable.ic_favorite);
                    }
                    else{
                        favoriteButton.setImageResource(R.drawable.ic_unfavorite);
                    }
                    isFavorite = !isFavorite;
                }
            });

            // Add the request to the queue
            requestQueue.add(clubRequest);
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

    private View createEventRow(Context context, JSONObject event) throws JSONException{
        // Create a horizontal LinearLayout for each event
        LinearLayout eventRow = new LinearLayout(context);
        TableLayout.LayoutParams rowLayoutParams = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT, // will take the width of the parent (the device)
                TableLayout.LayoutParams.WRAP_CONTENT  // height will be based on the content
        );
        rowLayoutParams.setMargins(0,0,0, 0);
        eventRow.setLayoutParams(rowLayoutParams);
        eventRow.setOrientation(LinearLayout.VERTICAL);
        // Padding BETWEEN the text and the outline
        eventRow.setPadding(16, 16, 16, 16);


        // Create a vertical LinearLayout to hold the text details
        LinearLayout eventDetailsLayout = new LinearLayout(context);
        LinearLayout.LayoutParams detailsLayoutParams = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        );
        detailsLayoutParams.setMargins(22, 5, 22, 5); // Add right margin for spacing BETWEEN end of text and date box
        eventDetailsLayout.setPadding(0,0,0,10);
        eventDetailsLayout.setLayoutParams(detailsLayoutParams);
        eventDetailsLayout.setOrientation(LinearLayout.VERTICAL); // we want the event name, desc, to be stacked vertically

        // Create TextViews for event name, and desc
        TextView eventNameView = EventListFragment.createTextView(context, event.getString("name"), 20, 2, true);
        eventRow.addView(eventNameView);
        TextView eventDescriptionView = EventListFragment.createTextView(context, event.getString("description"), 16, 3, false);
        eventRow.addView(eventDescriptionView);
        // Add event details layout to the event row
        eventRow.addView(eventDetailsLayout);

        // Get dates and create a TextView for the date information
        String dateTimeStr = event.getJSONObject("appointment").getString("startTime");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd");
        String formattedDateTime = dateTime.format(formatter).toUpperCase();

        TextView dateView = EventListFragment.createDateView(context, formattedDateTime);
        ViewGroup.LayoutParams dateLayoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        dateView.setLayoutParams(dateLayoutParams);
        dateView.setGravity(Gravity.CENTER);
        // Add date view to event row
        eventRow.addView(dateView);

        // Add drawable as the background - a border on the top and bottom
        eventRow.setBackgroundResource(R.drawable.thin_border);

        int eventID = event.getInt("eventID");
        eventRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putInt("eventID", eventID);
                Navigation.findNavController(view).navigate(R.id.action_nav_eventList_to_nav_individualEvent, args);
            }
        });

        return eventRow;
    }
}