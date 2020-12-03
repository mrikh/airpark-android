package com.example.airpark.models;

public abstract class CarParkSpace {
    private int spaceID, availableSpaces;
    private Vehicle vehicle;

    CarParkSpace(int spaceID, int availableSpaces, Vehicle vehicle){
        this.spaceID = spaceID;
        this.availableSpaces = availableSpaces;
        this.vehicle = vehicle;
    }

    public int getSpaceID() { return spaceID; }

    public int getNoOfSpaces() {
        return availableSpaces;
    }

    public void removeSpace() {
        availableSpaces = availableSpaces - 1;
    }

    public void addSpace() {
        availableSpaces = availableSpaces + 1;
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

    public boolean isFree() {
        if(vehicle == null){
            return true;
        }
        return false;
    }
}