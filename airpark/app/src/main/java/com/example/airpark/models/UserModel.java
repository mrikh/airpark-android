package com.example.airpark.models;

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
