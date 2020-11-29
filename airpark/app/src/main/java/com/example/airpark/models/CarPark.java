package com.example.airpark.models;

public class CarPark {

    private int carparkID;
    private String carparkName, carparkType;
    private double distanceFromAirport, price;

    public CarPark(int carparkID, String carparkType, String carparkName, double distanceFromAirport, double price){
        this.carparkID = carparkID;
        this.carparkName = carparkName;
        this.carparkType = carparkType;
        this.distanceFromAirport = distanceFromAirport;
        this.price = price;
    }

    public int getCarparkID(){ return carparkID; }

    public  String getCarparkName(){ return carparkName; }

    public  String getCarparkType(){ return carparkType; }

    public double getDistanceFromAirport(){ return distanceFromAirport; }

    public double getPrice(){ return price; }

    public boolean isFull(int availableSpaces){
        if(availableSpaces > 0){
            return false;
        }
        return true;
    }

    public String toString(){
        return carparkType + " - " + distanceFromAirport + "m from Airport";}
}
