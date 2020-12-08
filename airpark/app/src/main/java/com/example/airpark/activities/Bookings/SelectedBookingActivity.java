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
import com.example.airpark.activities.QRgeneratorActivity;
import com.example.airpark.models.BookingTicket;
import com.example.airpark.models.CarPark;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

// Note: Code for QR code button and Delete button still to be fixed!

public class SelectedBookingActivity extends AppCompatActivity {

    private TextView airportView, carparkType, entryDate, exitDate, carparkPrice, carparkInfo, priceMoreInfo;
    private Button qrBtn;
    private ImageView deleteBtn;
    private BookingTicket ticket;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy - hh:mm a");
    private DecimalFormat df = new DecimalFormat("#.00");

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_selected_booking);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bindUiItems();

        Intent myIntent = getIntent();
        ticket = (BookingTicket)myIntent.getSerializableExtra("ticket");

        /** Harcoded in place of ticket **/
        ArrayList<String> test = new ArrayList<>(3);
        test.add("Shannon Airport");
        test.add("Green Short Car Park");
        test.add("10 Oct 2020");
        test.add("15 Oct 2020");
        test.add("€123");

        // Following works for Details Page:
        //Default Short Term
        carparkInfo.setText(R.string.short_term_info);
//        carparkPrice.setText("Price: " + R.string.euro + df.format(ticket.getPrice()) + R.string.price_per_hour);

        carparkPrice.setText("Price: " + test.get(4)); //Remove when database added

        //Change Screen Title if Long Term Car Park
//        if(ticket.getSelectedCarPark().getCarparkType() == CarPark.CarParkType.LONG_TERM){
//            setTitle(R.string.carpark_long_term);
//            carparkInfo.setText(R.string.long_term_info);
//            carparkPrice.setText("Price: " + R.string.euro + df.format(ticket.getPrice()) + R.string.price_per_day);
//        }

//        airportView.setText(ticket.getAirport().getAirportName());
        airportView.setText(test.get(0));

//        carparkType.setText(ticket.getSelectedCarPark().getCarparkName());
        carparkType.setText(test.get(1));

//        entryDate.setText(getString(R.string.carpark_entry) + " " + sdf.format(ticket.getEntryDate()));
//        exitDate.setText(getString(R.string.carpark_exit) + "     " + sdf.format(ticket.getExitDate()));
        entryDate.setText(getString(R.string.carpark_entry) + " " + test.get(2));
        exitDate.setText(getString(R.string.carpark_exit) + "     " + test.get(3));

        ImageView imageView = findViewById(R.id.carpark_image);
//        Glide.with(this).load(ticket.getSelectedCarPark().getCarparkImage()).into(imageView);

        priceMoreInfo.setText(R.string.discount_info);

        // QR code button -- ADD
        qrBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, QRgeneratorActivity.class);
            intent.putExtra("screen name", "my booking");
            startActivity(intent);
        });

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

}
