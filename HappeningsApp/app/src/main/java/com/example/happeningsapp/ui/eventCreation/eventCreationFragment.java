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

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

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



        //bind variables
        TextView name = binding.inTextEventName;
        TextView description = binding.inTextEventDescription;
        TextView startTime= binding.inTextStartTime; //may want to cast this to java date type
//        LocalDateTime startTime= (LocalDateTime) binding.inTextStartTime; //may want to cast this to java date type
        TextView endTime= binding.inTextEndTime; //may want to cast this to java date type
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
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        root.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                dateField.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });

        pickStartTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting the
                // instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting our hour, minute.
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                // on below line we are initializing our Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(root.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                // on below line we are setting selected time
                                // in our text view.
                                startTime.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                // at last we are calling show to
                // display our time picker dialog.
                timePickerDialog.show();
            }
        });

        pickEndTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting the
                // instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting our hour, minute.
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                // on below line we are initializing our Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(root.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                // on below line we are setting selected time
                                // in our text view.
                                endTime.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                // at last we are calling show to
                // display our time picker dialog.
                timePickerDialog.show();
            }
        });


        button_createEvent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //url we are posting to, uses 10.0.2.2 instead of local host, this is what android studio will need to use local host.
                // if you type local host it will automatically map to 127.0.0.1 aka the wrong place.
                String postUrl="http://10.0.2.2:8080/event/createFromForm";
                RequestQueue requestQueue = Volley.newRequestQueue(root.getContext());

                //initializing the JSONObject that will be posted
                JSONObject postEvent = new JSONObject();

                try{
                    //This is how we will add elements to build the JSONObject post data
                    postEvent.put("eventName",name.getText().toString());
                    postEvent.put("description", description.getText().toString());

                    //create appointment to be put in event
                    postEvent.put("startTime", startTime.getText().toString());
                    postEvent.put("endTime", endTime.getText());
//                    postAppointment.put("type", "event");

                    //add location in appointment
                    postEvent.put("locationName", location.getText().toString());
                    postEvent.put("room", room.getText().toString());

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


} // end of eventCreationFragment