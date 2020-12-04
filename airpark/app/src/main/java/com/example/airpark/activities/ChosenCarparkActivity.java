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

import java.text.DecimalFormat;

/**
 * Airpark Application - Group 14
 *
 * CS4125 -> System Analysis & Design
 * CS5721 -> Software Design
 *
 * Selected Car Park Details Screen
 */
public class ChosenCarparkActivity extends AppCompatActivity {
    private TextView airportView, carparkType, entryDate, exitDate, carparkPrice, carparkInfo;
    private Button selectBtn;
    private CarPark carpark;
    private BookingTicket ticket;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosen_carpark);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DecimalFormat dFormat = new DecimalFormat("#.##");

        Intent myIntent = getIntent();
        carpark = (CarPark)myIntent.getSerializableExtra("car park");
        ticket = (BookingTicket)myIntent.getSerializableExtra("ticket");


        //Change Screen Title if Long Term Car Park
        if(carpark.getCarparkType().equals("Long Term")){
            setTitle(R.string.carpark_long_term);
        }

        bindUiItems();

        airportView.setText(ticket.getAirport());
        carparkType.setText(carpark.getCarparkName());
        entryDate.setText(getString(R.string.carpark_entry) + " " + ticket.getArrivalDate() + " - " + ticket.getArrivalTime());
        exitDate.setText(getString(R.string.carpark_exit) + "     " + ticket.getExitDate()  + " - " + ticket.getExitTime());
        carparkPrice.setText(getString(R.string.total_price) + dFormat.format(ticket.getTicketPrice()));

        ImageView imageView = findViewById(R.id.carpark_image);
        Glide.with(this).load(carpark.getCarparkImage()).into(imageView);

        /** Harcoded for now **/
        carparkInfo.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse auctor lectus fermentum nunc malesuada, et tristique tellus lobortis. Vestibulum at finibus ipsum. Etiam laoreet erat sit " +
                "amet mauris posuere placerat. Integer enim sem, faucibus ac erat sed, euismod viverra dolor. Curabitur sed arcu quis ex suscipit volutpat. In hac habitasse platea dictumst. Praesent dui ante, " +
                "volutpat a massa sed, euismod ultrices urna. Praesent sit amet gravida sapien. Nunc fermentum, sapien auctor congue placerat, eros risus maximus diam, eget dictum turpis ex quis orci."+
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse auctor lectus fermentum nunc malesuada, et tristique tellus lobortis. Vestibulum at finibus ipsum. Etiam laoreet erat sit ");

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
    }
}
