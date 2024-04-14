package com.example.happeningsapp.ui.individualEvent;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.happeningsapp.R;
import com.example.happeningsapp.databinding.FragmentIndividualEventBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class individualEventFragment extends Fragment {

    private FragmentIndividualEventBinding binding;
    private int rsvpCount = 99;
    private boolean isRSVP = false;
    private Button rsvpButton;
    public static individualEventFragment newInstance() {
        return new individualEventFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        IndividualEventViewModel individualEventViewModel =
                new ViewModelProvider(this).get(IndividualEventViewModel.class);

        binding = FragmentIndividualEventBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        rsvpButton = root.findViewById(R.id.rsvpButton);
        LinearLayout backToEventListing = root.findViewById(R.id.backToEventListing);

        com.example.happeningsapp.GlobalVars foo =  com.example.happeningsapp.GlobalVars.getInstance();
        int userAccountID = foo.getUserID();
        backToEventListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_individualEvent_to_nav_eventList);
            }
        });



        rsvpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRSVP) {
                    rsvpCount++;
                    rsvpButton.setText("Un-RSVP | " + rsvpCount);
                    isRSVP = true;
                } else {
                    rsvpCount--;
                    rsvpButton.setText("RSVP | " + rsvpCount);
                    isRSVP = false;
                }
            }
        });

        int eventId = -1;
        Bundle args = getArguments();
        if (args != null) {
            eventId = args.getInt("eventID");

            com.example.happeningsapp.GlobalVars server =  com.example.happeningsapp.GlobalVars.getInstance();
            String getWhetherRsvpURl = server.getServerUrl() + "/useraccount/isRsvp/" + userAccountID + "_" + eventId;
            String getRsvpUrl= server.getServerUrl() + "/event/rsvpCount/" + eventId;
            String getUrl= server.getServerUrl() + "/event/getById/" + eventId;

            RequestQueue requestQueue = Volley.newRequestQueue(requireContext());

            StringRequest stringRequestWhetherRsvp = new StringRequest(
                    Request.Method.GET, getWhetherRsvpURl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                isRSVP = response.trim().equalsIgnoreCase("true");
                                updateRsvpButton();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("IndividualEventFragment", "Error fetching event details: " + error.getMessage());
                        }
                    }



            );

            StringRequest stringRequestRsvp = new StringRequest(
                    Request.Method.GET, getRsvpUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                rsvpCount = Integer.parseInt(response.trim());
                                updateRsvpButton();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("IndividualEventFragment", "Error fetching event details: " + error.getMessage());
                        }
                    }
            );

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET, getUrl, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                ImageView eventImageView = root.findViewById(R.id.eventImageView);
                                // Parse the JSON response to get event details
                                String eventName = response.getString("name");
                                String eventDescription = response.getString("description");
                                String imageUrl = response.getString("photoSubDirectory");
                                String location = response.getJSONObject("appointment").getJSONObject("location").getString("name");
                                String room = response.getJSONObject("appointment").getJSONObject("location").getString("room");
                                String startTime = response.getJSONObject("appointment").getString("startTime");
                                String endTime = response.getJSONObject("appointment").getString("endTime");

                                String [] formattedDateAndTime = formatDateTimeRange(startTime, endTime);
                                Glide.with(root.getContext())
                                        .load(imageUrl)
                                        .into(eventImageView);

                                // Update UI with event details
                                TextView eventNameTextView = root.findViewById(R.id.eventNameTextView);
                                eventNameTextView.setText(eventName);

                                TextView eventDescriptionTextView = root.findViewById(R.id.eventDescriptionTextView);
                                eventDescriptionTextView.setText(eventDescription);

                                TextView dateTextView = root.findViewById(R.id.dateTextView);
                                dateTextView.setText(formattedDateAndTime[0]);

                                TextView timeTextView = root.findViewById(R.id.timeTextView);
                                if (!formattedDateAndTime[1].contains("12:00 AM - 12:00 AM")) {
                                    timeTextView.setText(formattedDateAndTime[1]);
                                } else {
                                    ViewGroup parent = (ViewGroup) timeTextView.getParent();
                                    if (parent != null) {
                                        parent.removeView(timeTextView);
                                    }
                                }

                                TextView locationTextView = root.findViewById(R.id.locationTextView);
                                locationTextView.setText(location);

                                TextView roomTextView = root.findViewById(R.id.roomTextView);
                                if (!room.equals("null")) {
                                    roomTextView.setText(room);
                                } else {
                                    ViewGroup parent = (ViewGroup) roomTextView.getParent();
                                    if (parent != null) {
                                        parent.removeView(roomTextView);
                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("IndividualEventFragment", "Error fetching event details: " + error.getMessage());
                        }
                    }
            );

            // Add the request to the queue

            requestQueue.add(stringRequestWhetherRsvp);
            requestQueue.add(stringRequestRsvp);
            requestQueue.add(jsonObjectRequest);
        }


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public static String[] formatDateTimeRange(String start, String end) {
        // Parsing the input strings
        LocalDateTime startLdt = LocalDateTime.parse(start);
        LocalDateTime endLdt = LocalDateTime.parse(end);

        // Formatting the date
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("E, d MMM yyyy");
        String formattedDate = startLdt.format(dateFormatter);

        // Formatting the time range
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        String formattedStartTime = startLdt.format(timeFormatter);
        String formattedEndTime = endLdt.format(timeFormatter);
        String formattedTimeRange = formattedStartTime + " - " + formattedEndTime;

        // Returning both formatted date and time range
        return new String[] { formattedDate, formattedTimeRange };
    }

    private void updateRsvpButton() {
        if (isRSVP) {
            rsvpButton.setText("UN-RSVP | " + rsvpCount);
        } else {
            rsvpButton.setText("RSVP | " + rsvpCount);
        }
    }

}
