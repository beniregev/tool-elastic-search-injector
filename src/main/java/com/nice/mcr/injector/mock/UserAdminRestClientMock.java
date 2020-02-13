package com.nice.mcr.injector.mock;

import com.nice.mcr.injector.config.ApplicationContextProvider;
import com.nice.mcr.injector.config.ArgsComponent;
import com.nice.mcr.injector.model.Agent;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  Mocking UserAdminRestClient class to get {@link Agent}
 *  by {@code agentId}.
 */
public class UserAdminRestClientMock {
    private static final Logger log = LoggerFactory.getLogger(UserAdminRestClientMock.class);

    private DB dbAgentsNames;
    private HTreeMap mapAgentsNames;

    private int numberOfAgents;
    private double uniqueNamePercent;

    public UserAdminRestClientMock() {
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

    /**
     * Using square-root of {@code numberOfAgents} to
     * have larger diversity of first and last names.
     * Note: middle-name in agent-name is not supported yet.
     *
     * @param numberOfAgents The size of the list of agents names.
     * @param uniqueNamePercent Percentage of unique names in the list of agents.
     * @param db {@link DB}
     * @return {@link HTreeMap}<{@link Integer}, {@link Agent}> list of agents, {@code agentId} is the key and value is {@link Agent} class.
     */
    public HTreeMap<Integer, Agent> generateListOfAgents(int numberOfAgents, double uniqueNamePercent, DB db) {
        List<String> firstNames = generateNames("..\\tool-elastic-search-injector\\input\\first-names.txt");
        List<String> lastNames = generateNames("..\\tool-elastic-search-injector\\input\\last-names.txt");
        int numberOfFirstNames = getNumberOfAgentsSquareRoot(numberOfAgents);
        log.debug("Number of First Names = " + numberOfFirstNames);

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

        return mapAgentsNames;
    }

    /**
     * numberOfFirstNames and numberOfLastNames is the same, since using Square Root to find them
     * @param value {@link Integer} to compute its Square-Root.
     * @return {@link Integer} value of Square-Root
     */
    private int getNumberOfAgentsSquareRoot(int value) {
        double returnValue = Math.ceil(Math.sqrt(value));
        return (int)returnValue;
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
            log.error("File \"" + path + "\" not found were expected. ", fnfe);
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

//    @PreDestroy
//    public void customDestroy() {
//        System.out.println("Method customDestroy() invoked...");
//        dbAgentsNames.close();
//        try {
//            Files.delete(Paths.get("MockAgentsNames.db"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
