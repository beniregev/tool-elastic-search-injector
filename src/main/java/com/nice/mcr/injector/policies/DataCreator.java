package com.nice.mcr.injector.policies;

import com.nice.mcr.injector.service.DataGeneratorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ariel Levi
 */
//  TODO Binyamin Regev -- re-engineering required. Separate to an abstract class or interface {@code DataCreator} and this class extends it
public class DataCreator implements Runnable {
    private long overallBulks;
    private List<String> segmentsList;
    private Thread invokingThread;
    private int numOfSegmentsInBulk = 1;
    private int cps = 1;
    private int availableSegments = 0;
    private long numGeneratedSegments = 0;
    private Object segmentLock = new Object();

    private static final Logger log = LoggerFactory.getLogger(DataCreator.class);

    public synchronized void run() {
        DataGeneratorImpl dataGenerator = new DataGeneratorImpl();
        while (numGeneratedSegments < overallBulks * numOfSegmentsInBulk) {
            while (segmentsList.size() > (cps * 60)) {
                try {
                    wait();
                }
                catch (InterruptedException e) {
                }
                log.info("Segments generated so far = " + numGeneratedSegments);
            }
            segmentsList.add(dataGenerator.createData(numOfSegmentsInBulk));
            numGeneratedSegments += numOfSegmentsInBulk;
        }
        log.info("Completed creating segments - " + numGeneratedSegments);
    };
    

    public DataCreator(int overallBulks, Thread invokingThread, int cps) {

        this(overallBulks, invokingThread, cps, 1);
    }
    
    public DataCreator(int overallBulks, Thread invokingThread, int cps, int numOfSegmentsInBulk) {
        this.segmentsList = new ArrayList<String>(overallBulks);
        this.overallBulks = overallBulks;
        this.invokingThread = invokingThread;
        this.numOfSegmentsInBulk = numOfSegmentsInBulk;
        this.cps = cps;
    }

    public void create(boolean runInSeparateThread) {
        if (runInSeparateThread) {
            new Thread(this, "CreateDataThread").start();
        }
        else {
            run();
        }
    }

    public long getOverallBulks() {
        return overallBulks;
    }

    public synchronized List<String> getSegmentsList() {
        return segmentsList;
    }

    public synchronized String getSegment() {
        if (segmentsList.size() > 0) {
            String segment = segmentsList.remove(0);
            if (segmentsList.size() < 10 * cps) {
                notify();
            }
            return segment;
        }
        else {
            log.error("No available segments to output");
            return null;
        }
    }

}
