package com.example.happeningsapp.ui.eventCreation;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast; //lets us have pop ups for user

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.happeningsapp.R;
import com.example.happeningsapp.databinding.FragmentEventCreationBinding;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class eventCreationFragment extends Fragment {

    //set binding
    private FragmentEventCreationBinding binding;
    private Button pickDateBtn;
    private TextView dateField;
    private Button pickStartTimeBtn;
    private Button pickEndTimeBtn;
    public static eventCreationFragment newInstance() {
        return new eventCreationFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        EventCreationViewModel EventCreationViewModelProvider=
                new ViewModelProvider(this).get(EventCreationViewModel.class);
        binding = FragmentEventCreationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Get the activity's ActionBar and set the title
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Event Creation");
        }

        //bind variables
        TextView name = binding.inTextEventName;
        TextView description = binding.inTextEventDescription;
        TextView startTime= binding.inTextStartTime;
        TextView endTime= binding.inTextEndTime;
        TextView location = binding.inTextEventLocation;
        TextView room = binding.inTextEventRoom;

        // these commands will make sure the variables are observed for their "life cycle"
        EventCreationViewModelProvider.getName().observe(getViewLifecycleOwner(), name::setText);
        EventCreationViewModelProvider.getDescription().observe(getViewLifecycleOwner(), description::setText);
        EventCreationViewModelProvider.getStartTime().observe(getViewLifecycleOwner(), startTime::setText);
        EventCreationViewModelProvider.getEndTime().observe(getViewLifecycleOwner(), endTime::setText);
        EventCreationViewModelProvider.getLocation().observe(getViewLifecycleOwner(), location::setText);
        EventCreationViewModelProvider.getRoom().observe(getViewLifecycleOwner(), room::setText);

        //binding create event button
        Button button_createEvent = (Button) root.findViewById(R.id.button_createEvent);


        pickDateBtn = root.findViewById(R.id.idBtnPickDate);
        dateField = root.findViewById(R.id.inText_Date);
        pickStartTimeBtn = root.findViewById(R.id.idBtnPickStartTime);
        pickEndTimeBtn = root.findViewById(R.id.idBtnPickEndTime);
        pickDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        root.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Creating a Calendar object to hold the selected date.
                                Calendar selectedDate = Calendar.getInstance();
                                selectedDate.set(Calendar.YEAR, year);
                                selectedDate.set(Calendar.MONTH, monthOfYear);
                                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());
                                String formattedDate = dateFormat.format(selectedDate.getTime());

                                // Setting the formatted date in TextView.
                                dateField.setText(formattedDate);

                            }
                        },

                        year, month, day);
                datePickerDialog.show();
            }
        });

        pickStartTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                // on below line we are initializing our Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(root.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                Calendar selectedTime = Calendar.getInstance();
                                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                selectedTime.set(Calendar.MINUTE, minute);

                                // Formatting the selected time to 12-hour format with AM/PM indicator.
                                SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
                                String formattedTime = timeFormat.format(selectedTime.getTime());

                                // Setting the formatted time in TextView.
                                startTime.setText(formattedTime);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        pickEndTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(root.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                Calendar selectedTime = Calendar.getInstance();
                                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                selectedTime.set(Calendar.MINUTE, minute);

                                // Formatting the selected time to 12-hour format with AM/PM indicator.
                                SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
                                String formattedTime = timeFormat.format(selectedTime.getTime());

                                // Setting the formatted time in TextView.
                                endTime.setText(formattedTime);
                            }
                        }, hour, minute, false);

                timePickerDialog.show();
            }
        });


        button_createEvent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                boolean missingRequiredFields = false;
                if (name.getText().toString().isEmpty()) {
                    name.setError("Event name is required.");
                    missingRequiredFields = true;
                }

                if (description.getText().toString().isEmpty()) {
                    description.setError("Event description is required.");
                    missingRequiredFields = true;
                }

                if (dateField.getText().toString().isEmpty()) {
                    dateField.setError("Date is required.");
                    missingRequiredFields = true;
                }

                if (startTime.getText().toString().isEmpty()) {
                    startTime.setError("Start time is required.");
                    missingRequiredFields = true;
                }

                if (endTime.getText().toString().isEmpty()) {
                    endTime.setError("End time is required.");
                    missingRequiredFields = true;
                }

                if (location.getText().toString().isEmpty()) {
                    location.setError("Location/Building is required.");
                    missingRequiredFields = true;
                }

                if (missingRequiredFields) { return; }
                //url we are posting to, uses 10.0.2.2 instead of local host, this is what android studio will need to use local host.
                // if you type local host it will automatically map to 127.0.0.1 aka the wrong place.
                com.example.happeningsapp.GlobalVars server =  com.example.happeningsapp.GlobalVars.getInstance();
                String postUrl= server.getServerUrl() + "/event/CreateFromForm";
                RequestQueue requestQueue = Volley.newRequestQueue(root.getContext());

                //initializing the JSONObject that will be posted
                JSONObject postEvent = new JSONObject();

                try{

                    postEvent.put("eventName",name.getText().toString());
                    postEvent.put("description", description.getText().toString());

                    postEvent.put("startTime", formatDateAndTime(dateField.getText().toString(), startTime.getText().toString()));
                    postEvent.put("endTime", formatDateAndTime(dateField.getText().toString(), endTime.getText().toString()));

                    postEvent.put("locationName", location.getText().toString());
                    if (!room.getText().toString().isEmpty()) {
                        postEvent.put("room", room.getText().toString());
                    }
                    //this log method will appear in logcat
                    Log.i("eventCreationFragment post data","JSONObject postData is built");
                } catch (JSONException e){
                    Log.i("eventCreationFragment catch post data","JSONObject postData FAILED to build");
                    e.printStackTrace();
                }



                JsonObjectRequest jsonObjectRequest_eventCreation = new JsonObjectRequest(Request.Method.POST,postUrl,postEvent, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //add toast for success
                        Toast.makeText(root.getContext(), "Event Creation Successful",Toast.LENGTH_SHORT).show();
                        Log.i("Volley",response.toString());
                        try {
                            int createdEventId = response.getInt("eventID");
                            Bundle args = new Bundle();
                            args.putInt("eventID", createdEventId);
                            Navigation.findNavController(view).navigate(R.id.action_nav_eventCreation_to_nav_individualEvent, args);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        May want to navigate to event page after its created???
//                        Navigation.findNavController(view).navigate(R.id.action_nav_userProfileSetting_to_nav_eventList);
                    }// end of onResponse
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        error.printStackTrace();
                    }
                });//end of jsonObjectRequest and error listener
                requestQueue.add(jsonObjectRequest_eventCreation);

            } //end of onClick
        });//end of setOnClickListener


        return root;
    }// end of onCreateView

    public static String formatDateAndTime(String dateStr, String timeStr) {
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
            SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.ENGLISH);
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

            Date date = dateFormat.parse(dateStr);
            Date time = timeFormat.parse(timeStr);

            String formattedDate = outputFormat.format(date);
            String formattedTime = outputFormat.format(time);

            return formattedDate.substring(0, 10) + 'T' + formattedTime.substring(11);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


} // end of eventCreationFragment