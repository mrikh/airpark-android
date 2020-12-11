package com.example.airpark.designPatterns.state;

public class Disconnected implements NetworkState{

    public boolean isConnected(){
        return false;
    }

    public void doAction(NetworkConnection context) {
        context.setState(this);
    }

    public String toString(){
        return "Not connected to the Internet!";
    }
}
