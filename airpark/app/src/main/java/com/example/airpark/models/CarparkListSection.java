package com.example.airpark.models;

import java.util.List;

/**
 * Airpark Application - Group 14
 *
 * CS4125 -> System Analysis & Design
 * CS5721 -> Software Design
 *
 * List of car parks
 */
public class CarparkListSection {
    private String sectionTitle;
    private List<CarPark> carparkItems;

    public CarparkListSection(String sectionTitle, List<CarPark> carparkItems){
        this.sectionTitle = sectionTitle;
        this.carparkItems = carparkItems;
    }

    public String getSectionTitle(){
        return sectionTitle;
    }

    public List<CarPark> getItemsInSection(){
        return carparkItems;
    }
}
