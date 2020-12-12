package com.example.airpark.activities.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.androidnetworking.AndroidNetworking;
import com.example.airpark.R;
import com.example.airpark.activities.Prelogin.LoginActivity;
import com.example.airpark.activities.Create.LandingSearchActivity;
import com.example.airpark.designPatterns.state.Connected;
import com.example.airpark.designPatterns.state.Disconnected;
import com.example.airpark.models.UserModel;
import com.example.airpark.utils.Networking.NetworkHandler;
import com.example.airpark.utils.Utilities;
import com.stripe.android.PaymentConfiguration;

import org.json.JSONObject;

/**
 * Airpark Application - Group 14
 *
 * CS4125 -> System Analysis & Design
 * CS5721 -> Software Design
 *
 * The initial app activity -> Checks if user is logged in or not
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        PaymentConfiguration.init(getApplicationContext(), "pk_test_51HrvFLISFKjjBkEL0Vnxz62UUYtlQpDJcrUHmvSIvqed63wxTel3PfaZhdvhTT0uqKukhLVKfBpv4bkBPZItYJEB00SeuhsWMH");

        AndroidNetworking.initialize(getApplicationContext());

        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()){
            Connected conn = new Connected();
            conn.doAction(NetworkHandler.getInstance().connectionStatus);
        }else{
            Disconnected disconn = new Disconnected();
            disconn.doAction(NetworkHandler.getInstance().connectionStatus);
        }

        Intent myIntent;
        try{
            JSONObject object = Utilities.getInstance().fetchJsonObject(getApplicationContext(), "user");
            UserModel current = new UserModel(object.getInt("id"), object.getString("name"), object.getString("email"));
            UserModel.currentUser = current;
            myIntent = new Intent(MainActivity.this, LandingSearchActivity.class);
        }catch (Exception e){
            //no user model so go to login
            myIntent = new Intent(MainActivity.this, LoginActivity.class);
        }

        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(myIntent);
    }
}