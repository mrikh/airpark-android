package com.example.airpark.models;
import java.io.Serializable;

public abstract class CarParkSpace implements Serializable {

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