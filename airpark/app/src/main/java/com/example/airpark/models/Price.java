package com.example.airpark.models;

public class Price {
    private double carparkPrice;
//    private int shortTermLength, longTermLength;

    public Price (double carparkPrice){
        this.carparkPrice = carparkPrice;
    }

    public double calculatePrice(String entryTime, String exitTime, String entryDate, String exitDate, String type){
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

    private int getLengthOfStay(String entryTime, String exitTime, String entryDate, String exitDate){
        int noOfHours=0, noOfDays=0, entryDay=0, exitDay=0, entryHour=0, exitHour=0;

        if(entryTime.equals("23:59") || exitTime.equals("23:59")){
            if(entryTime.equals("23:59")){
                entryTime = "24:00";
            }
            if(exitTime.equals("23:59")){
                exitTime = "24:00";
            }
        }
        entryHour = Integer.parseInt(entryTime.substring(0, entryTime.indexOf(":")));
        exitHour = Integer.parseInt(exitTime.substring(0, exitTime.indexOf(":")));

        if(entryDate.equals(exitDate)){
            noOfHours = exitHour - entryHour;
        }else{
            entryDay = Integer.parseInt(entryDate.substring(0, entryDate.indexOf("/")));
            exitDay = Integer.parseInt(exitDate.substring(0, exitDate.indexOf("/")));
            noOfDays = exitDay - entryDay;

            noOfHours = 24 * noOfDays;
            if(exitHour > 12){
                //If exit time is past 12pm, charge is into next day
                noOfHours += 24;
            }
        }

        return noOfHours;
    }

}
