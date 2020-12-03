package com.example.airpark.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.airpark.R;
import com.example.airpark.models.BookingTicket;
import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

/**
 * Airpark Application - Group 14
 *
 * CS4125 -> System Analysis & Design
 * CS5721 -> Software Design
 *
 * Payment Confirmation with QR code generation
 */
public class PaymentConfirmedActivity extends AppCompatActivity {

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private ImageView qrImage;
    private TextView uniqueID;
    private Button homeBtn;
    private QRGEncoder qrgEncoder;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirmed);
        bindUiItems();

        String data = getUniqueID();
        BookingTicket.currentTicket.setTicketID(data);

        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        int dimensions = Math.min(point.x,point.y);
        dimensions = dimensions * 3 / 4;

        qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, dimensions);

        try {
            // Set QR code as Bitmap & display
            bitmap = qrgEncoder.encodeAsBitmap();
            qrImage.setImageBitmap(bitmap);
        } catch (WriterException e) {
            Log.v("Generate QR Code", e.toString());
        }


        homeBtn.setOnClickListener(v -> navigateToLanding());
    }

    /**
     * Bind Ui with id
     */
    private void bindUiItems(){
        qrImage = (ImageView) findViewById(R.id.qr_image);
        uniqueID = (TextView) findViewById(R.id.numeric_code);
        homeBtn = (Button)findViewById(R.id.home_Btn);
    }

    /**
     * Generates a random ID string using letters and numbers
     *
     * @return Unique ID String
     */
    private String getUniqueID(){
        int length = 12;
        StringBuilder builder = new StringBuilder();

        while(length-- != 0){
            int value = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(value));
        }
        uniqueID.setText(builder);

        return uniqueID.getText().toString().trim();
    }

    private void navigateToLanding(){
        Intent myIntent = new Intent(this, LandingSearchActivity.class);
        startActivity(myIntent);
    }
}
