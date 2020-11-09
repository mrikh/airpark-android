package com.example.airpark.models;

public class DisabledSpace implements CarParkSpace {

    private int spaceID, availableSpaces;
    private String vehicleReg;

    public DisabledSpace(int spaceID, int availableSpaces, String vehicleReg){
        this.spaceID = spaceID;
        this.availableSpaces = availableSpaces;
        this.vehicleReg = vehicleReg;
    }

    @Override
    public int getSpaceID() {
        return spaceID;
    }

    @Override
    public int getNoOfSpaces() {
        return availableSpaces;
    }

    @Override
    public void removeSpace() {
        availableSpaces = availableSpaces - 1;
    }

    @Override
    public void addSpace() {
        availableSpaces = availableSpaces + 1;
    }

    @Override
    public void addVehicle(String reg) {
        if(vehicleReg == "Null"){
            vehicleReg = reg;
        }else{
            System.out.println("Space is Full");
        }
    }

    @Override
    public void removeVehicle(String reg) {
        if(vehicleReg != "Null"){
            vehicleReg = "Null";
        }else{
            System.out.println("No Vehicle in Space");
        }
    }

    @Override
    public boolean isFree() {
        if(vehicleReg == "Null"){
            return true;
        }
        return false;
    }
}
