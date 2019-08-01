package com.nice.mcr.injector.policies;

public class BacklogPolicy implements Policy {

    private UpdateHandlers updateHandlers;
    private int numOfSegments;
    private boolean isInOtherThread;
    private Runnable r;

    public BacklogPolicy(UpdateHandlers updateHandlers, int numOfSegments, boolean isInOtherThread) {
        this.updateHandlers = updateHandlers;
        this.numOfSegments = numOfSegments;
        this.isInOtherThread = isInOtherThread;
        this.r = () -> {
            for (int i = 0; i < this.numOfSegments; i++) {
                this.updateHandlers.run();
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