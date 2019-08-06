package com.nice.mcr.injector.policies;

import com.nice.mcr.injector.MainCli;
import com.nice.mcr.injector.output.OutputHandler;
import com.nice.mcr.injector.service.DataGeneratorImpl;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

/**
 * An implementation for TimerTask to run inside the Timer defined.
 */
public class UpdateHandlers extends TimerTask {

    private int counter = 0;
    private List<OutputHandler> outputHandlers;
    private int callsPerSec = 0;
    private int overallSegments = 0;
    private CreateData createData;

    public UpdateHandlers(List<OutputHandler> outputHandlers) {
        this.outputHandlers = outputHandlers;
    }

    public List<OutputHandler> getOutputHandler() {
        return outputHandlers;
    }

    public void setOverallSegments(int overallSegments) {
        this.overallSegments = overallSegments;
    }

    public void setCreateData(CreateData createData) {
        this.createData = createData;
        this.overallSegments = this.createData.getOverallBulks();
    }

    public void setCallsPerSec(int callsPerSec) {
        this.callsPerSec = callsPerSec;
    }

    /**
     * The function creates a new json using the DataGenerator class and updates it's listeners.
     */
    public void run() {
        List<String> readySegmentsList = this.createData.getSegmentsList();
        // when it should be the last iteration of the task
        if ((this.counter + this.callsPerSec) >= this.overallSegments) {
            this.callsPerSec = this.overallSegments - this.counter;
        }
        for (int i = 0; i < this.callsPerSec; i++) {
            for (OutputHandler oh : this.outputHandlers) {
                if (SteadyPolicy.isRun) {
                    MainCli.beenCreated++;
                    oh.output(readySegmentsList.get(counter + i));
                } else {
                    this.cancel();
                }
            }
        }
        this.counter += this.callsPerSec;
    }

//        double startTime = System.currentTimeMillis();
//        String json = this.dataGenerator.createData(this.numOfSegments);
//        this.counter++;
//        MainCli.beenCreated++;
//        if (this.counter == this.overallSegments) {
//            this.cancel();
//            synchronized (this.t) {
//                try {
//                    t.notify();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        for (OutputHandler oh : this.outputHandlers) {
//            if (SteadyPolicy.isRun) {
//                oh.output(json);
//            } else {
//                this.cancel();
//            }
//        }
//        System.out.println("Time to generate segment and write to file: " + (System.currentTimeMillis() - startTime));

    public int getCounter() {
        return counter;
    }
}
