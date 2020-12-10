package com.example.airpark.activities.Bookings;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.example.airpark.R;
import com.example.airpark.activities.QRgeneratorActivity;
import com.example.airpark.models.BookingModel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Airpark Application - Group 14
 *
 * CS4125 -> System Analysis & Design
 * CS5721 -> Software Design
 *
 * The Selected Upcoming/Past Booking Screen
 */
public class SelectedBookingActivity extends AppCompatActivity {

    private TextView airportView, carparkType, entryDate, exitDate, carparkPrice, carparkInfo, priceMoreInfo;
    private Button qrBtn;
    private ImageView deleteBtn;
    private BookingModel booking;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy - hh:mm a");
    private DecimalFormat df = new DecimalFormat("#.00");

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_booking);

        bindUiItems();

        Intent myIntent = getIntent();
        booking = (BookingModel)myIntent.getSerializableExtra("booking");

        //Default Short Term
        if(booking.isLongTerm()) {
            setTitle(R.string.carpark_long_term);
        }

        carparkInfo.setText(R.string.short_term_info);
        carparkPrice.setText(getString(R.string.price) + ": " + getString(R.string.euro) + df.format(booking.getFinalPrice()));

        airportView.setText(booking.getAirportName());
        carparkType.setText(booking.getCarparkName());

        entryDate.setText(getString(R.string.carpark_entry) + " " + sdf.format(booking.getStartDateTime()));
        exitDate.setText(getString(R.string.carpark_exit) + "     " + sdf.format(booking.getEndDateTime()));

        ImageView imageView = findViewById(R.id.carpark_image);

        Glide.with(this).load(booking.getCarparkImage()).into(imageView);
        priceMoreInfo.setVisibility(View.GONE);

        // QR code button
        qrBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, QRgeneratorActivity.class);
            intent.putExtra("screen name", "my booking");
            intent.putExtra("code", booking.getUniqueCode());
            startActivity(intent);
        });

        // Observer Pattern - - Delete Button (Publisher)
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SelectedBookingActivity.this);
                builder.setTitle("Confirm Delete Booking");
                builder.setMessage("Are you sure you want to delete this booking?");
                builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener(){
                    public  void onClick(DialogInterface dialog, int id){
                        sendMessage();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){}
                });
                builder.show();
            }
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
     * Binding UI with IDs
     */
    private void bindUiItems(){
        airportView = (TextView) findViewById(R.id.carpark_airport);
        carparkType = (TextView) findViewById(R.id.carpark_name);
        entryDate = (TextView) findViewById(R.id.carpark_entry_date);
        exitDate = (TextView) findViewById(R.id.carpark_exit_date);
        carparkPrice = (TextView) findViewById(R.id.carpark_price);
        carparkInfo = (TextView) findViewById(R.id.carpark_important_info);
        qrBtn = (Button)findViewById(R.id.see_qr_code_btn);
        deleteBtn = (ImageView)findViewById(R.id.deleteButton);
        priceMoreInfo = findViewById(R.id.more_price_info);
    }

    private void sendMessage() {
        Intent intent = new Intent(MyBookingsActivity.kDeleteBooking);
        intent.putExtra("booking", booking);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        finish();
    }
}
