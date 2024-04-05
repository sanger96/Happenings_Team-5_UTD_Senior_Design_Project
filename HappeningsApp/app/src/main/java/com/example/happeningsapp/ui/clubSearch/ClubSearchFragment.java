package com.example.happeningsapp.ui.clubSearch;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.happeningsapp.databinding.FragmentClubSearchBinding;

import org.json.JSONArray;
import org.json.JSONException;

public class ClubSearchFragment extends Fragment {

    private FragmentClubSearchBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ClubSearchModel clubSearchModel =
                new ViewModelProvider(this).get(ClubSearchModel.class);

        binding = FragmentClubSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        final TextView textView = binding.textGallery;
        clubSearchModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        String getUrl = "http://10.0.2.2:8080/club/getAll";
        RequestQueue requestQueue = Volley.newRequestQueue(root.getContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, getUrl, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            //bind table to page
                            TableLayout table = (TableLayout) binding.clubSearchTable;

                            //create first row manually
                            TableRow row0 = new TableRow(table.getContext());                             TextView col0 = new TextView(table.getContext());
                            //row 0 column 0
                            col0.setText("| CLUB NAME |");
//                            col0.setGravity(Gravity.CENTER);
                            row0.addView(col0);

                            for(int i=0; i<response.length();i++){
//                                eventName.setText(response.getJSONObject(i).getString("name"));
//                                eventClub.setText(response.getJSONObject(i).getString("club"));
//                                eventDescription.setText(response.getJSONObject(i).getString("description"));
                                //code to dynamically grab and fill into table the events
                                TableRow row = new TableRow(table.getContext());
//                                TableRow.LayoutParams lp= new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT);
//                                row.setLayoutParams(lp);
                                //get event name
                                TextView clubName = new TextView(table.getContext());

                                clubName.setText(response.getJSONObject(i).getString("name"));
//                                clubName.setGravity(Gravity.CENTER);
                                row.addView(clubName);

                                table.addView(row);
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
//                        eventName.setText("Well");
//                        eventClub.setText("that");
//                        eventDescription.setText("Failed");
                        Log.wtf("Volley Fail",error.toString());

                    }
                });
        requestQueue.add(jsonArrayRequest);
        return root;
    }

//     For when you are ready for adding input text on clubSearchFragment
//https://developer.android.com/develop/ui/views/touch-and-input/creating-input-method
//    @Override
//    public View onCreateInputView() {
//        MyKeyboardView inputView =
//                (MyKeyboardView) getLayoutInflater().inflate(R.layout.input, null);
//
//        inputView.setOnKeyboardActionListener(this);
//        inputView.setKeyboard(latinKeyboard);
//
//        return inputView;
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}