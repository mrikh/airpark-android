package com.example.airpark.models;

public abstract class CarParkSpace {

    private Vehicle vehicle;

    CarParkSpace(Vehicle vehicle){
        this.vehicle = vehicle;
    }

    public void addVehicle(Vehicle vehicle) {
        if(vehicle == null){
            this.vehicle = vehicle;
        }else{
            System.out.println("Space is Full");
        }
    }

    public void removeVehicle(Vehicle vehicle) {
        if(vehicle != null){
            this.vehicle = null;
        }else{
            System.out.println("No Vehicle in Space");
        }
    }
}