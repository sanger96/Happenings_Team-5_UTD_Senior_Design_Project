package com.example.happeningsapp.ui.individualEvent;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.happeningsapp.R;
import com.example.happeningsapp.databinding.FragmentIndividualEventBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class individualEventFragment extends Fragment {

    private FragmentIndividualEventBinding binding;

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

        int eventId = -1;
        Bundle args = getArguments();
        if (args != null) {
            eventId = args.getInt("eventID");
            // Now you have the event ID, you can use it to fetch event details from your data source
            // For demonstration purposes, let's just display the event ID in a TextView
            TextView eventIdTextView = root.findViewById(R.id.eventIdTextView);
            eventIdTextView.setText("Event ID: " + eventId);

            // Send a request to get event details based on the event ID
            String getUrl = "http://10.0.2.2:8080/event/getById/" + eventId;
            RequestQueue requestQueue = Volley.newRequestQueue(requireContext());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET, getUrl, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                // Parse the JSON response to get event details
                                String eventName = response.getString("name");
                                String eventDescription = response.getString("description");

                                // Update UI with event details
                                TextView eventNameTextView = root.findViewById(R.id.eventNameTextView);
                                eventNameTextView.setText(eventName);

                                TextView eventDescriptionTextView = root.findViewById(R.id.eventDescriptionTextView);
                                eventDescriptionTextView.setText(eventDescription);
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
            requestQueue.add(jsonObjectRequest);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
