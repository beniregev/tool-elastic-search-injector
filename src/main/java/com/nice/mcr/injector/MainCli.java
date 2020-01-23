package com.nice.mcr.injector;

import com.nice.mcr.injector.output.FileOutput;
import com.nice.mcr.injector.output.OutputHandler;
import com.nice.mcr.injector.output.RabbitMQOutput;
import com.nice.mcr.injector.output.SocketOutput;
import com.nice.mcr.injector.policies.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ObjectUtils;

import java.util.*;

@SpringBootApplication
public class MainCli implements ApplicationRunner {

    @Autowired
    private ApplicationContext applicationContext;

    private ApplicationArguments applicationArguments;
    public static int shouldCreated = 0;
    public static int beenCreated = 0;

    /**
     * Key-Value arguments:
     *
     * p = policy
     * steady = SteadyPolicy
     * backlog = BacklogPolicy
     * spike = SpikePolicy
     * elastic = ElasticPolicy
     *
     * o = output handler
     * rmq = RabbitMQ
     * file = FileOutput
     * socket = socketOutput
     *
     * runtime = policy run time (seconds)
     * cps = segments per second (steady policy)
     * os = overall segments to send (steady policy)
     * nos = number of segments to create (backlog policy)
     *      <b>IMPORTANT:</b> <i>nos</i> cannot be defined with <i>cpd</i> + <i>nod</i> or with <i>cpd</i> + <i>date-from</i> + <i>date-to</i> cannot
     * st = time of steady policy (spike policy)
     * ms = max segments in backlog (spike policy)
     * nb = number of bulks to create (elastic policy)
     * sib = number of segments in a single bulk (elastic policy)
     * <p>
     * <div>------------------------------------------------------------------------------------</div>
     * <div>V10-5885 -- Additions</div>
     * <div>------------------------------------------------------------------------------------</div>
     * <div></div><b><i>noa =</i></b> Number Of Agents (BacklogPolicy, Agents -> Calls-Per-Day -> Number-of-Days)</div>
     * <div><b><i>unp =</i></b> Unique Name Percentage (BacklogPolicy, Agents -> Calls-Per-Day -> Number-of-Days), e.g. value of 100 means that all agents names in the list are unique - there are no duplicates, value of 70 means that 7-of-10 names in the list are unique</div>
     * <div><b><i>cpd =</i></b> Calls Per Day (BacklogPolicy, Agents -> Calls-Per-Day -> Number-of-Days)</div>
     * <div><b><i>doc =</i></b> Duration of call in minutes (BacklogPolicy, Agents -> Calls-Per-Day -> Number-of-Days)</div>
     * <div><b><i>nod =</i></b> Number Of Days (BacklogPolicy, Agents -> Calls-Per-Day -> Number-of-Days)</div>
     * <div><b><i>df =</i></b> Date-From (BacklogPolicy, Agents -> Calls-Per-Day -> Number-of-Days)</div>
     * <div><b><i>dt =</i></b> Date-To (BacklogPolicy, Agents -> Calls-Per-Day -> Number-of-Days)</div>
     * <div><b>IMPORTANT:</b> <i>nos</i> cannot be defined with <i>cpd</i> + <i>nod</i> or with <i>cpd</i> + <i>date-from</i> + <i>date-to</i>.</div>
     * </p>
     */
    public void run(ApplicationArguments args) throws IllegalArgumentException {
        if (ObjectUtils.isEmpty(args.getSourceArgs())) {
            return; // Regular web application
        }
        this.applicationArguments = args;
        List<String> policiesFromInput = args.getOptionValues("p");
        List<Policy> policyList = new ArrayList<Policy>();
        List<String> outputHandlersFromInput = args.getOptionValues("o");
        List<OutputHandler> outputHandlersList = new ArrayList<OutputHandler>();
        for (String s : outputHandlersFromInput) {
            //  TODO Binyamin Regev -- replace SWITCH with Spring @Component with id/code
            switch (s) {
                case RabbitMQOutput.CLI_OPTION:
                    outputHandlersList.add(applicationContext.getBean(RabbitMQOutput.class));
                    break;
                case SocketOutput.CLI_OPTION:
                    outputHandlersList.add(applicationContext.getBean(SocketOutput.class));
                    break;
                case FileOutput.CLI_OPTION:
                    outputHandlersList.add(applicationContext.getBean(FileOutput.class));
                    break;
            }
        }
        for (OutputHandler outputHandler : outputHandlersList) {
            outputHandler.open();
        }
        UpdateOutputHandlers updateOutputHandlers = new UpdateOutputHandlers(outputHandlersList);
        for (int i = 0 ; i < policiesFromInput.size() ; i++) {
            //  TODO Binyamin Regev -- replace SWITCH with Spring @Component with id/code
            switch (policiesFromInput.get(i)) {
                case "steady": {
                    List<String> runTime = args.getOptionValues("runtime");
                    List<String> numOfSegmentsPerSec = args.getOptionValues("cps");
                    List<String> overallSegments = args.getOptionValues("os");
                    if (numOfSegmentsPerSec != null) {
                        if (runTime != null) {
                            policyList.add(new SteadyPolicy(updateOutputHandlers, Integer.valueOf(runTime.get(0)),
                                    Integer.valueOf(numOfSegmentsPerSec.get(0)), true));
                        }
                        else if (overallSegments != null) {
                            policyList.add(new SteadyPolicy(true, updateOutputHandlers,
                                    Integer.valueOf(numOfSegmentsPerSec.get(0)), Integer.valueOf(overallSegments.get(0))));
                        }
                    }
                }
                break;
                case "backlog": {
                    List<String> numOfSegments = args.getOptionValues("nos");

                    List<String> numOfAgents = args.getOptionValues("noa");
                    List<String> callsPerDay = args.getOptionValues("cpd");
                    List<String> numOfDays = args.getOptionValues("nod");
                    List<String> dateFrom = args.getOptionValues("df");
                    List<String> dateTo = args.getOptionValues("dt");
                    boolean hasNumOfAgentsAndCallsPerDayArgs = numOfAgents != null && callsPerDay != null;
                    boolean hasNumberOfDaysArg = numOfDays != null;
                    boolean hasDateFromDateToArgs = dateFrom != null && dateTo != null;
                    if (numOfSegments != null && (hasNumOfAgentsAndCallsPerDayArgs && (hasNumberOfDaysArg || hasDateFromDateToArgs))) {
                        throw new IllegalArgumentException("Arguments provided resulted with a conflict, define either number-of-segments or calls-per-day with number-of-days or with date-from and date-to.");
                    }

                    if (numOfSegments != null) {
                        policyList.add(new BacklogPolicy(updateOutputHandlers, Integer.valueOf(numOfSegments.get(i)), true));
                    } else {
                        if (hasNumOfAgentsAndCallsPerDayArgs && (hasNumberOfDaysArg || hasDateFromDateToArgs)) {
                            //Constructor: BacklogPolicy(UpdateOutputHandlers, Map<String, List<String>>, boolean)
                            policyList.add(new BacklogPolicy(updateOutputHandlers, applicationArguments, true));
                        }
                    }
                }
                break;
                case "spike": {
                    List<String> runTime = args.getOptionValues("runtime");
                    List<String> numOfSegmentsPerSec = args.getOptionValues("cps");
                    List<String> steadyTime = args.getOptionValues("st");
                    List<String> maxSegments = args.getOptionValues("ms");
                    if ((runTime != null) && (numOfSegmentsPerSec != null) && (steadyTime != null)
                            && (maxSegments != null)) {
                        policyList.add(new SpikePolicy(updateOutputHandlers, Integer.valueOf(runTime.get(i)),
                                Integer.valueOf(numOfSegmentsPerSec.get(i)), Integer.valueOf(steadyTime.get(i)),
                                Integer.valueOf(maxSegments.get(i))));
                    }
                }
                break;
                case "elastic": {
                    List<String> numOfBulks = args.getOptionValues("nb");
                    List<String> numOfSegmentsPerSec = args.getOptionValues("sib");
                    if ((numOfBulks != null) && (numOfSegmentsPerSec != null)) {
                        policyList.add(new ElasticPolicy(updateOutputHandlers, Integer.valueOf(numOfBulks.get(i)),
                                Integer.valueOf(numOfSegmentsPerSec.get(i))));
                    }
                }
            }
        }
        for (Policy p : policyList) {
            p.run();
        }
    }
}