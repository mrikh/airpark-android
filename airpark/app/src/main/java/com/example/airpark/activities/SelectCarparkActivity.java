package com.example.airpark.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.airpark.R;
import com.example.airpark.adapters.CarparkSectionAdapter;
import com.example.airpark.models.BookingTicket;
import com.example.airpark.models.CalculatePrice;
import com.example.airpark.models.CarPark;
import com.example.airpark.models.CarParkSpace;
import com.example.airpark.designPatterns.factory.CarParkSpaceFactory;
import com.example.airpark.models.CarparkListSection;
import com.google.gson.JsonObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Airpark Application - Group 14
 *
 * CS4125 -> System Analysis & Design
 * CS5721 -> Software Design
 *
 * Car Park Options Screen (available car parks to choose from)
 */
public class SelectCarparkActivity extends AppCompatActivity {
    private TextView airportView, entryDate, exitDate, entryTime, exitTime;
    private RecyclerView recyclerView;
    private ArrayList<CarPark> carparkList, recommendedCarpark;
    private ArrayList<CarparkListSection> sections;
    private BookingTicket ticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_carpark);

        Intent myIntent = getIntent();
        ticket = (BookingTicket)myIntent.getSerializableExtra("ticket");

        String jsonString = myIntent.getStringExtra("listing");
        try{
            JSONObject object = new JSONObject(jsonString);
            JSONObject recommended = object.getJSONObject("best_match");

            JSONArray others = object.getJSONArray("other_matches");

            recommendedCarpark = new ArrayList<>();
            recommendedCarpark.add(new CarPark(recommended));

            carparkList = new ArrayList<>();

            for(int i = 0; i < others.length(); i++){
                carparkList.add(new CarPark(others.getJSONObject(i)));
            }

        }catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Sorry! Something went wrong!", Toast.LENGTH_LONG).show();
        }

        bindUiItems();

        airportView.setText(ticket.getAirport().getAirportName());

        String entry = ticket.getFormattedEntryDate();
        String[] entryDateAndTime = entry.split(" - ");
        entryDate.setText(entryDateAndTime[0]);
        entryTime.setText(entryDateAndTime[1]);

        String exit = ticket.getFormattedExitDate();
        String[] exitDateAndTime = exit.split(" - ");
        exitDate.setText(exitDateAndTime[0]);
        exitTime.setText(exitDateAndTime[1]);

        sections = new ArrayList<>();
        sections.add(new CarparkListSection("Recommended", recommendedCarpark));
        sections.add(new CarparkListSection("Other Availabilities", carparkList));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        final CarparkSectionAdapter adapter = new CarparkSectionAdapter(this, sections, ticket);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Bind Ui with id
     */
    private void bindUiItems(){
        recyclerView = (RecyclerView)findViewById(R.id.sectioned_recycler_view);
        airportView = (TextView) findViewById(R.id.selectedAirport);
        entryDate = (TextView) findViewById(R.id.selectedEntryDate);
        exitDate = (TextView) findViewById(R.id.selectedExitDate);
        entryTime = (TextView) findViewById(R.id.selectedEntryTime);
        exitTime = (TextView) findViewById(R.id.selectedExitTime);
    }
}
