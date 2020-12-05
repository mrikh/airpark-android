package com.example.airpark.models;

import android.widget.Space;

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

    public enum SpaceType{
        GENERAL,
        DISABLED,
        TWO_WHEELER
    }

    private String ticketID;
    private SpaceType spaceType;
    private int carparkID;
    private double ticketPrice;
    private Boolean isElderly, hasCarWash;
    private Airport airport;
    private Date entryDate, exitDate;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy - hh:mm a");

    /**
     * Constructs Booking Ticket object
     *
     */
    public  BookingTicket(){
        this.airport = null;
        this.ticketID = null;
        this.airport = null;
        this.entryDate = null;
        this.exitDate = null;
        this.carparkID = -1;
        this.ticketPrice = -1;
        this.hasCarWash = false;
        this.isElderly = false;
        this.spaceType = SpaceType.GENERAL;
    }

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }

    public String getTicketID(){ return ticketID; }

    public void setTicketID(String ticketID){ this.ticketID = ticketID; }

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

    public SpaceType getSpaceRequired() { return spaceType; }

    public void setSpaceRequired(SpaceType space) {this.spaceType = space;}

    public int getCarparkID() { return carparkID; }

    public void setCarparkID(int carparkID) {  this.carparkID = carparkID; }

    public  double getTicketPrice() { return ticketPrice; }

    public void setTicketPrice(double ticketPrice) { this.ticketPrice = ticketPrice; }

    public Boolean isElderly() { return isElderly; }

    public void setIsElderly(boolean isElderly) { this.isElderly = isElderly; }

    public Boolean hasCarWash() { return hasCarWash; }

    public void sethasCarWash(boolean hasCarWash) { this.hasCarWash = hasCarWash; }

    public String getFormattedEntryDate(){
        return sdf.format(entryDate);
    }

    public String getFormattedExitDate(){
        return sdf.format(exitDate);
    }

    public double calculatePrice(Double pricePerHour) throws ParseException {
        int lengthOfStay = getLengthOfStay();
        return lengthOfStay * pricePerHour;
    }

    public HashMap<String, String> getCarparkListingParameters(){

        HashMap<String, String> map = new HashMap<>();

        map.put("airport_id", Integer.toString(airport.getAirportID()));
        map.put("start_date", Long.toString(entryDate.getTime()));
        map.put("end_date", Long.toString(exitDate.getTime()));

        switch (spaceType){
            case DISABLED: {
                map.put("is_handicap", "1");
                break;
            }
            case TWO_WHEELER: {
                map.put("is_two_wheeler", "1");
            }
            default: break;
        }

        return map;
    }

    private int getLengthOfStay(){
        long noOfHours = 0;
        noOfHours = ((exitDate.getTime()-entryDate.getTime())/(1000*60*60));
        return (int)noOfHours;
    }
}