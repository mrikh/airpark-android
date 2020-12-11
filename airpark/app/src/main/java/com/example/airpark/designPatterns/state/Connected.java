package com.example.airpark.designPatterns.state;

import android.app.Activity;
import android.widget.Toast;

public class Connected implements NetworkState{

    public boolean isConnected(){
        return true;
    }

    public void doAction(NetworkConnection context) {
        context.setState(this);
    }

    public String toString(){
        return "Connected to the Internet!";
    }
}

