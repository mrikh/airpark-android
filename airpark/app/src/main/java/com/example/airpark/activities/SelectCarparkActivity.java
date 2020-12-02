package com.example.airpark.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.airpark.R;
import com.example.airpark.adapters.CarparkSectionAdapter;
import com.example.airpark.models.BookingTicket;
import com.example.airpark.models.CalculatePrice;
import com.example.airpark.models.CarPark;
import com.example.airpark.models.CarParkSpace;
import com.example.airpark.models.CarparkListSection;
import com.example.airpark.models.DisabledSpace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Airpark Application - Group 14
 *
 * CS4125 -> System Analysis & Design
 * CS5721 -> Software Design
 *
 * Car Park Options Screen (available car parks to choose from)
 */
public class SelectCarparkActivity extends AppCompatActivity {
    private TextView airportView, entryDate, exitDate, entryTime, exitTime;
    private BookingTicket ticket;
    private RecyclerView recyclerView;
    private ArrayList<CarPark> carparkList, recommendedCarpark;
    private ArrayList<CarparkListSection> sections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_carpark);
        bindUiItems();

        Intent myIntent = getIntent();
        ticket = (BookingTicket)myIntent.getSerializableExtra("ticket");

        airportView.setText(ticket.getAirport());
        entryDate.setText(ticket.getArrivalDate());
        entryTime.setText(ticket.getArrivalTime());
        exitDate.setText(ticket.getExitDate());
        exitTime.setText(ticket.getExitTime());

        carparkList = new ArrayList<>();
        prepareCarparkList();

        sections = new ArrayList<>();
        try {
            recommendedCarpark = getRecommendedCarpark();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Remove recommended car park from car park list so it doesn't appear in 'other availabilities'
        for(int i=0; i<carparkList.size(); i++){
            for(int j=0; j<recommendedCarpark.size(); j++) {
                if (carparkList.get(i).equals(recommendedCarpark.get(j))) {
                    carparkList.remove(i);
                }
            }
        }
        sections.add(new CarparkListSection("Recommended",recommendedCarpark));
        sections.add(new CarparkListSection("Other Availabilities",carparkList));


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        final CarparkSectionAdapter adapter = new CarparkSectionAdapter(this, sections, ticket);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Bind Ui with id
     */
    private void bindUiItems(){
        recyclerView = (RecyclerView)findViewById(R.id.sectioned_recycler_view);
        airportView = (TextView) findViewById(R.id.selectedAirport);
        entryDate = (TextView) findViewById(R.id.selectedEntryDate);
        exitDate = (TextView) findViewById(R.id.selectedExitDate);
        entryTime = (TextView) findViewById(R.id.selectedEntryTime);
        exitTime = (TextView) findViewById(R.id.selectedExitTime);
    }

    /**         Hardcoded           **/
    private void prepareCarparkList(){
        CarPark carPark = new CarPark(0, "Short Term", "Zone A", 2.5, 5, 10);
        carparkList.add(carPark);
        carPark = new CarPark(1, "Long Term", "Zone B", 10, 20, 0);
        carparkList.add(carPark);
        carPark = new CarPark(2, "Long Term", "Zone C", 15, 18, 25);
        carparkList.add(carPark);

        //Remove car park if no available spaces
        for(int i=0; i<carparkList.size(); i++){
            if(carparkList.get(i).isFull()) {
                carparkList.remove(i);
            }
        }
    }

    /**
     * Method gets the recommended car park depending on price
     *
     * @return CarPark ArrayList with recommended option
     */
    private ArrayList<CarPark> getRecommendedCarpark() throws ParseException {
        CalculatePrice price = new CalculatePrice(carparkList.get(0).getPrice());
        ArrayList<CarPark> recommended = new ArrayList<>();
        recommended.add(carparkList.get(0));

        double min = price.calculatePrice(ticket.getArrivalTime(), ticket.getExitTime(), ticket.getArrivalDate(), ticket.getExitDate(), carparkList.get(0).getCarparkType());

        for(int i=1; i<carparkList.size(); i++){
            price = new CalculatePrice(carparkList.get(i).getPrice());
            double max = price.calculatePrice(ticket.getArrivalTime(), ticket.getExitTime(), ticket.getArrivalDate(), ticket.getExitDate(), carparkList.get(i).getCarparkType());

            if(max < min){
                min = max;
                recommended.remove(0);
                recommended.add(carparkList.get(i));
            }else if(min == max){
                recommended.add(carparkList.get(i));
            }
        }
        return recommended;
    }
}
