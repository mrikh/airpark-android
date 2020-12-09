package com.example.airpark.models;

/**
 * Airpark Application - Group 14
 *
 * CS4125 -> System Analysis & Design
 * CS5721 -> Software Design
 *
 * A motorbike vehicle
 */
public class Motorbike implements Vehicle{

    private String motorbikeReg, ticketID;

    public Motorbike(){
        motorbikeReg = null;
        ticketID = null;
    }

    @Override
    public String getVehicleReg() {
        return this.motorbikeReg;
    }

    @Override
    public void setVehicleReg(String reg) {
        this.motorbikeReg = reg;
    }

    @Override
    public String getTicketID() { return ticketID; }

    @Override
    public void setTicketID(String ticketID) { this.ticketID = ticketID; }
}
