package com.example.airpark.models;
import java.io.Serializable;

/**
 * Airpark Application - Group 14
 *
 * CS4125 -> System Analysis & Design
 * CS5721 -> Software Design
 *
 * Abstract class for car park spaces
 */
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