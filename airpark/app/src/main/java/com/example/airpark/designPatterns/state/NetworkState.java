package com.example.airpark.designPatterns.state;

public interface NetworkState {

    void doAction(NetworkConnection context);
    public boolean isConnected();
}
