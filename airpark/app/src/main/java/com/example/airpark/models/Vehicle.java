package com.example.airpark.models;
import java.io.Serializable;

/**
 * Airpark Application - Group 14
 *
 * CS4125 -> System Analysis & Design
 * CS5721 -> Software Design
 *
 * Interface for vehicles
 */
public interface Vehicle extends Serializable {

    String getVehicleReg();
    void setVehicleReg(String reg);
    String getTicketID();
    void setTicketID(String ticketID);
}
