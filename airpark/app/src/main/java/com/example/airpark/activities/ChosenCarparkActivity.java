package com.example.airpark.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.airpark.R;
import com.example.airpark.models.BookingTicket;
import com.example.airpark.models.CalculatePrice;
import com.example.airpark.models.CarPark;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Airpark Application - Group 14
 *
 * CS4125 -> System Analysis & Design
 * CS5721 -> Software Design
 *
 * Selected Car Park Details Screen
 */
public class ChosenCarparkActivity extends AppCompatActivity {

    private TextView airportView, carparkType, entryDate, exitDate, carparkPrice, carparkInfo, priceMoreInfo;
    private Button selectBtn;
    private BookingTicket ticket;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy - hh:mm a");

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chosen_carpark);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent myIntent = getIntent();
        ticket = (BookingTicket)myIntent.getSerializableExtra("ticket");

        bindUiItems();
        DecimalFormat df = new DecimalFormat("#.00");

        //Default Short Term
        carparkInfo.setText(R.string.short_term_info);
        carparkPrice.setText(getString(R.string.price) + ": €" + df.format(ticket.getSelectedCarPark().getPrice()) + getString(R.string.price_per_hour));

        //Change Screen Title if Long Term Car Park
        if(ticket.getSelectedCarPark().getCarparkType() == CarPark.CarParkType.LONG_TERM){
            setTitle(R.string.carpark_long_term);
            carparkInfo.setText(R.string.long_term_info);
            carparkPrice.setText(getString(R.string.price) + ": €" + df.format(ticket.getSelectedCarPark().getPrice()) + getString(R.string.price_per_day));
        }

        airportView.setText(ticket.getAirport().getAirportName());
        carparkType.setText(ticket.getSelectedCarPark().getCarparkName());

        entryDate.setText(getString(R.string.carpark_entry) + " " + sdf.format(ticket.getEntryDate()));
        exitDate.setText(getString(R.string.carpark_exit) + "     " + sdf.format(ticket.getExitDate()));

        ImageView imageView = findViewById(R.id.carpark_image);
        Glide.with(this).load(ticket.getSelectedCarPark().getCarparkImage()).into(imageView);

        priceMoreInfo.setText(R.string.discount_info);

        selectBtn.setOnClickListener(v -> {
            Intent myIntent2 = new Intent(this, EnterDetailsActivity.class);
            myIntent2.putExtra("ticket", ticket);
            startActivity(myIntent2);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Bind Ui with id
     */
    private void bindUiItems(){
        airportView = (TextView) findViewById(R.id.carpark_airport);
        carparkType = (TextView) findViewById(R.id.carpark_name);
        entryDate = (TextView) findViewById(R.id.carpark_entry_date);
        exitDate = (TextView) findViewById(R.id.carpark_exit_date);
        carparkPrice = (TextView) findViewById(R.id.carpark_price);
        carparkInfo = (TextView) findViewById(R.id.carpark_important_info);
        selectBtn = (Button)findViewById(R.id.select_carpark_btn);
        priceMoreInfo = findViewById(R.id.more_price_info);
    }
}
