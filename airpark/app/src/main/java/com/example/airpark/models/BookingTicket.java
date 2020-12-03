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

//    public static BookingTicket currentTicket;

    private String airport, arrivalDate, exitDate, arrivalTime, exitTime;
    private int carparkID;
    private double ticketPrice;
    private Boolean hasDisability, hasMotorbike, isElderly, hasCarWash;

    /**
     * Constructs Booking Ticket object
     *
     */
    public  BookingTicket(){
        this.airport = null;
        this.arrivalDate = null;
        this.exitDate = null;
        this.arrivalTime = null;
        this.exitTime = null;
        this.hasDisability = false;
        this.hasMotorbike = false;
        this.carparkID = -1;
        this.ticketPrice = -1;
        this.hasCarWash = false;
        this.isElderly = false;
    }

    public String getAirport() { return airport; }

    public void setAirport(String airport) {  this.airport = airport; }

    public String getArrivalDate(){
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {  this.arrivalDate = arrivalDate; }

    public String getExitDate(){ return exitDate; }

    public void setExitDate(String exitDate) {  this.exitDate = exitDate; }

    public String getArrivalTime() { return arrivalTime; }

    public void setArrivalTime(String arrivalTime) {  this.arrivalTime = arrivalTime; }

    public String getExitTime() { return exitTime; }

    public void setExitTime(String exitTime) {  this.exitTime = exitTime; }

    public Boolean hasDisability() { return hasDisability; }

    public void setHasDisability(boolean hasDisability) { this.hasDisability = hasDisability; }

    public Boolean hasMotorbike() { return hasMotorbike; }

    public void setHasMotorbike(boolean hasMotorbike) { this.hasMotorbike = hasMotorbike; }

//    public int getCarparkID() { return carparkID; }
//
//    public void setCarparkID(int carparkID) {  this.carparkID = carparkID; }

    public  double getTicketPrice() { return ticketPrice; }

    public void setTicketPrice(double ticketPrice) { this.ticketPrice = ticketPrice; }

    public Boolean isElderly() { return isElderly; }

    public void setIsElderly(boolean isElderly) { this.isElderly = isElderly; }

    public Boolean hasCarWash() { return hasCarWash; }

    public void sethasCarWash(boolean hasCarWash) { this.hasCarWash = hasCarWash; }

}