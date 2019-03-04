package com.nice.mcr.injector.controller.request;

import java.io.Serializable;

public class DemoCreationRequest implements Serializable {

    private static final long serialVersionUID = 1418032757339869048L;

    private String bulkSize;

    private String numOfInteractios;

    public String getBulkSize() {
        return bulkSize;
    }

    public void setBulkSize(String myVal) {
        this.bulkSize = bulkSize;
    }

    public String getNumOfInteractios() {
        return bulkSize;
    }

    public void setNumOfInteractios(String myVal) {
        this.numOfInteractios = bulkSize;
    }

}