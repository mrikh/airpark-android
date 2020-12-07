package com.example.airpark.models;

public abstract class CarParkSpace {

    private Vehicle vehicle;

    CarParkSpace(Vehicle vehicle){
        this.vehicle = vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}