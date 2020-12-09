package com.example.airpark.models;

/**
 * Airpark Application - Group 14
 *
 * CS4125 -> System Analysis & Design
 * CS5721 -> Software Design
 *
 * A user of the app that has signed-up/logged in
 */
public class UserModel {

    public static UserModel currentUser;

    private int id;
    private String name;
    private String email;

    public UserModel(int id, String name, String email){

        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getEmail(){
        return this.email;
    }
}
