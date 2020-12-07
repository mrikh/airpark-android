package com.example.airpark.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.airpark.R;
import com.example.airpark.adapters.MyBookingsItemAdapter;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UpcomingBookingsFragment extends Fragment {

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

        /** Harcoded for now **/
        ArrayList<String> test = new ArrayList<>(3);
        test.add("Dublin Airport");
        test.add("Express Car Park");
        test.add("2 Jan 2021 - 5 Jan 2021");

        MyBookingsItemAdapter adapter = new MyBookingsItemAdapter(test);
        recyclerView.setAdapter(adapter);
    }

}
