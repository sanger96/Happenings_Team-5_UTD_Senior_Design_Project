package com.example.happeningsapp.ui.clubSearch;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.LinearLayout;

import android.content.Context;
import com.example.happeningsapp.R;

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
import org.json.JSONObject;
import org.json.JSONException;



public class ClubSearchFragment extends Fragment {

    private FragmentClubSearchBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ClubSearchModel clubSearchModel = new ViewModelProvider(this).get(ClubSearchModel.class);

        binding = FragmentClubSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


//        final TextView textView = binding.textGallery;
//        clubSearchModel.getText().observe(getViewLifecycleOwner(), textView::setText);
//
//        final EditText clubSearchbar = binding.searchBar;

        String getUrl = "http://10.0.2.2:8080/club/getAll";
        RequestQueue requestQueue = Volley.newRequestQueue(root.getContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, getUrl, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject club = response.getJSONObject(i);
                                View clubRow = createClubRow(root.getContext(), club);
                                binding.clubSearchTable.addView(clubRow);
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
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

    private View createClubRow(Context context, JSONObject club) throws JSONException {
        // Create a horizontal LinearLayout for each club
        LinearLayout clubRow = new LinearLayout(context);
        clubRow.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, // will take the width of the parent (the device)
                LinearLayout.LayoutParams.WRAP_CONTENT  // height will be based on the content
        ));
        clubRow.setOrientation(LinearLayout.HORIZONTAL);

        // Padding BETWEEN the text and the outline
        clubRow.setPadding(16, 16, 16, 16);

        // Create a vertical LinearLayout to hold the text details
        LinearLayout clubDetailsLayout = new LinearLayout(context);
        LinearLayout.LayoutParams detailsLayoutParams = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        );
        detailsLayoutParams.setMargins(0, 5, 22, 5); // Add right margin for spacing BETWEEN end of text and date box
        clubDetailsLayout.setLayoutParams(detailsLayoutParams);
        clubDetailsLayout.setOrientation(LinearLayout.VERTICAL); // we want the club name, desc, to be stacked vertically

        // Create TextViews for club name, and desc
        TextView clubNameView = createTextView(context, club.getString("name"), 20, 2);
        clubDetailsLayout.addView(clubNameView);
        TextView clubDescriptionView = createTextView(context, club.getString("description"), 16, 3);
        clubDetailsLayout.addView(clubDescriptionView);

        // Add club details layout to the club row
        clubRow.addView(clubDetailsLayout);

        // Add drawable as the background - a border on the top and bottom
        clubRow.setBackgroundResource(R.drawable.thin_border);

        return clubRow;
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