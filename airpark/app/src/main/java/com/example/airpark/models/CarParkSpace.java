package com.example.airpark.models;

public class CarParkSpace {
    private int spaceID, availableSpaces;
    private String vehicleReg;

    public CarParkSpace(int spaceID, int availableSpaces, String vehicleReg){
        this.spaceID = spaceID;
        this.availableSpaces = availableSpaces;
        this.vehicleReg = vehicleReg;
    }

    public int getSpaceID() {
        return spaceID;
    }

    public int getNoOfSpaces() {
        return availableSpaces;
    }

    public void removeSpace() {
        availableSpaces = availableSpaces - 1;
    }

    public void addSpace() {
        availableSpaces = availableSpaces + 1;
    }

    public void addVehicle(String reg) {
        if(vehicleReg == "Null"){
            vehicleReg = reg;
        }else{
            System.out.println("Space is Full");
        }
    }

    public void removeVehicle(String reg) {
        if(vehicleReg != "Null"){
            vehicleReg = "Null";
        }else{
            System.out.println("No Vehicle in Space");
        }
    }

    public boolean isFree() {
        if(vehicleReg == "Null"){
            return true;
        }
        return false;
    }
}