package com.example.airpark.activities.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.androidnetworking.AndroidNetworking;
import com.example.airpark.R;
import com.example.airpark.activities.Payments.StripeActivity;
import com.example.airpark.activities.Prelogin.LoginActivity;
import com.example.airpark.activities.LandingSearchActivity;
import com.example.airpark.models.UserModel;
import com.example.airpark.utils.Utilities;
import com.stripe.android.PaymentConfiguration;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PaymentConfiguration.init(getApplicationContext(), "pk_test_51HrvFLISFKjjBkEL0Vnxz62UUYtlQpDJcrUHmvSIvqed63wxTel3PfaZhdvhTT0uqKukhLVKfBpv4bkBPZItYJEB00SeuhsWMH");

        AndroidNetworking.initialize(getApplicationContext());
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