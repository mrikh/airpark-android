package com.example.airpark.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Airport implements Serializable {
    private String airportName;
    private int airportID;

    public Airport(JSONObject object) throws JSONException {
        this.airportName = object.getString("airport_name");
        this.airportID = object.getInt("airport_id");
    }

    public Airport(String name){
        this.airportName = name;
    }

    public String getAirportName(){ return airportName; }

    public int getAirportID(){ return airportID; }

    @Override
    public String toString() {
        return airportName;
    }
}
