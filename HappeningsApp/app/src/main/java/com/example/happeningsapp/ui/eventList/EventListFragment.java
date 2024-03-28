package com.example.happeningsapp.ui.eventList;

//import static androidx.databinding.DataBindingUtil.setContentView;

import android.os.Bundle;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.happeningsapp.databinding.FragmentEventListBinding;

import org.json.JSONObject;
import org.w3c.dom.Text;

public class EventListFragment extends Fragment {

    private FragmentEventListBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EventListViewModel EventListViewModel =
                new ViewModelProvider(this).get(com.example.happeningsapp.ui.eventList.EventListViewModel.class);

        binding = FragmentEventListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        TextView topElement = binding.topElement;

        //URL to get from
        String getUrl="http://localhost:10.0.2.2/event/getAll";
        RequestQueue requestQueue = Volley.newRequestQueue(root.getContext());
        // since the response we get from the api is in JSON,
        // we need to use `JsonObjectRequest` for
        // parsing the request response
        // Request a string response from the provided URL.

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, getUrl, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        topElement.setText(response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        topElement.setText("Failed");

                    }
                });
        //add to queue
        requestQueue.add(jsonObjectRequest);
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}