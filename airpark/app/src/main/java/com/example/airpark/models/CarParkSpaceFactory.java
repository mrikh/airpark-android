package com.example.airpark.models;

public class CarParkSpaceFactory {

    /** Hardcoded new space objects **/

    public CarParkSpace getSpace(String spaceType){
        if(spaceType == null){
            return null;
        }
        if(spaceType.equalsIgnoreCase("GENERAL")){
            return new GeneralSpace(2, 10, null);

        } else if(spaceType.equalsIgnoreCase("DISABLED")){
            return new DisabledSpace(1, 5, null);

        } else if(spaceType.equalsIgnoreCase("MOTORBIKE")){
            return new MotorbikeSpace(3, 2, null);
        }

        return null;
    }
}
