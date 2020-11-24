package com.example.airpark.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.airpark.R;
import com.example.airpark.adapters.RecyclerViewAdapter;
import com.example.airpark.models.BookingTicket;
import com.example.airpark.models.CarPark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SelectCarparkActivity extends AppCompatActivity {
    private TextView airportView, entryDate, exitDate, entryTime, exitTime;
    private BookingTicket ticket;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<CarPark> carparkList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_carpark);
        bindUiItems();

        Intent myIntent = getIntent();
        ticket = (BookingTicket)myIntent.getSerializableExtra("ticket");

        airportView.setText(ticket.getAirport());
        entryDate.setText(ticket.getArrivalDate());
        entryTime.setText(ticket.getArrivalTime());
        exitDate.setText(ticket.getExitDate());
        exitTime.setText(ticket.getExitTime());

        carparkList = new ArrayList<>();
        prepareCarparkList();
        recyclerViewAdapter = new RecyclerViewAdapter(carparkList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerViewAdapter);


    }

    /**
     * Bind Ui with id
     */
    private void bindUiItems(){
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        airportView = (TextView) findViewById(R.id.selectedAirport);
        entryDate = (TextView) findViewById(R.id.selectedEntryDate);
        exitDate = (TextView) findViewById(R.id.selectedExitDate);
        entryTime = (TextView) findViewById(R.id.selectedEntryTime);
        exitTime = (TextView) findViewById(R.id.selectedExitTime);
    }

    //Hard coded for now
    private void prepareCarparkList(){
        CarPark carPark = new CarPark(0, "Short Term", "Zone A", 2.5);
        carparkList.add(carPark);
        carPark = new CarPark(1, "Long Term", "Zone B", 10);
        carparkList.add(carPark);
    }
}
