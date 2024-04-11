package com.example.happeningsapp.ui.eventList;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.graphics.Color;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.happeningsapp.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.happeningsapp.databinding.FragmentEventListBinding;
import com.example.happeningsapp.ui.individualEvent.individualEventFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EventListFragment extends Fragment {

    private FragmentEventListBinding binding;
    private Spinner filterSpinner;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EventListViewModel eventListViewModel =
                new ViewModelProvider(this).get(EventListViewModel.class);

        binding = FragmentEventListBinding.inflate(inflater, container, false);
        binding.eventListTable.removeAllViews();
        View root = binding.getRoot();


        filterSpinner = root.findViewById(R.id.filter_spinner);

        // Populate the Spinner with filter options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.filter_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(adapter);

        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                // Clear existing event list
                binding.eventListTable.removeAllViews();

                String selectedFilter = (String) adapterView.getItemAtPosition(position);
                String getUrl = "http://10.0.2.2:8080/event/";
                if (selectedFilter.equals("Club")) {
                    getUrl += "getClubEvents";
                } else if (selectedFilter.equals("Campus")) {
                    getUrl += "getCampusEvents";
                } else {
                    getUrl += "getAll";
                }
                RequestQueue requestQueue = Volley.newRequestQueue(root.getContext());
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                    Request.Method.GET, getUrl, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                binding.eventListTable.removeAllViews();
                                List<Pair<JSONObject, LocalDateTime>> eventList = new ArrayList<>();

                                // Parse start times and populate the event list
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject event = response.getJSONObject(i);
                                    String startTimeStr = event.getJSONObject("appointment").getString("startTime");
                                    LocalDateTime startTime = LocalDateTime.parse(startTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                                    eventList.add(new Pair<>(event, startTime));
                                }

                                // Sort the event list by start times
                                Collections.sort(eventList, new Comparator<Pair<JSONObject, LocalDateTime>>() {
                                    @Override
                                    public int compare(Pair<JSONObject, LocalDateTime> o1, Pair<JSONObject, LocalDateTime> o2) {
                                        return o1.second.compareTo(o2.second);
                                    }
                                });
                                for (Pair<JSONObject, LocalDateTime> pair : eventList) {
                                    JSONObject event = pair.first;
                                    View eventRow = createEventRow(root.getContext(), event);
                                    binding.eventListTable.addView(eventRow);
                                }
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.wtf("Volley Fail", error.toString());
                    }
                });

                requestQueue.add(jsonArrayRequest);
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private View createEventRow(Context context, JSONObject event) throws JSONException {
        // Create a horizontal LinearLayout for each event
        LinearLayout eventRow = new LinearLayout(context);
        eventRow.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, // will take the width of the parent (the device)
                LinearLayout.LayoutParams.WRAP_CONTENT  // height will be based on the content
        ));
        eventRow.setOrientation(LinearLayout.HORIZONTAL);
        
        // Padding BETWEEN the text and the outline
        eventRow.setPadding(16, 16, 16, 16);

        // Create a vertical LinearLayout to hold the text details
        LinearLayout eventDetailsLayout = new LinearLayout(context);
        LinearLayout.LayoutParams detailsLayoutParams = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        );
        detailsLayoutParams.setMargins(0, 5, 22, 5); // Add right margin for spacing BETWEEN end of text and date box
        eventDetailsLayout.setLayoutParams(detailsLayoutParams);
        eventDetailsLayout.setOrientation(LinearLayout.VERTICAL); // we want the event name, club, desc, to be stacked vertically

        // Create TextViews for event name, club, and desc
        TextView eventNameView = createTextView(context, event.getString("name"), 20, 2, true);
        eventDetailsLayout.addView(eventNameView);
        if (!event.getString("club").equals("null")) {
            TextView eventClubView = createTextView(context, event.getJSONObject("club").getString("name"), 16, 1, false);
            eventDetailsLayout.addView(eventClubView);
        }
        TextView eventDescriptionView = createTextView(context, event.getString("description"), 16, 3, false);
        eventDetailsLayout.addView(eventDescriptionView);

        // Add event details layout to the event row
        eventRow.addView(eventDetailsLayout);

        // Create a TextView for the date information
        String dateTimeStr = event.getJSONObject("appointment").getString("startTime");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd");
        String formattedDateTime = dateTime.format(formatter).toUpperCase();
        TextView dateView = createDateView(context, formattedDateTime);
        eventRow.addView(dateView);

        // Add drawable as the background - a border on the top and bottom
        eventRow.setBackgroundResource(R.drawable.thin_border);

        int eventID = event.getInt("eventID");
        eventRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle click event here
                // For example, navigate to EventDetailsFragment passing event ID as argument
                Bundle args = new Bundle();
                args.putInt("eventID", eventID);
                Navigation.findNavController(view).navigate(R.id.action_nav_eventList_to_nav_individualEvent, args);
            }
        });

        return eventRow;
    }

    private TextView createDateView(Context context, String date) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int deviceWidth = (int) (0.125 * displayMetrics.widthPixels);
        TextView dateView = new TextView(context);
        dateView.setText(date);
        dateView.setTextSize(18);
        dateView.setTextColor(Color.WHITE);
        dateView.setBackgroundResource(R.drawable.date_background);
        dateView.setMaxWidth(deviceWidth);
        dateView.setPadding(16, 8, 16, 8);
        return dateView;
    }

    private TextView createTextView(Context context, String text, int textSize, int maxLines, boolean isBold) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int deviceWidth = (int) (0.80 * displayMetrics.widthPixels);

        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setTextSize(textSize);
        textView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        textView.setMaxWidth(deviceWidth); // Set maximum width to device width
        textView.setMaxLines(maxLines);
        textView.setEllipsize(TextUtils.TruncateAt.END);

        if (isBold) {
            textView.setTypeface(null, Typeface.BOLD);
        }

        return textView;
    }
}
