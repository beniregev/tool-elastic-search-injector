package com.nice.mcr.injector.policies;

import com.nice.mcr.injector.linkedout.DataListener;
import com.nice.mcr.injector.service.DataGenerator;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class SteadyPolicy implements Policies {

    private UpdateListeners updateListeners;
    private int timeToRun;
    private int numOfSegmentsPerSec;
    private boolean isInOtherThread;
    private Runnable r;

    public SteadyPolicy(UpdateListeners updateListeners, int timeToRun, int numOfSegmentsPerSec, boolean isInOtherThread) {
        this.updateListeners = updateListeners;
        this.timeToRun = timeToRun;
        this.numOfSegmentsPerSec = numOfSegmentsPerSec;
        this.isInOtherThread = isInOtherThread;
        this.r = () -> {
            Timer timer = new Timer();
            // Define interval time
            // Set timer schedule
            timer.schedule(this.updateListeners, 0, 1000 / numOfSegmentsPerSec);
            // Loop that delays the main thread from stopping before it's time.
            // *** check for better solution ***
            try {
                Thread.sleep(timeToRun * 1000);
                timer.cancel();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

    public UpdateListeners getUpdateListeners() {
        return updateListeners;
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
