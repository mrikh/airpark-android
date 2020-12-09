package com.example.airpark.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.airpark.R;
import com.example.airpark.activities.Bookings.MyBookingsActivity;
import com.example.airpark.adapters.MyBookingsItemAdapter;
import com.example.airpark.models.Airport;
import com.example.airpark.models.BookingModel;
import com.example.airpark.utils.HelperInterfaces.NetworkingClosure;
import com.example.airpark.utils.Networking.NetworkHandler;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Airpark Application - Group 14
 *
 * CS4125 -> System Analysis & Design
 * CS5721 -> Software Design
 *
 * Fragment for Upcoming Bookings tab
 */
public class UpcomingBookingsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            BookingModel booking = (BookingModel) intent.getSerializableExtra("booking");
            MyBookingsItemAdapter adapter = (MyBookingsItemAdapter) recyclerView.getAdapter();
            adapter.deleteBooking(booking);
        }

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming_bookings, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.upcoming_bookings_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        progressBar = (ProgressBar)view.findViewById(R.id.progress);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        progressBar.setVisibility(View.INVISIBLE);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this.getContext()).unregisterReceiver(mMessageReceiver);
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(this.getContext()).registerReceiver(mMessageReceiver, new IntentFilter(MyBookingsActivity.kDeleteBooking));
        super.onPause();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progressBar.setVisibility(View.VISIBLE);
        //add progress
        NetworkHandler.getInstance().upcomingBookings(new NetworkingClosure() {
            @Override
            public void completion(JSONObject object, String message) {
                progressBar.setVisibility(View.INVISIBLE);
                if (object == null){
                    Toast.makeText(getContext(), getString(R.string.unable_fetch_bookings), Toast.LENGTH_SHORT).show();
                    return;
                }

                try{
                    JSONArray arr = object.getJSONArray("upcoming");
                    ArrayList<BookingModel> result = new ArrayList<>();

                    for (int i = 0; i < arr.length(); i++){
                        result.add(new BookingModel(arr.getJSONObject(i)));
                    }

                    MyBookingsItemAdapter adapter = new MyBookingsItemAdapter(result);
                    recyclerView.setAdapter(adapter);
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
