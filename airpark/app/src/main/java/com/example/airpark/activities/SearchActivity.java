package com.example.airpark.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.airpark.R;
import com.example.airpark.models.BookingTicket;
import com.example.airpark.utils.InputValidator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Calendar;

public class SearchActivity extends AppCompatActivity {

    private TextInputEditText entryDate, exitDate, entryTime, exitTime;
    private TextInputLayout airportContainer, entryDateContainer, exitDateContainer, entryTimeContainer, exitTimeContainer;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private InputValidator validator;
    private Button search;
    private AutoCompleteTextView autoText;
    private CheckBox disabilityCheck, motorbikeCheck;
    private BookingTicket ticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_info);

        validator = new InputValidator();
        ticket = new BookingTicket();

        //Assigning Variables
        autoText = (AutoCompleteTextView) findViewById(R.id.airport_auto);
        airportContainer = (TextInputLayout) findViewById(R.id.airportContainer);
        entryDate = (TextInputEditText) findViewById(R.id.entryDate);
        entryDateContainer = (TextInputLayout) findViewById(R.id.entryDateContainer);
        exitDate = (TextInputEditText) findViewById(R.id.exitDate);
        exitDateContainer = (TextInputLayout) findViewById(R.id.exitDateContainer);
        entryTime = (TextInputEditText) findViewById(R.id.entryTime);
        entryTimeContainer = (TextInputLayout) findViewById(R.id.entryTimeContainer);
        exitTime = (TextInputEditText) findViewById(R.id.exitTime);
        exitTimeContainer = (TextInputLayout) findViewById(R.id.exitTimeContainer);
        disabilityCheck = (CheckBox) findViewById(R.id.disabilityParking);
        motorbikeCheck = (CheckBox) findViewById(R.id.motorbikeParking);
        search = (Button) findViewById(R.id.searchButton);

        //Get Current Date/Time
        Calendar calender = Calendar.getInstance();
        DecimalFormat dFormat = new DecimalFormat("00");
        int day = calender.get(Calendar.DAY_OF_MONTH);
        int month = calender.get(Calendar.MONTH);
        int year = calender.get(Calendar.YEAR);
        int hour = calender.get(Calendar.HOUR_OF_DAY);


        /*      ******   CHANGE WHEN DATABASE ADDED  ******      */
        String[] airports = {"Dublin Airport", "Shannon Airport", "Cork Airport"};

        //Select Airport
        ArrayAdapter<String> adapter = new ArrayAdapter<>(SearchActivity.this, android.R.layout.simple_dropdown_item_1line, airports);
        autoText.setThreshold(1);
        autoText.setAdapter(adapter);

        //Select Arrival Date
        entryDate.setOnClickListener(v -> {
            hideKeyboard(SearchActivity.this);
            //Calender Pop-up
            datePickerDialog = new DatePickerDialog(SearchActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year1, int month1, int dayOfMonth) {
                    entryDate.setText(dFormat.format(dayOfMonth) + "/" + dFormat.format(month1 + 1) + "/" + year1); //Set Date

                    String entryD = entryDate.getText().toString();
                    String exitD = exitDate.getText().toString();
                    String entryT = entryTime.getText().toString();
                    String exitT = exitTime.getText().toString();

                    try {
                        //If arrival date is not valid with departure date
                        if (!validator.isValidTimeToDate(entryD, exitD, "", "")) {
                            entryDateContainer.setError(getString(R.string.invalid_entry_date));
                            exitDateContainer.setError(null);
                        } else {
                            entryDateContainer.setError(null); //Reset Error Message
                            exitDateContainer.setError(null); //reset error message
                            if (!entryT.equals("")) {
                                if (!validator.isValidTimeToDate(entryD, exitD, entryT, exitT)) {
                                    entryTimeContainer.setError(getString(R.string.invalid_time));
                                } else {
                                    entryTimeContainer.setError(null); //reset error message
                                    exitTimeContainer.setError(null); //reset error message
                                }
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(calender.getTimeInMillis());
            datePickerDialog.show();
        });

        //Select Departure Date
        exitDate.setOnClickListener(v -> {
            hideKeyboard(SearchActivity.this);
            //Calender Pop-up
            datePickerDialog = new DatePickerDialog(SearchActivity.this, (view, year12, month12, dayOfMonth) -> {
                exitDate.setText(dFormat.format(dayOfMonth) + "/" + dFormat.format(month12 + 1) + "/" + year12);

                String entryD = entryDate.getText().toString();
                String exitD = exitDate.getText().toString();
                String entryT = entryTime.getText().toString();
                String exitT = exitTime.getText().toString();

                try {
                    //If departure date is not valid with arrival date
                    if (!validator.isValidTimeToDate(entryD, exitD, "", "")) {
                        exitDateContainer.setError(getString(R.string.invalid_exit_date));
                        entryDateContainer.setError(null);
                    } else {
                        exitDateContainer.setError(null); //reset error message
                        entryDateContainer.setError(null); //reset error message
                        if (!exitT.equals("")) {
                            if (!validator.isValidTimeToDate(entryD, exitD, entryT, exitT)) {
                                exitTimeContainer.setError(getString(R.string.invalid_time));
                            } else {
                                exitTimeContainer.setError(null); //reset error message
                                entryTimeContainer.setError(null); //reset error message
                            }
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(calender.getTimeInMillis());
            datePickerDialog.show();
        });


        //Set Arrival Time
        entryTime.setOnClickListener((View v) -> {
            hideKeyboard(SearchActivity.this);

            timePickerDialog = new TimePickerDialog(SearchActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute12) {
                    entryTime.setText(dFormat.format(hourOfDay) + ":00");

                    String entryD = entryDate.getText().toString();
                    String exitD = exitDate.getText().toString();
                    String entryT = entryTime.getText().toString();
                    String exitT = exitTime.getText().toString();

                    try {
                        if (!validator.isValidTimeToDate(entryD, exitD, entryT, exitT)) {
                            //if both time are valid individually = arrival time > departure time
                            if (validator.isValidTimeToDate(entryD, exitD, "", exitT) && validator.isValidTimeToDate(entryD, exitD, entryT, "")) {
                                exitTimeContainer.setError(null);
                                entryTimeContainer.setError(SearchActivity.this.getString(R.string.invalid_time));
                                //if only arrival time is valid
                            } else if (validator.isValidTimeToDate(entryD, exitD, entryT, "")) {
                                entryTimeContainer.setError(null);
                            } else {
                                entryTimeContainer.setError(getString(R.string.invalid_time));
                            }
                        } else {
                            entryTimeContainer.setError(null);
                            exitTimeContainer.setError(null);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            }, hour, 0, true);
            timePickerDialog.show();
        });


        //Set Departure Time
        exitTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(SearchActivity.this);
                timePickerDialog = new TimePickerDialog(SearchActivity.this, (view, hourOfDay, minute1) -> {
                    exitTime.setText(dFormat.format(hourOfDay) + ":00");

                    String entryD = entryDate.getText().toString();
                    String exitD = exitDate.getText().toString();
                    String entryT = entryTime.getText().toString();
                    String exitT = exitTime.getText().toString();

                    try {
                        if (!validator.isValidTimeToDate(entryD, exitD, entryT, exitT)) {
                            //if both time is valid individually = departure < arrival
                            if (validator.isValidTimeToDate(entryD, exitD, "", exitT) && validator.isValidTimeToDate(entryD, exitD, entryT, "")) {
                                entryTimeContainer.setError(null);
                                exitTimeContainer.setError(SearchActivity.this.getString(R.string.invalid_time));
                                //if only departure time is valid
                            } else if (validator.isValidTimeToDate(entryD, exitD, "", exitT)) {
                                exitTimeContainer.setError(null);
                            } else {
                                exitTimeContainer.setError(SearchActivity.this.getString(R.string.invalid_time));
                            }
                        } else {
                            entryTimeContainer.setError(null);
                            exitTimeContainer.setError(null);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }, hour, 0, true);
                timePickerDialog.show();
            }
        });

        //Select Disability Parking
        disabilityCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(disabilityCheck.isChecked()){
                    motorbikeCheck.setChecked(false);
                    ticket.setMotorbike(false);
                    ticket.setDisability(true);
                }
            }
        });

        //Select Motorbike Parking
        motorbikeCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(motorbikeCheck.isChecked()){
                    disabilityCheck.setChecked(false);
                    ticket.setDisability(false);
                    ticket.setMotorbike(true);
                }
            }
        });

        //Select Search Button
        search.setOnClickListener(v -> {
            String entryD = entryDate.getText().toString();
            String exitD = exitDate.getText().toString();
            String entryT = entryTime.getText().toString();
            String exitT = exitTime.getText().toString();
            Boolean validAirport = false;


            for (int i = 0; i < airports.length; i++) {
                //Check Airport is in list
                if (autoText.getText().toString().equals(airports[i])) {
                    validAirport = true;
                    airportContainer.setError(null);

                    //Check all date and time entries are complete
                    if (!entryD.equals("") && !exitD.equals("") && !entryT.equals("") && !exitT.equals("")) {
                        try {
                            if (validator.isValidTimeToDate(entryD, exitD, entryT, exitT)) {
                                System.out.println("Search OK");
                                //Update booking ticket
                                ticket.setAirport(autoText.getText().toString());
                                ticket.setArrivalDate(entryD);
                                ticket.setExitDate(exitD);
                                ticket.setArrivalTime(entryT);
                                ticket.setExitTime(exitT);

                                //Move to Next Screen
                                Intent myIntent = new Intent(SearchActivity.this, SelectCarparkActivity.class);
                                startActivity(myIntent);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else {
                        //Set error if incomplete date or time
                        if (entryD.equals("")) { entryDateContainer.setError(getString(R.string.invalid_date)); }
                        if (exitD.equals("")) { exitDateContainer.setError(getString(R.string.invalid_date)); }
                        if (entryT.equals("")) { entryTimeContainer.setError(getString(R.string.invalid_time)); }
                        if (exitT.equals("")) { exitTimeContainer.setError(getString(R.string.invalid_time)); }
                    }
                }
            }
            if (!validAirport) {
                airportContainer.setError(getString(R.string.invalid_airport));
                Toast.makeText(SearchActivity.this, getText(R.string.invalid_airport), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        airportContainer.clearFocus();
    }
}

