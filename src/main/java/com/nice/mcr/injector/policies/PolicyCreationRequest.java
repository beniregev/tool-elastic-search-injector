package com.nice.mcr.injector.policies;

public class PolicyCreationRequest {

    private int timeToRun;
    private int numOfInteractionsInSec;
    private int steadyPacePerSec;
    private int maxInteractions;
    private int maxOutOfSpikeTime;

    public int getNumOfInteractionsInSec() {
        return numOfInteractionsInSec;
    }

    public int getTimeToRun() {
        return timeToRun;
    }

    public int getSteadyPacePerSec() {
        return steadyPacePerSec;
    }

    public int getMaxInteractions() {
        return maxInteractions;
    }

    public int getMaxOutOfSpikeTime() {
        return maxOutOfSpikeTime;
    }

    public void setNumOfInteractionsInSec(int numOfInteractionsInSec) {
        this.numOfInteractionsInSec = numOfInteractionsInSec;
    }

    public void setSteadyPacePerSec(int steadyPacePerSec) {
        this.steadyPacePerSec = steadyPacePerSec;
    }

    public void setTimeToRun(int timeToRun) {
        this.timeToRun = timeToRun;
    }

    public void setMaxInteractions(int maxInteractions) {
        this.maxInteractions = maxInteractions;
    }

    public void setMaxOutOfSpikeTime(int maxOutOfSpikeTime) {
        this.maxOutOfSpikeTime = maxOutOfSpikeTime;
    }
}
