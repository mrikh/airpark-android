package com.example.airpark.designPatterns.factory;

import com.example.airpark.models.BookingTicket;
import com.example.airpark.models.Car;
import com.example.airpark.models.Motorbike;
import com.example.airpark.models.Vehicle;

public class VehicleFactory {

    public Vehicle getVehicle(BookingTicket.SpaceType spaceType){
        if(spaceType == null){
            return null;
        }

        if (spaceType == BookingTicket.SpaceType.GENERAL || spaceType == BookingTicket.SpaceType.DISABLED){
            return new Car();
        }else if(spaceType == BookingTicket.SpaceType.TWO_WHEELER){
            return new Motorbike();
        }

        return null;
    }
}
