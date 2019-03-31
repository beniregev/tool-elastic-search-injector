package com.nice.mcr.injector.controller.request;

import java.io.Serializable;

public class DemoCreationRequest implements Serializable {

    private static final long serialVersionUID = 1418032757339869048L;

    private int numberOfBulks;

    private int numOfInteractions;

    public int getNumberOfBulks() {
        return numberOfBulks;
    }

    public void setNumberOfBulks(int myVal) {
        this.numberOfBulks = myVal;
    }

    public int getNumOfInteractions() {
        return numOfInteractions;
    }

    public void setNumOfInteractions(int myVal) {
        this.numOfInteractions = myVal;
    }

}