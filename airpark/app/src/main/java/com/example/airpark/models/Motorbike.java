package com.example.airpark.models;

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
