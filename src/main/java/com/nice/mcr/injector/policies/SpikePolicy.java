package com.nice.mcr.injector.policies;

import javafx.print.Collation;

import java.util.*;

public class SpikePolicy implements Policies {

    private UpdateListeners updateListeners;
    private int timeToRun;
    private int numOfSegmentsPerSec;
    private int maxSegments;
    private int steadyTime;
    private int maxNumberOfPeaks;

    public SpikePolicy(UpdateListeners updateListeners, int timeToRun, int numOfSegmentsPerSec, int steadyTime,
                       int maxSegments, int maxNumberOfPicks) {
        this.updateListeners = updateListeners;
        this.timeToRun = timeToRun;
        this.numOfSegmentsPerSec = numOfSegmentsPerSec;
        this.maxSegments = maxSegments;
        this.steadyTime = steadyTime;
        this.maxNumberOfPeaks = maxNumberOfPicks;
    }

    @Override
    public void run() {
        new Thread(()-> {
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
                    new SteadyPolicy(new UpdateListeners(this.updateListeners.getDataListeners()),
                            (int) (tempSteadyTime / 1000), this.numOfSegmentsPerSec, false).run();
                    break;
                }
                // creates SteadyPolicy instance as the timing defined and runs it
                steadyPolicy = new SteadyPolicy(new UpdateListeners(this.updateListeners.getDataListeners()),
                        this.steadyTime, this.numOfSegmentsPerSec, false);
                steadyPolicy.run();
                numOfPressurePeaks = r.nextInt(maxNumberOfPeaks) + 1;
                // runs a random number of backlogs.
                // each backlogs randoms the number of jsons created
                for (int i = 0; i < numOfPressurePeaks; i++) {
                    // creates BacklogPolicy instance and runs it
                    backlogPolicy = new BacklogPolicy(new UpdateListeners(this.updateListeners.getDataListeners()),
                            r.nextInt(this.maxSegments), false);
                    backlogPolicy.run();
                    if (System.currentTimeMillis() > endTime) {
                        stop = true;
                        break;
                    }
                }
            }
        }).start();
    }

//    private List<Integer> generatePeaksTime(int pressureTime) {
//        Random r = new Random();
//        int timeLeft = pressureTime;
//        int peakTime;
//        List<Integer> timeOfPeaks = new ArrayList<>();
//        while (timeLeft > 0) {
//            peakTime = r.nextInt(timeLeft) + 1;
//            timeOfPeaks.add(peakTime);
//            timeLeft -= peakTime;
//        }
//        Collections.shuffle(timeOfPeaks);
//        return timeOfPeaks;
//    }
}
