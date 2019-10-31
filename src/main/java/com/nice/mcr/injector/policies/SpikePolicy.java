package com.nice.mcr.injector.policies;

import com.nice.mcr.injector.MainCli;

import java.util.*;

public class SpikePolicy implements Policy {

    private UpdateOutputHandlers updateOutputHandlers;
    private int timeToRun;
    private int numOfSegmentsPerSec;
    private int maxSegments;
    private int steadyTime;

    public SpikePolicy(UpdateOutputHandlers updateOutputHandlers, int timeToRun, int numOfSegmentsPerSec, int steadyTime,
                       int maxSegments) {
        this.updateOutputHandlers = updateOutputHandlers;
        this.timeToRun = timeToRun;
        this.numOfSegmentsPerSec = numOfSegmentsPerSec;
        this.maxSegments = maxSegments;
        this.steadyTime = steadyTime;
    }

    @Override
    public void run() {
        new Thread(() -> {
            Random r = new Random();
            double startTime = System.currentTimeMillis();
            double endTime = timeToRun * 1000 + startTime;
            int numOfPressurePeaks;
            double tempSteadyTime;
            SteadyPolicy steadyPolicy;
            BacklogPolicy backlogPolicy;
            boolean stop = false;
            while (!stop) {
                // check if the steady policy is over time the run
                tempSteadyTime = this.steadyTime * 1000 + System.currentTimeMillis();
                if (tempSteadyTime > endTime) {
                    // changing the steady policy timing to the end of the run
                    tempSteadyTime = tempSteadyTime - endTime;
                    new SteadyPolicy(new UpdateOutputHandlers(this.updateOutputHandlers.getOutputHandlers()),
                            (int) (tempSteadyTime / 1000), this.numOfSegmentsPerSec, false).run();
                    System.out.println("entering steady policy, number of segments to create: " + ((this.steadyTime/1000)*this.numOfSegmentsPerSec));
                    break;
                }
                System.out.println("entering steady policy, number of segments to create: " + (this.steadyTime*this.numOfSegmentsPerSec));
                // creates SteadyPolicy instance as the timing defined and runs it
                steadyPolicy = new SteadyPolicy(new UpdateOutputHandlers(this.updateOutputHandlers.getOutputHandlers()),
                        this.steadyTime, this.numOfSegmentsPerSec,false);
                steadyPolicy.run();
                SteadyPolicy.isRun = true;
                numOfPressurePeaks = r.nextInt(10) + 1;
                // runs a random number of backlogs.
                // each backlogs randoms the number of jsons created
                for (int i = 0; i < numOfPressurePeaks; i++) {
                    // creates BacklogPolicy instance and runs it
                    backlogPolicy = new BacklogPolicy(new UpdateOutputHandlers(this.updateOutputHandlers.getOutputHandlers()),
                            r.nextInt(this.maxSegments), false);
                    backlogPolicy.run();
                    if (System.currentTimeMillis() > endTime) {
                        stop = true;
                        break;
                    }
                }
            }
            System.out.println("Total run time: " + (System.currentTimeMillis() - startTime));
            System.out.println("number of segments should be created: " + MainCli.shouldCreated);
            System.out.println("number of segments been created: " + MainCli.beenCreated);
        }).start();
    }
}
