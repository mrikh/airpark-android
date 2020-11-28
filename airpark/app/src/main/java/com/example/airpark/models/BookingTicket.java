package com.example.airpark.models;

import java.io.Serializable;

public class BookingTicket implements Serializable {

//    private static BookingTicket btInstance = null;
    private String airport, arrivalDate, exitDate, arrivalTime, exitTime;
    private Boolean hasDisability, hasMotorbike;

    public  BookingTicket(String airport, String arrivalDate, String exitDate, String arrivalTime, String exitTime, Boolean hasDisability, Boolean hasMotorbike){
        this.airport = airport;
        this.arrivalDate =arrivalDate;
        this.exitDate = exitDate;
        this.arrivalTime = arrivalTime;
        this.exitTime = exitTime;
        this.hasDisability = hasDisability;
        this.hasMotorbike = hasMotorbike;
    }

//    public static BookingTicket getInstance(){
//        if(btInstance == null){
//            btInstance = new BookingTicket();
//        }
//        return  btInstance;
//    }

    public String getAirport() { return airport; }

    public String getArrivalDate(){
        return arrivalDate;
    }

    public String getExitDate(){ return exitDate; }

    public String getArrivalTime() { return arrivalTime; }

    public String getExitTime() { return exitTime; }

    public Boolean hasDisability() { return hasDisability; }

    public Boolean hasMotorbike() { return hasMotorbike; }

}