package com.nice.mcr.injector.policies;

import com.nice.mcr.injector.MainCli;

public class BacklogPolicy implements Policy {

    private UpdateHandlers updateHandlers;
    private int overallSegments;
    private boolean isInOtherThread;
    private Runnable r;

    public BacklogPolicy(UpdateHandlers updateHandlers, int overallSegments, boolean isInOtherThread) {
        this.updateHandlers = updateHandlers;
        this.overallSegments = overallSegments;
        this.isInOtherThread = isInOtherThread;
        System.out.println("entering backlog policy, number of segments to create: " + this.overallSegments);
        MainCli.shouldCreated += overallSegments;
        this.r = () -> {
            double startTime = System.currentTimeMillis();
            CreateData createData = new CreateData(this.overallSegments, Thread.currentThread());
            createData.create(false);
            this.updateHandlers.setCreateData(createData);
            this.updateHandlers.setCallsPerSec(this.overallSegments);
            this.updateHandlers.setOverallSegments(this.overallSegments);
            this.updateHandlers.run();
            System.out.println("Total run time of this backlog: " + (System.currentTimeMillis() - startTime));
            System.out.println("number of segments should be created: " + MainCli.shouldCreated);
            System.out.println("number of segments been created: " + MainCli.beenCreated);
        };
    }

    @Override
    public void run() {
        if (this.isInOtherThread) {
            new Thread(this.r).start();
        } else {
            this.r.run();
        }
    }
}