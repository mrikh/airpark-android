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
public class CalculatePrice {
    private double carparkPrice, carWashPrice;

    /**
     * Constructs Price Object
     * @param carparkPrice
     */
    public CalculatePrice (double carparkPrice){
        this.carparkPrice = carparkPrice;
    }

    public double discountPrice(double currentPrice, double percentage){
        double decimal = percentage/100;
        double price = currentPrice - (decimal * currentPrice);

        return price;
    }
}
