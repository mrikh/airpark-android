package com.example.airpark.models;

import java.io.Serializable;
/**
 * Airpark Application - Group 14
 *
 * CS4125 -> System Analysis & Design
 * CS5721 -> Software Design
 *
 * A car park booking ticket
 */

public class BookingTicket implements Serializable {

    private String airport, arrivalDate, exitDate, arrivalTime, exitTime;
    private Boolean hasDisability, hasMotorbike;

    /**
     * Constructs Booking Ticket object
     * @param airport
     * @param arrivalDate
     * @param exitDate
     * @param arrivalTime
     * @param exitTime
     * @param hasDisability
     * @param hasMotorbike
     */
    public  BookingTicket(String airport, String arrivalDate, String exitDate, String arrivalTime, String exitTime, Boolean hasDisability, Boolean hasMotorbike){
        this.airport = airport;
        this.arrivalDate =arrivalDate;
        this.exitDate = exitDate;
        this.arrivalTime = arrivalTime;
        this.exitTime = exitTime;
        this.hasDisability = hasDisability;
        this.hasMotorbike = hasMotorbike;
    }

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