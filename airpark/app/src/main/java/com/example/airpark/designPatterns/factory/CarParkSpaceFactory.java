package com.example.airpark.designPatterns.factory;

import com.example.airpark.models.CarParkSpace;
import com.example.airpark.models.DisabledSpace;
import com.example.airpark.models.GeneralSpace;
import com.example.airpark.models.MotorbikeSpace;

public class CarParkSpaceFactory {

    /** Hardcoded new space objects **/

    public CarParkSpace getSpace(String spaceType){
        if(spaceType == null){
            return null;
        }
        if(spaceType.equalsIgnoreCase("GENERAL")){
            return new GeneralSpace(null);

        } else if(spaceType.equalsIgnoreCase("DISABILITY")){
            return new DisabledSpace( null);

        } else if(spaceType.equalsIgnoreCase("MOTORBIKE")){
            return new MotorbikeSpace( null);
        }

        return null;
    }
}
