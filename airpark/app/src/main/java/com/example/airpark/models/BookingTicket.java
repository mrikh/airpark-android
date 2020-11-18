package com.example.airpark.models;

public class BookingTicket {

    private String airport, arrivalDate, exitDate, arrivalTime, exitTime;
    private Boolean hasDisability, hasMotorbike;

    public  BookingTicket(){
        airport = null;
        arrivalDate = null;
        exitDate = null;
        arrivalTime = null;
        exitTime = null;
    }

    public String getAirport() { return airport; }

    public void setAirport(String airport) { this.airport = airport; }

    public String getArrivalDate(){
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate){
        this.arrivalDate = arrivalDate;
    }

    public String getExitDate(){ return exitDate; }

    public void setExitDate(String exitDate){
        this.exitDate = exitDate;
    }

    public String getArrivalTime() { return arrivalTime; }

    public void setArrivalTime(String arrivalTime) {this.arrivalTime = arrivalTime; }

    public String getExitTime() { return exitTime; }

    public void setExitTime(String exitTime) {this.exitTime = exitTime; }

    public Boolean hasDisability() { return hasDisability; }

    public Boolean setDisability(Boolean result) { return hasDisability = result; }

    public Boolean hasMotorbike() { return hasMotorbike; }

    public Boolean setMotorbike(Boolean result) { return hasMotorbike = result; }
}
