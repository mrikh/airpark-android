package com.example.airpark.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Airpark Application - Group 14
 *
 * CS4125 -> System Analysis & Design
 * CS5721 -> Software Design
 *
 * Calculates the price of car park
 */
public class Price {
    private double carparkPrice, carWashPrice;

    /**
     * Constructs Price Object
     * @param carparkPrice
     */
    public Price (double carparkPrice){
        this.carparkPrice = carparkPrice;
    }

    /**
     * Method that calculates the car park prices depending on time stayed
     *
     * @param entryTime
     * @param exitTime
     * @param entryDate
     * @param exitDate
     * @param type
     * @return double fullPrice
     * @throws ParseException
     */
    public double calculatePrice(String entryTime, String exitTime, String entryDate, String exitDate, String type) throws ParseException {
        int lengthOfStay = getLengthOfStay(entryTime,exitTime,entryDate,exitDate);
        double fullPrice = 0;

        //Long Term = price per day
        if(type.equals("Long Term")){
            if(lengthOfStay < 24){
                lengthOfStay = 24;
            }
            fullPrice = (lengthOfStay/24) * carparkPrice;
        }else{
            //Short Term = price per hour
            fullPrice = lengthOfStay * carparkPrice;
        }

        return fullPrice;
    }

    /**
     * Calculate the length of time required in hours
     * @param entryTime
     * @param exitTime
     * @param entryDate
     * @param exitDate
     * @return int noOfHours
     * @throws ParseException
     */
    private int getLengthOfStay(String entryTime, String exitTime, String entryDate, String exitDate) throws ParseException {
        long noOfHours=0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        if(entryTime.equals("23:59") || exitTime.equals("23:59")){
            if(entryTime.equals("23:59")){
                entryTime = "24:00";
            }
            if(exitTime.equals("23:59")){
                exitTime = "24:00";
            }
        }

        Date entry = sdf.parse(entryDate + " " + entryTime);
        Date exit = sdf.parse(exitDate + " " + exitTime);

        noOfHours = ((exit.getTime()-entry.getTime())/(1000*60*60));

        return (int)noOfHours;
    }

    public double discountPrice(double currentPrice, double percentage){
        double decimal = percentage/100;
        double price = currentPrice - (decimal * currentPrice);

        return price;
    }
}
