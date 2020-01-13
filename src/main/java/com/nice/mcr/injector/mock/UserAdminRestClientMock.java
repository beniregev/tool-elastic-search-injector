package com.nice.mcr.injector.mock;

import com.nice.mcr.injector.config.ApplicationContextProvider;
import com.nice.mcr.injector.config.ArgsComponent;
import com.nice.mcr.injector.model.Agent;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;

import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *     Mocking UserAdminRestClient class to get {@link Agent} by {@code agentId}.
 * </p>
 */
public class UserAdminRestClientMock {
    private static final Logger log = LoggerFactory.getLogger(UserAdminRestClientMock.class);

    private DB dbAgentsNames;
    private HTreeMap mapAgentsNames;

    private int numberOfAgents;
    private double uniqueNamePercent;

    public UserAdminRestClientMock() {
        //ArgsComponent argsComponent = applicationContext.getBean(ArgsComponent.class);
        this.dbAgentsNames = DBMaker.fileDB("MockAgentsNames.db")
                .cleanerHackEnable()
                .closeOnJvmShutdown()
                .make();
        Map<String, String> appArgs = ApplicationContextProvider.getApplicationContext().getBean(ArgsComponent.class).getAppArgs();
        this.numberOfAgents = Integer.parseInt(appArgs.getOrDefault("noa", "-1"));
        this.uniqueNamePercent = Double.parseDouble(appArgs.getOrDefault("unp", "-1"));
        if (numberOfAgents > 0) {
            this.mapAgentsNames = generateListOfAgents(numberOfAgents, uniqueNamePercent, this.dbAgentsNames);
        }
    }

    public UserAdminRestClientMock(ApplicationArguments applicationArguments) {

    }

    public UserAdminRestClientMock(Map<String, String> appArgs) {
        System.out.println("UserAdminRestClientMock .constructor(Map<String, String>): " + appArgs);
    }

    public Agent getAgentById(int agentId) {
        Agent agent = (Agent) mapAgentsNames.get(agentId);
        return agent;
    }

    public HTreeMap getMapAgentsNames() {
        return mapAgentsNames;
    }

//    public HTreeMap<Integer, Agent> generateListOfAgents(HTreeMap<String, Agent> agentsNames) {
//        HTreeMap mapAgentsNames = (HTreeMap) agentsNames.values().stream().collect(Collectors.toMap(x -> x.getAgentId(), x -> x));
//        return mapAgentsNames;
//    }

    /**
     * <p>
     * <div>
     *     Using square-root of {@code numberOfAgents} to have
     *     larger diversity of first and last names.
     * </div>
     * <div>
     *     Note: middle-name in agent-name is not supported yet.
     * </div>
     * </p>
     * @param numberOfAgents The size of the list of agents names.
     * @param uniqueNamePercent Percentage of unique names in the list of agents.
     * @param db {@link DB}
     * @return {@link HTreeMap}<{@link Integer}, {@link Agent}> list of agents, {@code agentId} is the key and value is {@link Agent} class.
     */
    public HTreeMap<Integer, Agent> generateListOfAgents(int numberOfAgents, double uniqueNamePercent, DB db) {
        List<String> firstNames = generateNames("..\\tool-elastic-search-injector\\input\\first-names.txt");
        List<String> lastNames = generateNames("..\\tool-elastic-search-injector\\input\\last-names.txt");
        //List<String> middleNames = generateNames("..\\tool-elastic-search-injector\\input\\middle-names.txt");

        //  numberOfFirstNames and numberOfLastNames is the same, since using Square Root to find them
        int numberOfFirstNames = (int)Math.sqrt((double)numberOfAgents) + 1;
        log.trace("Number of First Names = " + numberOfFirstNames);

        HTreeMap mapAgentsNames = db.hashMap("mapAgentsNames").createOrOpen();
        long start = System.currentTimeMillis();
        int i=1;
        int agentId = 1;
        for(String firstName : firstNames) {
            int j=1;
            for (String lastName : lastNames) {
                Agent agent = new Agent(agentId, firstName, lastName);
                mapAgentsNames.put(agentId, agent);
                agentId++;
                log.debug("Agent Name: " + firstName + " " + lastName);
                j++;
                if (j > numberOfFirstNames) break;
            }
            i++;
            if (i > numberOfFirstNames) break;
        }
        long end = System.currentTimeMillis();
        log.debug("Generating a list of " + numberOfAgents +
                " agents names took " + (end - start) + " milliseconds");

        return mapAgentsNames;
    }

    private List<String> generateNames(String path) {
        List<String> listNames = new ArrayList<>();
        BufferedReader bufferReader = null;
        try {
            bufferReader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = bufferReader.readLine()) != null) {
                listNames.add(line);
            }

        } catch (FileNotFoundException fnfe) {
            log.error("", fnfe);
        } catch (IOException ioe) {
            log.error("Error while reading name file", ioe);
        } finally {
            if (bufferReader != null) {
                try {
                    bufferReader.close();
                } catch (IOException ioe) {
                    log.error("Error while trying to close reader", ioe);
                }
            }
        }
        log.debug("names \"" + path + "\": " + listNames.size());
        return listNames;
    }

    @PreDestroy
    public void customDestroy() {
        System.out.println("Method customDestroy() invoked...");
        dbAgentsNames.close();
        try {
            Files.delete(Paths.get("MockAgentsNames.db"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
