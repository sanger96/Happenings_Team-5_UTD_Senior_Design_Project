package com.example.happeningsapp.ui.eventList;

//import static androidx.databinding.DataBindingUtil.setContentView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
//import android.webkit.WebView;
//import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

//import com.example.happeningsapp.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.happeningsapp.databinding.FragmentEventListBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EventListFragment extends Fragment {

    private FragmentEventListBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EventListViewModel EventListViewModel =
                new ViewModelProvider(this).get(com.example.happeningsapp.ui.eventList.EventListViewModel.class);

        binding = FragmentEventListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //decalre variables for eventName, eventClub, eventDescription
        TextView eventName = binding.eventName;
        TextView eventClub = binding.eventClub;
        TextView eventDescription = binding.eventDescription;

        //URL to get from
        String getUrl="http://10.0.2.2:8080/event/getAll";
        RequestQueue requestQueue = Volley.newRequestQueue(root.getContext());

        // since the response we get from the api is in JSONArray,
        // we need to use `JsonArrayRequest` for
        // parsing the request response
        // Request a JSONArray response from the provided URL.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest

                (Request.Method.GET, getUrl, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            //below is where I will get the name, club, and description of events in that order and directly put them on the page.
                            for(int i=0; i<response.length();i++){
                                eventName.setText(response.getJSONObject(i).getString("name"));
                                eventClub.setText(response.getJSONObject(i).getString("club"));
                                eventDescription.setText(response.getJSONObject(i).getString("description"));
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

//                        try {
//                            topElement.setText(response.getString("name"));
//                        } catch (JSONException e) {
//                            Log.wtf("try on line 57","failed to get name");
//                            throw new RuntimeException(e);
//                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        eventName.setText("Well");
                        eventClub.setText("that");
                        eventDescription.setText("Failed");
                        Log.wtf("Volley Fail",error.toString());

                    }
                });
        //add to queue
        requestQueue.add(jsonArrayRequest);
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}