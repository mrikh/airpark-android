package com.example.airpark.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.airpark.R;
import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

/**
 * Airpark Application - Group 14
 *
 * CS4125 -> System Analysis & Design
 * CS5721 -> Software Design
 *
 * Screen generating and displaying booking ticket QR code
 */
public class QRgeneratorActivity extends AppCompatActivity {

    private String ALPHA_NUMERIC_STRING;

    private ImageView qrImage;
    private TextView uniqueID, title;
    private Button homeBtn;
    private QRGEncoder qrgEncoder;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        bindUiItems();

        homeBtn.setOnClickListener(v -> navigateToLanding());

        Intent myIntent = getIntent();
        ALPHA_NUMERIC_STRING = myIntent.getStringExtra("code");

        //If viewing booking QR from My Bookings section
        String comingFrom = getIntent().getStringExtra("screen name");

        if(comingFrom != null && comingFrom.equalsIgnoreCase("my booking")){
            title.setVisibility(View.GONE);
            homeBtn.setVisibility(View.GONE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        uniqueID.setText(ALPHA_NUMERIC_STRING);

        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        int dimensions = Math.min(point.x,point.y);
        dimensions = dimensions * 3 / 4;

        qrgEncoder = new QRGEncoder(ALPHA_NUMERIC_STRING, null,QRGContents.Type.TEXT, dimensions);

        try {
            // Set QR code as Bitmap & display
            bitmap = qrgEncoder.encodeAsBitmap();
            qrImage.setImageBitmap(bitmap);
        } catch (
            WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() { }

    /**
     * Bind Ui with id
     */
    private void bindUiItems(){
        title = (TextView) findViewById(R.id.payment_confirmed_title);
        qrImage = (ImageView) findViewById(R.id.qr_image);
        uniqueID = (TextView) findViewById(R.id.numeric_code);
        homeBtn = (Button)findViewById(R.id.home_Btn);
    }

    private void navigateToLanding(){
        Intent myIntent = new Intent(this, LandingSearchActivity.class);
        startActivity(myIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

