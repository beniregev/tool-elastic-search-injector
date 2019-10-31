package com.nice.mcr.injector.policies;

import com.nice.mcr.injector.output.OutputHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.TimerTask;

/**
 * An implementation for TimerTask to run inside the Timer defined.
 */
public class UpdateOutputHandlers extends TimerTask {

    private static final Logger log = LoggerFactory.getLogger(UpdateOutputHandlers.class);

    private int counter = 0;
    private List<OutputHandler> outputHandlers;
    private long callsPerSec = 0;
    private long overallSegments = 0;
    private DataCreator dataCreator;

    public UpdateOutputHandlers(List<OutputHandler> outputHandlers) {
        this.outputHandlers = outputHandlers;
    }

    public List<OutputHandler> getOutputHandlers() {
        return outputHandlers;
    }

    public void setOverallSegments(int overallSegments) {
        this.overallSegments = overallSegments;
    }

    public void setDataCreator(DataCreator dataCreator) {
        this.dataCreator = dataCreator;
        this.overallSegments = this.dataCreator.getOverallBulks();
    }

    public void setCallsPerSec(int callsPerSec) {
        this.callsPerSec = callsPerSec;
    }

    /**
     * The function creates a new json using the DataGenerator class and updates it's listeners.
     */
    public void run() {
//        List<String> readySegmentsList = this.dataCreator.getSegmentsList();
        // when it should be the last iteration of the task
        // TODO: This condition should be reviewed if still necessary
        if ((this.counter + this.callsPerSec) >= this.overallSegments) {
            this.callsPerSec = this.overallSegments - this.counter;
        }
        for (int i = 0; i < this.callsPerSec; i++) {
            String segment = dataCreator.getSegment();
            for (OutputHandler oh : this.outputHandlers) {
                if (SteadyPolicy.isRun) {
                    if (segment != null) {
                        oh.output(segment);
                        log.debug("Segment = " + segment);
                    } else {
                        log.error("**** Segment data is null !! ****");
                    }
                } else {
                    this.cancel();
                }
            }
        }
        this.counter += this.callsPerSec;
    }

    public int getCounter() {
        return counter;
    }
}
