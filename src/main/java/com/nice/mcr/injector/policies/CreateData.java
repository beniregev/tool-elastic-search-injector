package com.nice.mcr.injector.policies;

import com.nice.mcr.injector.service.DataGeneratorImpl;

import java.util.ArrayList;
import java.util.List;

public class CreateData {
    private int overallBulks;
    private List<String> segmentsList;
    private Thread t;
    private int numOfSegmentsInBulk = 1;
    private Runnable r = () -> {
        double startTime = System.currentTimeMillis();
        DataGeneratorImpl dataGenerator = new DataGeneratorImpl();
        while (this.segmentsList.size() < this.overallBulks) {
            segmentsList.add(dataGenerator.createData(this.numOfSegmentsInBulk));
        }
        if ((System.currentTimeMillis() - startTime) < 5000) {
            this.t.interrupt();
        }
    };
    

    public CreateData(int overallBulks, Thread t) {
        this.overallBulks = overallBulks;
        this.segmentsList = new ArrayList<>();
        this.t = t;
    }
    
    public CreateData(int overallBulks, Thread t, int numOfSegmentsInBulk) {
        this.segmentsList = new ArrayList<>();
        this.overallBulks = overallBulks;
        this.t = t;
        this.numOfSegmentsInBulk = numOfSegmentsInBulk;
    }

    public void create(boolean isOtherTread) {
        if (isOtherTread) {
            new Thread(this.r).start();
        }
        else {
            this.r.run();
        }
    }

    public int getOverallBulks() {
        return overallBulks;
    }

    public List<String> getSegmentsList() {
        return segmentsList;
    }
}
