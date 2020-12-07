package com.example.airpark.designPatterns.factory;

import com.example.airpark.models.Car;
import com.example.airpark.models.Motorbike;
import com.example.airpark.models.Vehicle;

public class VehicleFactory {

    public Vehicle getVehicle(CarParkSpaceFactory.SpaceType spaceType){
        if(spaceType == null){
            return null;
        }

        if (spaceType == CarParkSpaceFactory.SpaceType.GENERAL || spaceType == CarParkSpaceFactory.SpaceType.DISABLED){
            return new Car();
        }else if(spaceType == CarParkSpaceFactory.SpaceType.TWO_WHEELER){
            return new Motorbike();
        }

        return null;
    }
}
