package com.example.airpark.models;

public class Price {
    private double carparkPrice;
//    private int shortTermLength, longTermLength;

    public Price (double carparkPrice){
        this.carparkPrice = carparkPrice;
    }

    public double calculatePrice(int entryTime, int exitTime, String entryDate, String exitDate, String type){
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

    private int getLengthOfStay(int entryTime, int exitTime, String entryDate, String exitDate){
        int noOfHours = 0, noOfDays=0, entry=0, exit=0;

        if(entryDate.equals(exitDate)){
            noOfHours = exitTime - entryTime;
        }else{
            entry = Integer.parseInt(entryDate.substring(0, entryDate.indexOf("/")));
            exit = Integer.parseInt(exitDate.substring(0, exitDate.indexOf("/")));
            noOfDays = exit - entry; //2

            noOfHours = 24 * noOfDays;
            if(exitTime > 12){
                //If exit time is past 12pm, charge is into next day
                noOfHours += 24;
            }
        }

        return noOfHours;
    }

}
