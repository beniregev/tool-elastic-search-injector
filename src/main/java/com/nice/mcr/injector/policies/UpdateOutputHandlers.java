package com.nice.mcr.injector.policies;

import com.nice.mcr.injector.MainCli;
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
    //  TODO Binyamin Regev -- Remove after refactoring, when this class complies with SOLID Single Responsibility Principle
    private DataCreatorAgentCallsDays dataCreatorAgentCallsDays;

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

    //  TODO Binyamin Regev -- Remove after refactoring, when this class complies with SOLID Single Responsibility Principle
    public void setDataCreatorAgentCallsDays(DataCreatorAgentCallsDays dataCreatorAgentCallsDays) {
        this.dataCreatorAgentCallsDays = dataCreatorAgentCallsDays;
    }

    public void setCallsPerSec(int callsPerSec) {
        this.callsPerSec = callsPerSec;
    }

    /**
     * The function creates a new json using the DataGenerator class and updates it's listeners.
     */
    public void run() {
        // when it should be the last iteration of the task
        // TODO: This condition should be reviewed if still necessary
        if ((this.counter + this.callsPerSec) >= this.overallSegments) {
            this.callsPerSec = this.overallSegments - this.counter;
        }
        for (int i = 0; i < this.callsPerSec; i++) {
            String segment;
            String resultMessage = this.dataCreator != null ?
                    "dataCreator is NOT null" :
                    (this.dataCreatorAgentCallsDays != null ?
                            "dataCreatorAgentCallsDays is NOT null" :
                            "dataCreator AND dataCreatorAgentCallsDays are NULL" );
            System.out.println(">>>>> UpdateOutputHandlers.run() " + resultMessage);
            if (this.dataCreator != null) {
                segment = dataCreator.getSegment();
            } else {
                segment = dataCreatorAgentCallsDays.getSegment();
            }
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
            MainCli.beenCreated ++;
        }
        this.counter += this.callsPerSec;
    }

    public int getCounter() {
        return counter;
    }
}
