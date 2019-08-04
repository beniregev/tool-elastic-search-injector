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
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication(scanBasePackages = {"com.nice.mcr"})
public class MainCli implements ApplicationRunner {

    /*
     * Key-Value arguments:
     *
     * p = policy
     * steady = SteadyPolicy
     * backlog = BacklogPolicy
     * spike = SpikePolicy
     *
     * o = output handler
     * rmq = RabbitMQ
     * file = FileOutput
     * socket = socketOutput
     *
     * runtime = policy run time (seconds)
     * cps = segments per second (steady policy)
     * bulk = number of segments in bulk (backlog policy)
     * st = time of steady policy (spike policy)
     * ms = max segments in bulk (spike policy)
     * mnop = max number of peaks in bulk (spike policy)
     *
     * Update Handlers arguments:
     * nb = number of bulks to create
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
                    outputHandlersList.add(new RabbitMQOutput());
                    break; // creates RMQ and add to OutputHandlersList
                case SocketOutput.CLI_OPTION:
                    outputHandlersList.add(new SocketOutput());
                    break;
                case FileOutput.CLI_OPTION:
                    outputHandlersList.add(new FileOutput());
                    break;
            }
        }
        for (OutputHandler outputHandler : outputHandlersList) {
            outputHandler.open();
        }
        UpdateHandlers updateHandlers;
        List<String> numOfBulks = args.getOptionValues("nb");
        if (numOfBulks != null) {
            updateHandlers = new UpdateHandlers(outputHandlersList, Integer.valueOf(numOfBulks.get(0)));

        }
        else {
            updateHandlers = new UpdateHandlers(outputHandlersList, 1);
        }
        for (String s : policiesFromInput) {
            switch (s) {
                case "steady": {
                    List<String> runTime = args.getOptionValues("runtime");
                    List<String> numOfSegmentsPerSec = args.getOptionValues("cps");
                    if ((runTime != null) && (numOfSegmentsPerSec != null)) {
                        policyList.add(new SteadyPolicy(updateHandlers, Integer.valueOf(runTime.get(0)),
                                Integer.valueOf(numOfSegmentsPerSec.get(0)), false));
                    }
                }
                break;
                case "backlog": {
                    List<String> numOfSegments = args.getOptionValues("bulk");
                    if (numOfSegments != null) {
                        policyList.add(new BacklogPolicy(updateHandlers, Integer.valueOf(numOfSegments.get(0)), false));
                    }
                }
                break;
                case "spike": {
                    List<String> runTime = args.getOptionValues("runtime");
                    List<String> numOfSegmentsPerSec = args.getOptionValues("cps");
                    List<String> steadyTime = args.getOptionValues("st");
                    List<String> maxSegments = args.getOptionValues("ms");
                    List<String> maxNumberOfPicks = args.getOptionValues("mnop");
                    if ((runTime != null) && (numOfSegmentsPerSec != null) && (steadyTime != null)
                            && (maxSegments != null) && (maxNumberOfPicks != null)) {
                        policyList.add(new SpikePolicy(updateHandlers, Integer.valueOf(runTime.get(0)),
                                Integer.valueOf(numOfSegmentsPerSec.get(0)), Integer.valueOf(steadyTime.get(0)),
                                Integer.valueOf(maxSegments.get(0)), Integer.valueOf(maxNumberOfPicks.get(0))));
                    }
                }
                break;
            }
        }
        for (Policy p : policyList) {
            p.run();
        }
    }
}