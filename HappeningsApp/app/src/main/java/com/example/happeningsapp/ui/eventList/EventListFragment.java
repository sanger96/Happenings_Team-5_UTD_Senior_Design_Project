package com.example.happeningsapp.ui.eventList;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

import com.example.happeningsapp.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.happeningsapp.databinding.FragmentEventListBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class EventListFragment extends Fragment {

    private FragmentEventListBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EventListViewModel eventListViewModel =
                new ViewModelProvider(this).get(EventListViewModel.class);

        binding = FragmentEventListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // URL to fetch events from
        String getUrl = "http://10.0.2.2:8080/event/getAll";
        RequestQueue requestQueue = Volley.newRequestQueue(root.getContext());

        // Request a JSONArray response from the provided URL
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, getUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject event = response.getJSONObject(i);
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
                }
        );

        // Add the request to the queue
        requestQueue.add(jsonArrayRequest);
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
        TextView eventNameView = createTextView(context, event.getString("name"), 20, 2);
        eventDetailsLayout.addView(eventNameView);
        if (!event.getString("club").equals("null")) {
            TextView eventClubView = createTextView(context, event.getString("club"), 16, 1);
            eventDetailsLayout.addView(eventClubView);
        }
        TextView eventDescriptionView = createTextView(context, event.getString("description"), 16, 3);
        eventDetailsLayout.addView(eventDescriptionView);

        // Add event details layout to the event row
        eventRow.addView(eventDetailsLayout);

        // Create a TextView for the date information
        TextView dateView = createDateView(context, "FEB 29");
        eventRow.addView(dateView);

        // Add drawable as the background - a border on the top and bottom
        eventRow.setBackgroundResource(R.drawable.thin_border);

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

    private TextView createTextView(Context context, String text, int textSize, int maxLines) {
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
        return textView;
    }
}
