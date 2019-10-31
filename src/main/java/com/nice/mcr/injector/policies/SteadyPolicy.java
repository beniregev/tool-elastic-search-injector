package com.nice.mcr.injector.policies;

import com.nice.mcr.injector.MainCli;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;

public class SteadyPolicy implements Policy {

    private static final Logger log = LoggerFactory.getLogger(SteadyPolicy.class);

    public static boolean isRun = true;
    private UpdateOutputHandlers updateOutputHandlers;
    private int timeToRun;
    private int callsPerSec;
    private int overallSegments;
    private boolean runInSeparateThread;
    private Runnable r = () -> {
        DataCreator dataCreator = new DataCreator(this.overallSegments, Thread.currentThread(), callsPerSec);
        if (this.runInSeparateThread) {
            dataCreator.create(true);
        }
        else {
            dataCreator.create(false);
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ie) {
            log.debug("", ie);
        }
        double startTime = System.currentTimeMillis();
        this.updateOutputHandlers.setDataCreator(dataCreator);
        this.updateOutputHandlers.setCallsPerSec(callsPerSec);
        this.updateOutputHandlers.setOverallSegments(overallSegments);
        Timer timer = new Timer();
        // Define interval time
        // Set timer schedule
        timer.scheduleAtFixedRate(this.updateOutputHandlers, 0, 1000);
        try {
            Thread.sleep(this.timeToRun * 1000);
            isRun = false;
            timer.cancel();
            System.out.println("Total run time of this steady: " + (System.currentTimeMillis() - startTime));
            System.out.println("number of segments should be created: " + MainCli.shouldCreated);
            System.out.println("number of segments been created: " + MainCli.beenCreated);
        } catch (InterruptedException ie) {
            log.debug("", ie);
        }
    };

    public SteadyPolicy(UpdateOutputHandlers updateOutputHandlers, int timeToRun, int callsPerSec, boolean runInSeparateThread) {
        this.updateOutputHandlers = updateOutputHandlers;
        this.timeToRun = timeToRun;
        this.callsPerSec = callsPerSec;
        this.runInSeparateThread = runInSeparateThread;
        this.overallSegments = timeToRun * callsPerSec;
        MainCli.shouldCreated += timeToRun * callsPerSec;
    }

    public SteadyPolicy(boolean runInSeparateThread, UpdateOutputHandlers updateOutputHandlers, int callsPerSec, int overallSegments) {
        this.updateOutputHandlers = updateOutputHandlers;
        this.overallSegments = overallSegments;
        this.callsPerSec = callsPerSec;
        this.runInSeparateThread = runInSeparateThread;
        this.timeToRun = (int) (Math.ceil((double) this.overallSegments / (double) this.callsPerSec));
        MainCli.shouldCreated += this.overallSegments;
    }

    public UpdateOutputHandlers getUpdateOutputHandlers() {
        return updateOutputHandlers;
    }

    public void run() {
        if (this.runInSeparateThread) {
            new Thread(this.r, "SteadyPolicyThread").start();
        } else {
            this.r.run();
        }
    }
}
