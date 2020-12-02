package com.example.airpark.models;

public class Motorbike implements Vehicle{

    private String motorbikeReg;
    private int ticketID;

    public Motorbike(){
        motorbikeReg = null;
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
    public int getTicketID() {
        return 0;
    }
}
