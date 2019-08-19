package com.nice.mcr.injector.policies;

import com.nice.mcr.injector.MainCli;
import com.nice.mcr.injector.output.RabbitMQOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;

public class SteadyPolicy implements Policy {

    private static final Logger log = LoggerFactory.getLogger(SteadyPolicy.class);

    public static boolean isRun = true;
    private UpdateHandlers updateHandlers;
    private int timeToRun;
    private int callsPerSec;
    private int overallSegments;
    private boolean isInOtherThread;
    private Runnable r = () -> {
        CreateData createData = new CreateData(this.overallSegments, Thread.currentThread());
        if (this.isInOtherThread) {
            createData.create(true);
        }
        else {
            createData.create(false);
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ie) {
            log.debug("", ie);
        }
        double startTime = System.currentTimeMillis();
        this.updateHandlers.setCreateData(createData);
        this.updateHandlers.setCallsPerSec(this.callsPerSec);
        this.updateHandlers.setOverallSegments(this.overallSegments);
        Timer timer = new Timer();
        // Define interval time
        // Set timer schedule
        timer.scheduleAtFixedRate(this.updateHandlers, 0, 1000);
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

    public SteadyPolicy(UpdateHandlers updateHandlers, int timeToRun, int callsPerSec, boolean isInOtherThread) {
        this.updateHandlers = updateHandlers;
        this.timeToRun = timeToRun;
        this.callsPerSec = callsPerSec;
        this.isInOtherThread = isInOtherThread;
        this.overallSegments = timeToRun * callsPerSec;
        MainCli.shouldCreated += timeToRun * callsPerSec;
    }

    public SteadyPolicy(boolean isInOtherThread, UpdateHandlers updateHandlers, int callsPerSec, int overallSegments) {
        this.updateHandlers = updateHandlers;
        this.overallSegments = overallSegments;
        this.callsPerSec = callsPerSec;
        this.isInOtherThread = isInOtherThread;
        this.timeToRun = (int) (Math.ceil((double) this.overallSegments / (double) this.callsPerSec));
        MainCli.shouldCreated += this.overallSegments;
    }

    public UpdateHandlers getUpdateHandlers() {
        return updateHandlers;
    }

    public void run() {
        if (this.isInOtherThread) {
            new Thread(this.r).start();
        } else {
            this.r.run();
        }
    }
}
