package com.nice.mcr.injector.policies;

public class BacklogPolicy implements Policies {

    private UpdateListeners updateListeners;
    private int numOfSegments;
    private boolean isInOtherThread;
    private Runnable r;

    public BacklogPolicy(UpdateListeners updateListeners, int numOfSegments, boolean isInOtherThread) {
        this.updateListeners = updateListeners;
        this.numOfSegments = numOfSegments;
        this.isInOtherThread = isInOtherThread;
        this.r = () -> {
            for (int i = 0; i < this.numOfSegments; i++) {
                updateListeners.run();
            }
        };
    }

    public void setNumOfSegments(int numOfSegments) {
        this.numOfSegments = numOfSegments;
    }

    @Override
    public void run() {
        if (this.isInOtherThread) {
            new Thread(this.r).start();
        }
        else {
            this.r.run();
        }
    }
}