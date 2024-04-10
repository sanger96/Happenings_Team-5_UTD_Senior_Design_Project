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
import android.widget.Toast;

import com.example.happeningsapp.R;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

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

import java.util.ArrayList;
import java.util.List;


public class ClubSearchFragment extends Fragment {

    private FragmentClubSearchBinding binding;
    private ArrayList<JSONObject> clubs;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ClubSearchModel clubSearchModel = new ViewModelProvider(this).get(ClubSearchModel.class);

        binding = FragmentClubSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SearchView searchView = binding.SearchView;
//        searchView.clearFocus();

        clubs = new ArrayList<>();
        binding.clubSearchTable.removeAllViews();
//        Toast.makeText(root.getContext(),
//                "table len: " + binding.clubSearchTable.getChildCount() +
//                "\nlist len: " + clubs.size(), Toast.LENGTH_LONG).show();

        // Create listener for search bar
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    // Clear the current rows
                    binding.clubSearchTable.removeAllViews();

                    // If nothing was searched then show all clubs
                    if(newText == null || newText.isEmpty()){

                        for(JSONObject club : clubs){
                            View clubRow = createClubRow(root.getContext(), club);
                            binding.clubSearchTable.addView(clubRow);
                        }
                        return true;
                    }

                    int count = 0;
                    for(JSONObject club : clubs){
                        String name = club.getString("name");
                        if(name.toLowerCase().contains(newText.toLowerCase())){
                            View clubRow = createClubRow(root.getContext(), club);
                            binding.clubSearchTable.addView(clubRow);
                            count++;
                        }
                    }

                    if(count == 0){
                        Toast.makeText(root.getContext(), "There are no clubs with that name", Toast.LENGTH_SHORT).show();
                    }

                    return true;
                }catch(JSONException e){
                    throw new RuntimeException(e);
                }
            }
        });
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
                                clubs.add(club);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private View createClubRow(Context context, JSONObject club) throws JSONException {
        // Create a horizontal LinearLayout for each club
        LinearLayout clubRow = new LinearLayout(context);

        LinearLayout.LayoutParams rowLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, // will take the width of the parent (the device)
                LinearLayout.LayoutParams.WRAP_CONTENT  // height will be based on the content
        );
        clubRow.setLayoutParams(rowLayoutParams);
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

        int clubID = club.getInt("clubID");
        clubRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putInt("clubID", clubID);
                Navigation.findNavController(view).navigate(R.id.action_nav_clubSearch_to_nav_individualClub, args);
            }
        });

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

        textView.setPadding(0,0,0,0);

        return textView;
    }
}