package com.nice.mcr.injector.controller.request;

import java.io.Serializable;

public class DemoCreationRequest implements Serializable {

    private static final long serialVersionUID = 1418032757339869048L;

    private int bulkSize;

    private int numOfInteractions;

    public int getBulkSize() {
        return bulkSize;
    }

    public void setBulkSize(int myVal) {
        this.bulkSize = myVal;
    }

    public int getNumOfInteractions() {
        return numOfInteractions;
    }

    public void setNumOfInteractions(int myVal) {
        this.numOfInteractions = myVal;
    }

}