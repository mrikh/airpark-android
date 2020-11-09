package com.example.airpark.models;

public class Airport {
    private String airportName;
    private int airportID;

    public Airport(String name, int id){
        this.airportName = name;
        this.airportID = id;
    }

    public String getAirportName(){ return airportName; }

    public int getAirportID(){ return airportID; }
}
