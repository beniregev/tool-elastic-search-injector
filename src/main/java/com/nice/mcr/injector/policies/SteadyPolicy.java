package com.nice.mcr.injector.policies;

import java.util.Timer;

public class SteadyPolicy implements Policy {

    private UpdateHandlers updateHandlers;
    private int timeToRun;
    private int numOfSegmentsPerSec;
    private boolean isInOtherThread;
    private Runnable r;

    public SteadyPolicy(UpdateHandlers updateHandlers, int timeToRun, int numOfSegmentsPerSec, boolean isInOtherThread) {
        this.updateHandlers = updateHandlers;
        this.timeToRun = timeToRun;
        this.numOfSegmentsPerSec = numOfSegmentsPerSec;
        this.isInOtherThread = isInOtherThread;
        this.r = () -> {
            Timer timer = new Timer();
            // Define interval time
            // Set timer schedule
            timer.schedule(this.updateHandlers, 0, 1000 / this.numOfSegmentsPerSec);
            // Loop that delays the thread from stopping before it's time.
            try {
                Thread.sleep(this.timeToRun * 1000);
                timer.cancel();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

    public UpdateHandlers getUpdateHandlers() {
        return updateHandlers;
    }

    public void run() {
        if (this.isInOtherThread) {
            new Thread(this.r).start();
        }
        else {
            this.r.run();
        }
    }
}
