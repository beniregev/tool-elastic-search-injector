package com.nice.mcr.injector.policies;

import com.nice.mcr.injector.MainCli;

public class ElasticPolicy implements Policy {
    private int numOfBulks;
    private int segmentsInBulk;
    private UpdateHandlers updateHandlers;
    private Runnable r;

    public ElasticPolicy(UpdateHandlers updateHandlers, int numOfBulks, int segmentsInBulk) {
        this.updateHandlers = updateHandlers;
        this.numOfBulks = numOfBulks;
        this.segmentsInBulk = segmentsInBulk;
        this.r = () -> {
            double startTime = System.currentTimeMillis();
            CreateData createData = new CreateData(this.numOfBulks, Thread.currentThread(), this.segmentsInBulk);
            createData.create(false);
            this.updateHandlers.setCreateData(createData);
            this.updateHandlers.setCallsPerSec(this.numOfBulks);
            this.updateHandlers.setOverallSegments(this.numOfBulks);
            this.updateHandlers.run();
            System.out.println("Total run time of this backlog: " + (System.currentTimeMillis() - startTime));
            System.out.println("number of segments should be created: " + MainCli.shouldCreated);
            System.out.println("number of segments been created: " + MainCli.beenCreated);
        };
    }
    @Override
    public void run() {
        this.r.run();
    }
}