package com.example.airpark.designPatterns.state;

public class NetworkConnection {

    private NetworkState network;

    public NetworkConnection(){
        network = null;
    }

    public void setState(NetworkState network){
        this.network = network;
    }

    public NetworkState getState() {
        return network;
    }
}
