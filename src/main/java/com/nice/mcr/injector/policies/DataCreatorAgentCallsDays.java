package com.nice.mcr.injector.policies;

import com.nice.mcr.injector.model.Agent;
import com.nice.mcr.injector.output.OutputHandler;
import com.nice.mcr.injector.service.DataGeneratorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Class to create data for Agent number of calls-per-day. Separated from {@link DataCreator} to
 * avoid damaging/changing existing and working functionality, plus, adding functionality required.
 * </p>
 * @author Binyamin Regev
 */
//  TODO Binyamin Regev -- abstract class or interface {@code DataCreator} and this class extends it
public class DataCreatorAgentCallsDays implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(DataCreatorAgentCallsDays.class);

    private long overallBulks;
    private List<String> segmentsList;
    private Thread invokingThread;
    private int numOfSegmentsInBulk = 1;
    private int cps = 1;
    private long numGeneratedSegments = 0;
    private Object segmentLock = new Object();

    private List<OutputHandler> listOutputHandlers;

    private List<LocalDateTime> listOfCallsPerAgent;
    private int numberOfSegments;
    private boolean runInSeparateThread;
    private Agent agent;
    private int index;

    public DataCreatorAgentCallsDays(Thread invokingThread, Agent agent, List listOfCallsPerAgent, int overallBulks, int numOfSegmentsInBulk, int index, List listOutputHandlers) {
        this.agent = agent;
        this.listOutputHandlers = listOutputHandlers;
        this.overallBulks = overallBulks;
        this.invokingThread = invokingThread;
        this.numberOfSegments = listOfCallsPerAgent.size();
        this.listOfCallsPerAgent = listOfCallsPerAgent;
        this.numOfSegmentsInBulk = numOfSegmentsInBulk;
        this.segmentsList = new ArrayList<String>(overallBulks);
    }

    @Override
    public void run() {
        System.out.println(">> DataCreatorAgentCallsDays.run() --> " + this.index + " - Agent: " + this.agent);
        int callIndex = 1;
        //  Generate Segments for Agent
        for (LocalDateTime callDateTime : listOfCallsPerAgent) {
            //Thread threadLevel2 = new Thread(new ThreadLevel2(this.agent, callDateTime, callIndex));
            //threadLevel2.start();
            DataGeneratorImpl dataGenerator = new DataGeneratorImpl();
            //segmentsList.add(dataGenerator.createDataAgentCallInDay(this.agent, callDateTime, this.numberOfSegments));
            String dataSegment = dataGenerator.createDataAgentCallInDay(this.agent, callDateTime, this.numberOfSegments);
            this.outputSegment(dataSegment);
            numGeneratedSegments += numOfSegmentsInBulk;
            callIndex ++;
        }

    }

    private void outputSegment(String segment) {
        for (OutputHandler oh : this.listOutputHandlers) {
            if (SteadyPolicy.isRun) {
                if (segment != null) {
                    oh.output(segment);
                    log.debug("Segment = " + segment);
                } else {
                    log.error("**** Segment data is null !! ****");
                }
            } else {
                //  Cancel
                log.error("SteadyPolicy is not running!!!!!!!");
                return;
            }
        }
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
