package com.nice.mcr.injector;

import com.nice.mcr.injector.output.FileOutput;
import com.nice.mcr.injector.output.OutputHandler;
import com.nice.mcr.injector.output.RabbitMQOutput;
import com.nice.mcr.injector.output.SocketOutput;
import com.nice.mcr.injector.policies.*;
import com.nice.mcr.injector.service.DataGenerator;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MainCli implements ApplicationRunner {

    @Autowired
    private ApplicationContext applicationContext;

    public static int shouldCreated = 0;
    public static int beenCreated = 0;
    /*
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
     * st = time of steady policy (spike policy)
     * ms = max segments in backlog (spike policy)
     * nb = number of bulks to create (elastic policy)
     * sib = number of segments in a single bulk (elastic policy)
     *
     */
    public void run(ApplicationArguments args) {
        if (ObjectUtils.isEmpty(args.getSourceArgs())) {
            return; // Regular web application
        }
        List<String> policiesFromInput = args.getOptionValues("p");
        List<Policy> policyList = new ArrayList<>();
        List<String> outputHandlersFromInput = args.getOptionValues("o");
        List<OutputHandler> outputHandlersList = new ArrayList<>();
        for (String s : outputHandlersFromInput) {
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
        UpdateHandlers updateHandlers = new UpdateHandlers(outputHandlersList);
        for (int i = 0 ; i < policiesFromInput.size() ; i++) {
            switch (policiesFromInput.get(i)) {
                case "steady": {
                    List<String> runTime = args.getOptionValues("runtime");
                    List<String> numOfSegmentsPerSec = args.getOptionValues("cps");
                    List<String> overallSegments = args.getOptionValues("os");
                    if (numOfSegmentsPerSec != null) {
                        if (runTime != null) {
                            policyList.add(new SteadyPolicy(updateHandlers, Integer.valueOf(runTime.get(i)),
                                    Integer.valueOf(numOfSegmentsPerSec.get(i)), true));
                        }
                        else if (overallSegments != null) {
                            policyList.add(new SteadyPolicy(true, updateHandlers,
                                    Integer.valueOf(numOfSegmentsPerSec.get(i)), Integer.valueOf(overallSegments.get(i))));
                        }
                    }
                }
                break;
                case "backlog": {
                    List<String> numOfSegments = args.getOptionValues("nos");
                    if (numOfSegments != null) {
                        policyList.add(new BacklogPolicy(updateHandlers, Integer.valueOf(numOfSegments.get(i)), true));
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
                        policyList.add(new SpikePolicy(updateHandlers, Integer.valueOf(runTime.get(i)),
                                Integer.valueOf(numOfSegmentsPerSec.get(i)), Integer.valueOf(steadyTime.get(i)),
                                Integer.valueOf(maxSegments.get(i))));
                    }
                }
                break;
                case "elastic": {
                    List<String> numOfBulks = args.getOptionValues("nb");
                    List<String> numOfSegmentsPerSec = args.getOptionValues("sib");
                    if ((numOfBulks != null) && (numOfSegmentsPerSec != null)) {
                        policyList.add(new ElasticPolicy(updateHandlers, Integer.valueOf(numOfBulks.get(i)),
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