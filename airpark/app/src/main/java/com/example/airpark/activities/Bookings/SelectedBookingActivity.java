package com.example.airpark.activities.Bookings;

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
import com.example.airpark.activities.EnterDetailsActivity;
import com.example.airpark.models.BookingTicket;
import com.example.airpark.models.CarPark;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

// Note: Code for QR code button and Delete button still to be fixed!

public class SelectedBookingActivity extends AppCompatActivity {

    private TextView airportView, carparkType, entryDate, exitDate, carparkPrice, carparkInfo, priceMoreInfo;
    private Button qrBtn, deleteBtn;
    private BookingTicket ticket;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_selected_booking);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent myIntent = getIntent();
        ticket = (BookingTicket)myIntent.getSerializableExtra("ticket");

        bindUiItems();
        DecimalFormat df = new DecimalFormat("#.00");

        // Following works for Details Page:
        //Default Short Term
        carparkInfo.setText(R.string.short_term_info);
        carparkPrice.setText("Price: €" + df.format(ticket.getSelectedCarPark().getPrice()) + "/hr");

        //Change Screen Title if Long Term Car Park
        if(ticket.getSelectedCarPark().getCarparkType() == CarPark.CarParkType.LONG_TERM){
            setTitle(R.string.carpark_long_term);
            carparkInfo.setText(R.string.long_term_info);
            carparkPrice.setText("Price: €" + df.format(ticket.getSelectedCarPark().getPrice()) + "/day");
        }

        airportView.setText(ticket.getAirport().getAirportName());
        carparkType.setText(ticket.getSelectedCarPark().getCarparkName());

        entryDate.setText(getString(R.string.carpark_entry) + " " + sdf.format(ticket.getEntryDate()));
        exitDate.setText(getString(R.string.carpark_exit) + "     " + sdf.format(ticket.getExitDate()));

        ImageView imageView = findViewById(R.id.carpark_image);
        Glide.with(this).load(ticket.getSelectedCarPark().getCarparkImage()).into(imageView);

        priceMoreInfo.setText(R.string.discount_info);

        // QR code button -- ADD
        qrBtn.setOnClickListener(v -> {});

        // change method for delete button -- ADD
        deleteBtn.setOnClickListener(v -> {});

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
     * Binding UI with IDs
     */
    @SuppressLint("WrongViewCast")
    private void bindUiItems(){
        airportView = (TextView) findViewById(R.id.carpark_airport);
        carparkType = (TextView) findViewById(R.id.carpark_name);
        entryDate = (TextView) findViewById(R.id.carpark_entry_date);
        exitDate = (TextView) findViewById(R.id.carpark_exit_date);
        carparkPrice = (TextView) findViewById(R.id.carpark_price);
        carparkInfo = (TextView) findViewById(R.id.carpark_important_info);
        qrBtn = (Button)findViewById(R.id.see_qr_code_btn);
        deleteBtn = (Button)findViewById(R.id.deleteButton);
        priceMoreInfo = findViewById(R.id.more_price_info);
    }

}
