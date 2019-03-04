package com.nice.mcr.injector.controller.response;

import java.io.Serializable;

public class DemoResponse implements Serializable {

    private static final long serialVersionUID = 4017419524163670670L;

    private String myRetVal;


    public String getMyRetVal() {
        return myRetVal;
    }

    public void setMyRetVal(String myRetVal) {
            this.myRetVal = myRetVal;
        }

}