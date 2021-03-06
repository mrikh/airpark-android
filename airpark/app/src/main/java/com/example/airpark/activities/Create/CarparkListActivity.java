package com.example.airpark.activities.Create;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.airpark.R;
import com.example.airpark.adapters.CarparkSectionAdapter;
import com.example.airpark.models.BookingTicket;
import com.example.airpark.models.CarPark;
import com.example.airpark.models.CarparkListSection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Airpark Application - Group 14
 *
 * CS4125 -> System Analysis & Design
 * CS5721 -> Software Design
 *
 * Car Park List Options Screen (available car parks to choose from)
 */
public class CarparkListActivity extends AppCompatActivity {

    private TextView airportView, entryDate, exitDate, entryTime, exitTime;
    private RecyclerView recyclerView;
    private ArrayList<CarPark> carparkList, recommendedCarpark;
    private ArrayList<CarparkListSection> sections;
    private BookingTicket ticket;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy - hh:mm a");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_carpark_list);

        bindUiItems();

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
            Toast.makeText(this, getString(R.string.something_wrong), Toast.LENGTH_LONG).show();
        }

        airportView.setText(ticket.getAirport().getAirportName());

        String entry = sdf.format(ticket.getEntryDate());
        String[] entryDateAndTime = entry.split(" - ");
        entryDate.setText(entryDateAndTime[0]);
        entryTime.setText(entryDateAndTime[1]);

        String exit = sdf.format(ticket.getExitDate());
        String[] exitDateAndTime = exit.split(" - ");
        exitDate.setText(exitDateAndTime[0]);
        exitTime.setText(exitDateAndTime[1]);

        sections = new ArrayList<>();
        sections.add(new CarparkListSection(getString(R.string.recommended), recommendedCarpark));
        sections.add(new CarparkListSection(getString(R.string.other_available), carparkList));

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
