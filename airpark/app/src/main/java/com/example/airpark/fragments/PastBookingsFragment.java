package com.example.airpark.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airpark.R;
import com.example.airpark.adapters.MyBookingsItemAdapter;
import com.example.airpark.models.BookingModel;
import com.example.airpark.utils.HelperInterfaces.NetworkingClosure;
import com.example.airpark.utils.Networking.NetworkHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Airpark Application - Group 14
 *
 * CS4125 -> System Analysis & Design
 * CS5721 -> Software Design
 *
 * Fragment for Past Bookings tab
 */
public class PastBookingsFragment extends Fragment {
    private RecyclerView recyclerView;

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

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //add progress
        NetworkHandler.getInstance().pastBookings(new NetworkingClosure() {
            @Override
            public void completion(JSONObject object, String message) {
                if (object == null){
                    Toast.makeText(getContext(), getString(R.string.unable_fetch_bookings), Toast.LENGTH_SHORT).show();
                    return;
                }

                try{
                    JSONArray arr = object.getJSONArray("history");
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

