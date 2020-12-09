package com.example.airpark.activities.Bookings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.airpark.R;
import com.example.airpark.activities.LandingSearchActivity;
import com.example.airpark.activities.Prelogin.LoginActivity;
import com.example.airpark.adapters.MyBookingsAdapter;
import com.example.airpark.models.UserModel;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;

/**
 * Airpark Application - Group 14
 *
 * CS4125 -> System Analysis & Design
 * CS5721 -> Software Design
 *
 * User's My Booking Screen -> Only available to app members
 */
public class MyBookingsActivity extends AppCompatActivity{

    private ActionBarDrawerToggle drawerToggle;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static String kDeleteBooking = "delete_booking";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.upcoming_tab));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.past_tab));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final MyBookingsAdapter bookingsAdapter = new MyBookingsAdapter(this,getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(bookingsAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        setupDrawer();
    }

    private void setupDrawer(){
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigationView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View headerView = navigationView.getHeaderView(0);

        TextView nameTextView = headerView.findViewById(R.id.nameTextView);
        nameTextView.setText(UserModel.currentUser.getName());

        TextView emailTextView = headerView.findViewById(R.id.emailTextView);
        emailTextView.setText(UserModel.currentUser.getEmail());

        //Hide Login View from menu
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.login).setVisible(false);

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Intent myIntent = null;
                if (item.getItemId() == R.id.home){
                    myIntent = new Intent(MyBookingsActivity.this, LandingSearchActivity.class);
                }else if(item.getItemId() == R.id.bookings) {
                    drawerLayout.close();
                }else if(item.getItemId() == R.id.login){
                    myIntent = new Intent(MyBookingsActivity.this, LoginActivity.class);
                }else{
                    UserModel.currentUser = null;
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(navigationView.getContext()).edit();
                    editor.clear();
                    editor.apply();
                    myIntent = new Intent(MyBookingsActivity.this, LoginActivity.class);
                }

                if(myIntent != null) {
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