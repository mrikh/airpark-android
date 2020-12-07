package com.example.airpark.models;
import java.io.Serializable;

public interface Vehicle extends Serializable {

    String getVehicleReg();
    void setVehicleReg(String reg);
    String getTicketID();
    void setTicketID(String ticketID);
}
