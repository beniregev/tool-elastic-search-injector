package com.nice.mcr.injector.policies;

import com.nice.mcr.injector.MainCli;
import java.util.Timer;

public class BacklogPolicy implements Policy {

    private final static int CPS_CONST = 1000;
    private UpdateOutputHandlers updateOutputHandlers;
    private int overallSegments;
    private boolean runInSeparateThread;
    private Runnable runnable;

    public BacklogPolicy(UpdateOutputHandlers updateOutputHandlers, int overallSegments, boolean runInSeparateThread) {
        this.updateOutputHandlers = updateOutputHandlers;
        this.overallSegments = overallSegments;
        this.runInSeparateThread = runInSeparateThread;
        System.out.println("entering backlog policy, number of segments to create: " + this.overallSegments);
        MainCli.shouldCreated += overallSegments;
        this.runnable = () -> {
            double startTime = System.currentTimeMillis();
            DataCreator dataCreator = new DataCreator(this.overallSegments, Thread.currentThread(), CPS_CONST);
            dataCreator.create(true);
            this.updateOutputHandlers.setDataCreator(dataCreator);
            this.updateOutputHandlers.setCallsPerSec(CPS_CONST);
            this.updateOutputHandlers.setOverallSegments(this.overallSegments);
            // Make sure the data creation thread gets a 5 sec head start
            try {
                Thread.currentThread().sleep(5000);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            Timer timer = new Timer();
            // Define interval time
            // Set timer schedule
            timer.scheduleAtFixedRate(this.updateOutputHandlers, 0, 1000);
//            this.updateOutputHandlers.run();
            System.out.println("Total run time of this backlog: " + (System.currentTimeMillis() - startTime));
            System.out.println("number of segments should be created: " + MainCli.shouldCreated);
            System.out.println("number of segments been created: " + MainCli.beenCreated);
        };
    }

    @Override
    public void run() {
        if (this.runInSeparateThread) {
            new Thread(this.runnable, "BacklogPolicyThread").start();
        } else {
            this.runnable.run();
        }
    }
}