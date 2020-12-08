package com.example.airpark.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.airpark.R;
import com.example.airpark.activities.Bookings.MyBookingsActivity;
import com.example.airpark.activities.Prelogin.LoginActivity;
import com.example.airpark.designPatterns.factory.CarParkSpaceFactory;
import com.example.airpark.models.Airport;
import com.example.airpark.models.BookingTicket;
import com.example.airpark.models.UserModel;
import com.example.airpark.utils.HelperInterfaces.NetworkingClosure;
import com.example.airpark.utils.InputValidator;
import com.example.airpark.utils.Networking.NetworkHandler;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Airpark Application - Group 14
 *
 * CS4125 -> System Analysis & Design
 * CS5721 -> Software Design
 *
 * Creates Search/Landing Screen & sets user input booking info
 */
public class LandingSearchActivity extends AppCompatActivity {

    private DecimalFormat dFormat;
    private Calendar calender;
    private InputValidator validator;
    private TextInputEditText entryDate, exitDate, entryTime, exitTime;
    private TextInputLayout  airportContainer, entryDateContainer, exitDateContainer, entryTimeContainer, exitTimeContainer;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button search;
    private AutoCompleteTextView airportAutoText;
    private CheckBox disabilityCheck, motorbikeCheck;
    private ActionBarDrawerToggle drawerToggle;
    private Airport selectedAirport;
    private ProgressBar progressBar;

    public LandingSearchActivity(){
        dFormat = new DecimalFormat("00");
        calender = Calendar.getInstance();
    }

    /**
     * Create Search/Landing Screen & set click actions
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_info);

        BookingTicket ticket = new BookingTicket();
        validator = new InputValidator();
        bindUiItems();

        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        progressBar.setVisibility(View.INVISIBLE);

        //Get Current Date/Time
        int day = calender.get(Calendar.DAY_OF_MONTH);
        int month = calender.get(Calendar.MONTH);
        int year = calender.get(Calendar.YEAR);
        int hour = calender.get(Calendar.HOUR_OF_DAY);

        List<Airport> airports = new ArrayList<>();

        //Select Airport
        NetworkHandler.getInstance().airportsListing(new NetworkingClosure() {
            @Override
            public void completion(JSONObject object, String message) {
                if (object == null){
                    Toast.makeText(airportAutoText.getContext(), getString(R.string.unable_fetch_airports), Toast.LENGTH_SHORT).show();
                    return;
                }

                try{
                    JSONArray arr = object.getJSONArray("airports");
                    for (int i = 0; i < arr.length(); i++){
                        airports.add(new Airport(arr.getJSONObject(i)));
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                ArrayAdapter<Airport> adapter = new ArrayAdapter<>(airportAutoText.getContext(), android.R.layout.simple_dropdown_item_1line, airports);
                airportAutoText.setAdapter(adapter);
            }
        });

        airportAutoText.setThreshold(1);

        airportAutoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedAirport = (Airport) parent.getItemAtPosition(position);
                airportAutoText.setError(null);
                airportContainer.setError(null);
            }
        });

        entryDate.setOnClickListener(v -> {setEntryDate(year, month, day); hideKeyboard(this);});
        exitDate.setOnClickListener(v -> {setExitDate(year,month,day); hideKeyboard(this);});
        entryTime.setOnClickListener(v -> {setEntryTime(hour); hideKeyboard(this);});
        exitTime.setOnClickListener(v -> {setExitTime(hour); hideKeyboard(this);});
        //Set Disabled Parking
        disabilityCheck.setOnClickListener(v -> {
            if(disabilityCheck.isChecked()){ motorbikeCheck.setChecked(false); }
        });
        //Set Motorbike Parking
        motorbikeCheck.setOnClickListener(v -> {
            if(motorbikeCheck.isChecked()){ disabilityCheck.setChecked(false); }
        });

        //Select Search Button & Validate Info
        search.setOnClickListener(v -> {
            if(isValidSearch(airports)) {

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                String entryD = entryDate.getText().toString();
                String exitD = exitDate.getText().toString();
                String entryT = entryTime.getText().toString();
                String exitT = exitTime.getText().toString();

                String finalEntryString = entryD + " " + entryT;
                String finalExitString = exitD + " " + exitT;

                try {
                    Date finalEntryDate = sdf.parse(finalEntryString);
                    Date finalExitDate = sdf.parse(finalExitString);

                    ticket.setAirport(selectedAirport);
                    ticket.setEntryDate(finalEntryDate);
                    ticket.setExitDate(finalExitDate);

                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(v.getContext(), getString(R.string.something_wrong), Toast.LENGTH_LONG).show();
                    return;
                }
                //Update booking ticket
                CarParkSpaceFactory carpParkSpaceFactory = new CarParkSpaceFactory();

                if(disabilityCheck.isChecked()){
                    ticket.setSpaceRequired(carpParkSpaceFactory.getSpace(CarParkSpaceFactory.SpaceType.DISABLED));
                }else if (motorbikeCheck.isChecked()){
                    ticket.setSpaceRequired(carpParkSpaceFactory.getSpace(CarParkSpaceFactory.SpaceType.TWO_WHEELER));
                }else{
                    ticket.setSpaceRequired(carpParkSpaceFactory.getSpace(CarParkSpaceFactory.SpaceType.GENERAL));
                }

                System.out.println(ticket.getSpaceRequired());
                HashMap<String, String> params = ticket.getCarparkListingParameters();
                progressBar.setVisibility(View.VISIBLE);
                NetworkHandler.getInstance().getAvailableCarParks(params, new NetworkingClosure() {
                    @Override
                    public void completion(JSONObject object, String message) {
                        progressBar.setVisibility(View.INVISIBLE);
                        if (object == null){
                            Toast.makeText(progressBar.getContext(), message, Toast.LENGTH_LONG);
                        }else{
                            //Move to Next Screen
                            Intent myIntent = new Intent(progressBar.getContext(), SelectCarparkActivity.class);
                            myIntent.putExtra("ticket", ticket);
                            myIntent.putExtra("listing", object.toString());
                            startActivity(myIntent);
                        }
                    }
                });
            }
        });

        setupDrawer();
    }

    //Prevent back to login screen on android default back button at bottom of screen
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    void setupDrawer(){
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigationView);
        View headerView = navigationView.getHeaderView(0);
        TextView nameTextView = headerView.findViewById(R.id.nameTextView);
        TextView emailTextView = headerView.findViewById(R.id.emailTextView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Menu menu = navigationView.getMenu();

        //don't show menu button if not logged in
        if (UserModel.currentUser == null) {
            nameTextView.setVisibility(View.GONE);
            emailTextView.setVisibility(View.GONE);
            menu.findItem(R.id.home).setVisible(false);
            menu.findItem(R.id.logout).setVisible(false);
            menu.findItem(R.id.bookings).setVisible(false);
        }else{
            nameTextView.setText(UserModel.currentUser.getName());
            emailTextView.setText(UserModel.currentUser.getEmail());
            menu.findItem(R.id.login).setVisible(false);
        }

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.home){
                    drawerLayout.close();
                }else if(item.getItemId() == R.id.bookings) {
                    Intent myIntent = new Intent(LandingSearchActivity.this, MyBookingsActivity.class);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(myIntent);
                }else if(item.getItemId() == R.id.login){
                    Intent myIntent = new Intent(LandingSearchActivity.this, LoginActivity.class);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(myIntent);
                }else{
                    UserModel.currentUser = null;
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(navigationView.getContext()).edit();
                    editor.clear();
                    editor.apply();
                    Intent myIntent = new Intent(LandingSearchActivity.this, LoginActivity.class);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(myIntent);
                }
                return false;
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        if (drawerToggle != null) {
            drawerToggle.syncState();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Bind Ui with id
     */
    private void bindUiItems(){
        progressBar = (ProgressBar)findViewById(R.id.progress);
        airportAutoText = (AutoCompleteTextView) findViewById(R.id.airport_auto);
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
    }

    /**
     * Calender Pop-up appears and sets user selected date
     * Error is set if incorrect date chosen (Entry date is after exit date)
     *
     * @param year current year
     * @param month current month
     * @param day current day
     */
    private void setEntryDate(int year, int month, int day){
        //Calender Pop-up
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
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
    }

    /**
     * Calender Pop-up appears and sets user selected date
     * Error is set if incorrect date chosen (Exit date is before entry date)
     *
     * @param year current year
     * @param month current month
     * @param day current day
     */
    private void setExitDate(int year, int month, int day) {
        //Calender Pop-up
        datePickerDialog = new DatePickerDialog(this, (view, year12, month12, dayOfMonth) -> {
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
    }

    /**
     * Clock pop-up appears and sets user selected time(per hour)
     * Error is set if incorrect
     *
     * @param hour current hour
     */
    private void setEntryTime(int hour){
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute12) {
                entryTime.setText(dFormat.format(hourOfDay) + ":00");
                if(hourOfDay == 00){
                    entryTime.setText("23:59");
                }

                String entryD = entryDate.getText().toString();
                String exitD = exitDate.getText().toString();
                String entryT = entryTime.getText().toString();
                String exitT = exitTime.getText().toString();

                try {
                    if (!validator.isValidTimeToDate(entryD, exitD, entryT, exitT)) {
                        //if both time are valid individually = arrival time > departure time
                        if (validator.isValidTimeToDate(entryD, exitD, "", exitT) && validator.isValidTimeToDate(entryD, exitD, entryT, "")) {
                            exitTimeContainer.setError(null);
                            entryTimeContainer.setError(getString(R.string.invalid_time));
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

    }

    /**
     * Clock pop-up appears and sets user selected time(per hour)
     * Error is set if incorrect
     *
     * @param hour current hour
     */
    private void setExitTime(int hour){
        timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute1) -> {
            exitTime.setText(dFormat.format(hourOfDay) + ":00");
            if(hourOfDay == 00){
                exitTime.setText("23:59");
            }

            String entryD = entryDate.getText().toString();
            String exitD = exitDate.getText().toString();
            String entryT = entryTime.getText().toString();
            String exitT = exitTime.getText().toString();

            try {
                if (!validator.isValidTimeToDate(entryD, exitD, entryT, exitT)) {
                    //if both time is valid individually = departure < arrival
                    if (validator.isValidTimeToDate(entryD, exitD, "", exitT) && validator.isValidTimeToDate(entryD, exitD, entryT, "")) {
                        entryTimeContainer.setError(null);
                        exitTimeContainer.setError(getString(R.string.invalid_time));
                        //if only departure time is valid
                    } else if (validator.isValidTimeToDate(entryD, exitD, "", exitT)) {
                        exitTimeContainer.setError(null);
                    } else {
                        exitTimeContainer.setError(getString(R.string.invalid_time));
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

    /**
     * Checks all required user inputs have been filled and are valid
     * Error set if any are missing/incorrect
     *
     * @param airports Array of airports
     * @return true if all info is valid, else false
     */
    private Boolean isValidSearch(List<Airport> airports) {
        String entryD = entryDate.getText().toString();
        String exitD = exitDate.getText().toString();
        String entryT = entryTime.getText().toString();
        String exitT = exitTime.getText().toString();

        for (Airport airport : airports) {
            if (airportAutoText.getText().toString().equals(airport.getAirportName())) {
                selectedAirport = airport;
                airportContainer.setError(null);

                if (!entryD.equals("") && !exitD.equals("") && !entryT.equals("") && !exitT.equals("")) {
                   try {
                       if (validator.isValidTimeToDate(entryD, exitD, entryT, exitT)) {
                           return true;
                       }
                   } catch (ParseException e) {
                       e.printStackTrace();
                   }
                } else {
                    if (entryD.equals("")) { entryDateContainer.setError(getString(R.string.invalid_date)); }
                    if (exitD.equals("")) { exitDateContainer.setError(getString(R.string.invalid_date)); }
                    if (entryT.equals("")) { entryTimeContainer.setError(getString(R.string.invalid_time)); }
                    if (exitT.equals("")) { exitTimeContainer.setError(getString(R.string.invalid_time)); }
                }
            }
        }

        if (selectedAirport == null) {
            airportContainer.setError(getString(R.string.invalid_airport));
            Toast.makeText(this, getText(R.string.invalid_airport), Toast.LENGTH_LONG).show();
        }

        return false;
    }

    /**
     * Hides soft keyboard
     * @param activity
     */
    private void hideKeyboard(Activity activity){
        InputMethodManager imm = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if(view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        airportContainer.clearFocus();
    }

}

