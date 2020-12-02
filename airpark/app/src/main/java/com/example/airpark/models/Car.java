package com.example.airpark.models;

public class Car implements Vehicle {

    private String carReg;
    private int ticketID;

    public Car(){
        carReg = null;
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
    public int getTicketID() {
        return 0;
    }
}
