package com.example.airpark.models;

/**
 * Airpark Application - Group 14
 *
 * CS4125 -> System Analysis & Design
 * CS5721 -> Software Design
 *
 * An airport car park
 */
public class CarPark {

    private int carparkID, spacesAvailable;
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
     * @param spacesAvailable
     */
    public CarPark(int carparkID, String carparkType, String carparkName, double distanceFromAirport, double price, int spacesAvailable){
        this.carparkID = carparkID;
        this.carparkName = carparkName;
        this.carparkType = carparkType;
        this.distanceFromAirport = distanceFromAirport;
        this.price = price;
        this.spacesAvailable = spacesAvailable;
    }

    public int getCarparkID(){ return carparkID; }

    public  String getCarparkName(){ return carparkName; }

    public  String getCarparkType(){ return carparkType; }

    public double getDistanceFromAirport(){ return distanceFromAirport; }

    public double getPrice(){ return price; }

    public boolean isFull(){
        if(spacesAvailable > 0){
            return false;
        }
        return true;
    }

    public String toString(){
        return carparkType + " - " + distanceFromAirport + "m from Airport";}
}
