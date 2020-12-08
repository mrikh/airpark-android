package com.example.airpark.activities.Bookings;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.airpark.R;
import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

// Edit whole class for Booking QR Activity.
public class BookingQRActivity extends AppCompatActivity {

    private String ALPHA_NUMERIC_STRING;

    private ImageView qrImage;
    private TextView uniqueID;
    private QRGEncoder qrgEncoder;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_booking);

        Intent myIntent = getIntent();
        ALPHA_NUMERIC_STRING = myIntent.getStringExtra("code");

        bindUiItems();

        uniqueID.setText(ALPHA_NUMERIC_STRING);

        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        int dimensions = Math.min(point.x,point.y);
        dimensions = dimensions * 3 / 4;

        qrgEncoder = new QRGEncoder(ALPHA_NUMERIC_STRING, null, QRGContents.Type.TEXT, dimensions);

        try {
            // Set QR code as Bitmap & display
            bitmap = qrgEncoder.encodeAsBitmap();
            qrImage.setImageBitmap(bitmap);
        } catch (WriterException e) {
            Log.v("Generate QR Code", e.toString());
        }

    }

    @Override
    public void onBackPressed() { }

    /**
     * Binding UI with IDs
     */
    private void bindUiItems(){
        qrImage = (ImageView) findViewById(R.id.qr_image);
        uniqueID = (TextView) findViewById(R.id.numeric_code);
    }

}
