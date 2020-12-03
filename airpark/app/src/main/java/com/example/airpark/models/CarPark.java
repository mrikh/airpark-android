package com.example.airpark.models;

import java.io.Serializable;

/**
 * Airpark Application - Group 14
 *
 * CS4125 -> System Analysis & Design
 * CS5721 -> Software Design
 *
 * An airport car park
 */
public class CarPark implements Serializable {

    private int carparkID, availableSpaces;
    private String carparkName, carparkType;
    private double distanceFromAirport, price;

    /**
     * Constructs Car Park Object
     *
     * @param carparkID
     * @param carparkType
     * @param carparkName
     * @param distanceFromAirport
     * @param price
     */
    public CarPark(int carparkID, String carparkType, String carparkName, double distanceFromAirport, double price, int availableSpaces){
        this.carparkID = carparkID;
        this.carparkName = carparkName;
        this.carparkType = carparkType;
        this.distanceFromAirport = distanceFromAirport;
        this.price = price;
        this.availableSpaces = availableSpaces;

    }

    public int getCarparkID(){ return carparkID; }

    public  String getCarparkName(){ return carparkName; }

    public  String getCarparkType(){ return carparkType; }

    public double getDistanceFromAirport(){ return distanceFromAirport; }

    //Price per hour (short term) or day (long term)
    public double getPrice(){ return price; }

    public boolean isFull(){
        if(availableSpaces > 0){
            return false;
        }
        return true;
    }

    public String toString(){
        return carparkType + " - " + distanceFromAirport + "m from Airport";}
}
