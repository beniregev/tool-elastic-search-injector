package com.nice.mcr.injector.policies;

import com.nice.mcr.injector.MainCli;
import com.nice.mcr.injector.config.ApplicationContextProvider;
import com.nice.mcr.injector.mock.UserAdminRestClientMock;
import com.nice.mcr.injector.model.Agent;
import com.nice.mcr.injector.service.Consts;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.ApplicationContext;

import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BacklogPolicy implements Policy {
    private static final Logger log = LoggerFactory.getLogger(BacklogPolicy.class);

    private static final String AGENTS_NAMES_DATABASE_FILE = "MapAgentsNames.db";

    //  region class properties
    private final static int CPS_CONST = 1000;
    private UpdateOutputHandlers updateOutputHandlers;
    private int overallSegments;
    private boolean runInSeparateThread;
    private Runnable r;

    public BacklogPolicy(UpdateOutputHandlers updateOutputHandlers, int overallSegments, boolean runInSeparateThread) {
        this.updateOutputHandlers = updateOutputHandlers;
        this.overallSegments = overallSegments;
        this.runInSeparateThread = runInSeparateThread;
        log.debug("entering backlog policy, number of segments to create: " + this.overallSegments);
        MainCli.shouldCreated += overallSegments;
        this.runnable = () -> {
            double startTime = System.currentTimeMillis();
            DataCreator dataCreator = new DataCreator(this.overallSegments,
                    Thread.currentThread(),
                    CPS_CONST);
            dataCreator.create(true);
            this.updateOutputHandlers.setDataCreator(dataCreator);
            this.updateOutputHandlers.setCallsPerSec(CPS_CONST);
            this.updateOutputHandlers.setOverallSegments(this.overallSegments);
            // Make sure the data creation thread gets a 5 sec head start
            try {
                Thread.currentThread().sleep(5000);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            Timer timer = new Timer();
            // Define interval time
            // Set timer schedule
            timer.scheduleAtFixedRate(this.updateOutputHandlers, 0, 1000);
//            this.updateOutputHandlers.run();
            System.out.println("Total run time of this backlog: " +
                    (System.currentTimeMillis() - startTime));
            System.out.println("number of segments should be created: " + MainCli.shouldCreated);
            System.out.println("number of segments been created: " + MainCli.beenCreated);
        };
    }

    @Override
    public void run() {
        if (this.runInSeparateThread) {
            new Thread(this.runnable, "BacklogPolicyThread").start();
        } else {
            this.runnable.run();
        }
    }

    /**
     * Use {@link UserAdminRestClientMock#generateListOfAgents(int, double, DB)}
     * to create the agents-list. This method only take that list and convert it
     * from {@link HTreeMap}<{@link Integer}, {@link Agent}> to
     * {@link HTreeMap}<{@link String}, {@link Agent}>.
     * @param numberOfAgents Number of agents names in the final list.
     * @param uniqueNamePercent Percentage of unique agents names, e.g.
     *                          value=66.5 = 66.5% means 33.5% (335 of 1000) of the names are repeating.
     *                          value=100.0 = 100% means all unique names, no duplicates.
     * @param db {@code MapDB} database ({@link DB}), {@code null} if not in use.
     * @return
     */
    @Override
    public HTreeMap<String, Agent> generateListOfAgents(int numberOfAgents, double uniqueNamePercent, DB db) {
        HTreeMap agentsNames = ApplicationContextProvider
                .getApplicationContext()
                .getBean(UserAdminRestClientMock.class)
                .getMapAgentsNames();
        HTreeMap mapNames = db.hashMap("mapAgentsNames").createOrOpen();

        //  Convert HTreeMap<Integer, Agent> to HTreeMap<String, Agent>
        for (Object object : agentsNames.values()) {
            Agent agent = (Agent) object;
            mapNames.put(agent.getFirstName() + " " + agent.getLastName(), agent);
        }

        return mapNames;
    }

    private List<LocalDateTime> generateListOfCallsPerAgent(LocalDate dateFrom, LocalDate dateTo) {
        int durationOfCall = Integer.parseInt(appArgs.get("doc"));

        List<LocalDate> listOfDates = generateListOfDates(dateFrom,dateTo);
        List<LocalTime> listOfCallsTimes = generateListOfCallsPerDay(this.callsPerDay, durationOfCall);

        List<LocalDateTime> callsPerAgent = new ArrayList<>();
        for (LocalDate date : listOfDates) {
            for (LocalTime time : listOfCallsTimes) {
                callsPerAgent.add(LocalDateTime.of(date, time));
            }
        }

        return callsPerAgent;
    }

    private List<LocalDate> generateListOfDates(LocalDate dateFrom, LocalDate dateTo) {
        int numberOfDays = (int) ChronoUnit.DAYS.between(dateFrom, dateTo) + 1;
        return IntStream.iterate(0, i -> i + 1)
                .limit(numberOfDays)
                .mapToObj(i -> dateFrom.plusDays(i))
                .collect(Collectors.toList());
    }

    @Override
    public List<LocalTime> generateListOfCallsPerDay(int numberOfCallsPerDay, int durationOfCallInMinutes) {
        final int timeIntervalBetweenCallInMinutes = (Consts.MINUTES_PER_DAY - (durationOfCallInMinutes * (numberOfCallsPerDay + 1))) / (numberOfCallsPerDay + 1);

        //  For the first gap there's no need to add the call duration
        LocalTime timeCallStarted = LocalTime.MIN.plusMinutes(timeIntervalBetweenCallInMinutes);

        List<LocalTime> listOfCalls = new ArrayList<>();
        for (int i=1; i<=callsPerDay; i++) {
            listOfCalls.add(timeCallStarted);
            timeCallStarted = timeCallStarted.plusMinutes(durationOfCallInMinutes).plusMinutes(timeIntervalBetweenCallInMinutes);
        }
        return listOfCalls;
    }

    private List<String> generateNames(String path) {
        List<String> names = new ArrayList<>();
        BufferedReader bufferReader = null;
        try {
            bufferReader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = bufferReader.readLine()) != null) {
                names.add(line);
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
        log.debug("names \"" + path + "\": " + names.size());
        return names;
    }

    private DB makeDBFileForMapDB(String databaseFileName) {
        return DBMaker.fileDB(databaseFileName)
                .cleanerHackEnable()
                .closeOnJvmShutdown()
                .make();
    }

    /**
     * Set ApplicationArguments into a {@link Map}<{@link String}, {@link String}>
     * @param args {@link ApplicationArguments} from {@link MainCli} class.
     */
    @Override
    public void setApplicationArguments(ApplicationArguments args) {
        Arrays.asList(args.getSourceArgs()).forEach(x -> {
                    System.out.println(x.substring(2));
                    String[] item = x.substring(2).split("=");
                    this.appArgs.put(item[0], item[1]);
                }
        );
        this.appArgs.forEach((k, v) -> System.out.println("key=" + k + ", value=" + v));
    }

    /**
     * Close the database and delete the database
     * file when class is destroyed
     */
    @PreDestroy
    public void customDestroy() {
        //  region close DB and cleanup
        dbAgentsNames.close();
        try {
            Files.delete(Paths.get("MapAgentsNames.db"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //  endregion
    }
}
