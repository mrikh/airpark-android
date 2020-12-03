package com.example.airpark.models;

import java.util.List;

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
