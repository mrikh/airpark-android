package com.example.airpark.models;

import android.widget.Space;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Airpark Application - Group 14
 *
 * CS4125 -> System Analysis & Design
 * CS5721 -> Software Design
 *
 * A car park booking ticket
 */

public class BookingTicket implements Serializable {

    private String customerName, customerEmail, customerNumber, carRegistration;
    private Airport airport;
    private CarPark selectedCarPark;
    private Date entryDate, exitDate;
    private CarParkSpace spaceType;
    private boolean isOld, hasCarWash, isLoggedIn;

    public BookingTicket(){
        this.airport = null;
        this.airport = null;
        this.entryDate = null;
        this.exitDate = null;
        this.spaceType = null;
        this.selectedCarPark = null;
    }

    public CarPark getSelectedCarPark() {
        return selectedCarPark;
    }

    public void setSelectedCarPark(CarPark selectedCarPark) {
        this.selectedCarPark = selectedCarPark;
    }

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Date getExitDate() {
        return exitDate;
    }

    public void setExitDate(Date exitDate) {
        this.exitDate = exitDate;
    }

    public CarParkSpace getSpaceRequired() { return spaceType; }

    public void setSpaceRequired(CarParkSpace space) {
        this.spaceType = space;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public void setCarRegistration(String carRegistration) {
        this.carRegistration = carRegistration;
    }

    public void setOld(boolean old) {
        isOld = old;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public void setHasCarWash(boolean hasCarWash) {
        this.hasCarWash = hasCarWash;
    }

    public JSONObject convertForBooking() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("car_park_id", getSelectedCarPark().getCarparkID());
        params.put("customer_name", customerName);
        params.put("email", customerEmail);
        params.put("phone", customerNumber);
        params.put("car_reg", carRegistration);
        params.put("is_old", isOld);
        params.put("is_logged_in", isLoggedIn);
        params.put("car_wash", hasCarWash);
        params.put("end_date", exitDate.getTime());
        params.put("start_date", entryDate.getTime());
        params.put("is_handicap", (spaceType instanceof DisabledSpace));
        params.put("is_two_wheeler", (spaceType instanceof MotorbikeSpace));
        return params;
    }

    public HashMap<String, String> getCarparkListingParameters(){

        HashMap<String, String> map = new HashMap<>();

        map.put("airport_id", Integer.toString(airport.getAirportID()));
        map.put("start_date", Long.toString(entryDate.getTime()));
        map.put("end_date", Long.toString(exitDate.getTime()));
        if (spaceType instanceof DisabledSpace){
            map.put("is_handicap", "1");
        }
        if (spaceType instanceof MotorbikeSpace){
            map.put("is_two_wheeler", "1");
        }

        return map;
    }
}