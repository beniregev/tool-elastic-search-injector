package com.nice.mcr.injector.policies;

import com.nice.mcr.injector.MainCli;

public class ElasticPolicy implements Policy {
    private int numOfBulks;
    private int segmentsInBulk;
    private UpdateOutputHandlers updateOutputHandlers;
    private Runnable r;

    public ElasticPolicy(UpdateOutputHandlers updateOutputHandlers, int numOfBulks, int segmentsInBulk) {
        this.updateOutputHandlers = updateOutputHandlers;
        this.numOfBulks = numOfBulks;
        this.segmentsInBulk = segmentsInBulk;
        this.r = () -> {
            double startTime = System.currentTimeMillis();
            DataCreator dataCreator = new DataCreator(this.numOfBulks, Thread.currentThread(), this.segmentsInBulk);
            dataCreator.create(false);
            this.updateOutputHandlers.setDataCreator(dataCreator);
            this.updateOutputHandlers.setCallsPerSec(this.numOfBulks);
            this.updateOutputHandlers.setOverallSegments(this.numOfBulks);
            this.updateOutputHandlers.run();
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