package com.example.airpark.models;

public class Car implements Vehicle {

    private String carReg, ticketID;

    public Car(){
        carReg = null;
        ticketID = null;
    }

    @Override
    public String getVehicleReg() {
        return this.carReg;
    }

    @Override
    public void setVehicleReg(String reg) {
        this.carReg = reg;
    }

    @Override
    public String getTicketID() { return ticketID; }

    @Override
    public void setTicketID(String ticketID) { this.ticketID = ticketID; }

}
