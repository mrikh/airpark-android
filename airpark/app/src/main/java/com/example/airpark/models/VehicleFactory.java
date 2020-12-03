package com.example.airpark.models;

public class VehicleFactory {

    public Vehicle getVehicle(String vehicleType){
        if(vehicleType == null){
            return null;
        }
        if(vehicleType.equals("GENERAL") || vehicleType.equals("DISABLED")){
            vehicleType = "CAR";
        }
        if(vehicleType.equalsIgnoreCase("CAR")){
            return new Car();

        } else if(vehicleType.equalsIgnoreCase("MOTORBIKE")){
            return new Motorbike();
        }

        return null;
    }
}
