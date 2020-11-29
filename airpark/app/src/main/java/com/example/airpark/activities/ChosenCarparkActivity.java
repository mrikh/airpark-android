package com.example.airpark.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.airpark.R;
import com.example.airpark.models.BookingTicket;
import com.example.airpark.models.CarPark;

public class ChosenCarparkActivity extends AppCompatActivity {
    private BookingTicket ticket;
    private CarPark carpark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosen_carpark);

        Intent myIntent = getIntent();
        ticket = (BookingTicket)myIntent.getSerializableExtra("ticket");
        carpark = (CarPark)myIntent.getSerializableExtra("Car Park");

        if(carpark.getCarparkType().equals("Long Term")){
            setTitle(R.string.carpark_long_term);
        }

    }
}
