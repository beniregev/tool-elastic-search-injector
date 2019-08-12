package com.nice.mcr.injector.output;

public interface OutputHandler {

    public boolean open();
    public void output(String data);
}
