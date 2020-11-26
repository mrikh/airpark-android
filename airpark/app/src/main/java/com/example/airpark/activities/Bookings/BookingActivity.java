package com.example.airpark.activities.Bookings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.airpark.R;
import com.example.airpark.activities.SearchActivity;
import com.example.airpark.models.UserModel;
import com.google.android.material.navigation.NavigationView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class BookingActivity extends AppCompatActivity {

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);

        setupDrawer();
    }

    private void setupDrawer(){

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigationView);

        //dont show menu button if not logged in
        if (UserModel.currentUser == null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }else{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            View headerView = navigationView.getHeaderView(0);

            TextView nameTextView = headerView.findViewById(R.id.nameTextView);
            nameTextView.setText(UserModel.currentUser.getName());

            TextView emailTextView = headerView.findViewById(R.id.emailTextView);
            emailTextView.setText(UserModel.currentUser.getEmail());
        }

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                if (item.getItemId() == R.id.home){

                    Intent myIntent = new Intent(BookingActivity.this, SearchActivity.class);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(myIntent);

                }else if(item.getItemId() == R.id.bookings){
                    drawerLayout.close();
                }else{
                    drawerLayout.close();
                }
                return false;
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}