package com.example.airpark.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Airpark Application - Group 14
 *
 * CS4125 -> System Analysis & Design
 * CS5721 -> Software Design
 *
 * An airport car park
 */
public class CarPark implements Serializable {

    private int carparkID, disabledCapacity, twoWheelerCapacity, normalCapacity;
    private String carparkName, carparkImageString;
    private double price;
    private long latitude, longitude;
    private CarParkType carparkType;
    private Airport airport;

    public enum CarParkType{
        LONG_TERM,
        SHORT_TERM
    }

    public CarPark(JSONObject object) throws JSONException {
        this.carparkID = object.getInt("id");
        this.airport = new Airport(object);
        this.carparkImageString = object.getString("image");
        this.carparkName = object.getString("car_park_name");
        this.price = object.getDouble("price");
        this.longitude = object.getLong("longitude");
        this.latitude = object.getLong("latitude");
        this.disabledCapacity = object.getInt("dis_capacity");
        this.twoWheelerCapacity = object.getInt("tw_capacity");
        this.normalCapacity = object.getInt("normal_capacity");
        this.carparkType = object.getBoolean("is_long_term") ? CarParkType.LONG_TERM : CarParkType.SHORT_TERM;
    }

    public int getCarparkID() {
        return carparkID;
    }

    public String getCarparkImage(){ return carparkImageString; }

    public String getCarparkName(){ return carparkName; }

    public CarParkType getCarparkType() {
        return carparkType;
    }

    public String getCarparkTypeString(){
        switch (carparkType){
            case LONG_TERM: return "Long Term";
            case SHORT_TERM: return "Short Term";
        }

        return null;
    }

    public double getPrice(){ return price; }
}
