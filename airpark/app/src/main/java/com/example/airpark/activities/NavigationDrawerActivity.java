package com.example.airpark.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.airpark.R;
import com.example.airpark.activities.Bookings.MyBookingsActivity;
import com.example.airpark.activities.Prelogin.LoginActivity;
import com.example.airpark.models.UserModel;
import com.google.android.material.navigation.NavigationView;

public class NavigationDrawerActivity extends AppCompatActivity {

    private ActionBarDrawerToggle drawerToggle;

    @SuppressLint("ResourceType")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.menu.navigation_menu);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigationView);
        View headerView = navigationView.getHeaderView(0);
        TextView nameTextView = headerView.findViewById(R.id.nameTextView);
        TextView emailTextView = headerView.findViewById(R.id.emailTextView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Menu menu = navigationView.getMenu();

    //don't show menu button if not logged in
        if (UserModel.currentUser == null) {
        nameTextView.setVisibility(View.GONE);
        emailTextView.setVisibility(View.GONE);
        menu.findItem(R.id.home).setVisible(false);
        menu.findItem(R.id.logout).setVisible(false);
        menu.findItem(R.id.bookings).setVisible(false);
        }else{
            nameTextView.setText(UserModel.currentUser.getName());
            emailTextView.setText(UserModel.currentUser.getEmail());
            menu.findItem(R.id.login).setVisible(false);
        }

            navigationView.bringToFront();
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.home){
                    drawerLayout.close();
                }else if(item.getItemId() == R.id.bookings) {
                    Intent myIntent = new Intent(NavigationDrawerActivity.this, MyBookingsActivity.class);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(myIntent);
                }else if(item.getItemId() == R.id.login){
                    Intent myIntent = new Intent(NavigationDrawerActivity.this, LoginActivity.class);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(myIntent);
                }else{
                    UserModel.currentUser = null;
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(navigationView.getContext()).edit();
                    editor.clear();
                    editor.apply();
                    Intent myIntent = new Intent(NavigationDrawerActivity.this, LoginActivity.class);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(myIntent);
                }
                return false;
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        if (drawerToggle != null) {
            drawerToggle.syncState();
        }
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
