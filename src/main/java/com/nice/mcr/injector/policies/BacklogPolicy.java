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

    //  region class properties
    private final static int CPS_CONST = 1000;
    private UpdateOutputHandlers updateOutputHandlers;
    private int overallSegments;
    private boolean runInSeparateThread;
    private Runnable r;

    private int callsPerDay;
    private boolean hasNumOfAgentsAndCallsPerDayArgs = false;
    private boolean hasNumberOfDaysArg = false;
    private boolean hasDateFromDateToArgs = false;
    private DB dbAgentsNames;
    private HTreeMap mapAgentsNames;
    private List<LocalDateTime> listOfCallsPerAgent;

    //  ApplicationArguments --> received from MainCli class and converted into Map<String, List<String>>
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private UserAdminRestClientMock userAdminRestClientMock;

    private ApplicationArguments applicationArguments;
    private Map<String, String> appArgs = new HashMap<>();
    //  endregion

    /**
     * <p>
     * <ul>Parameters to be used in the constructor:
     * <li><i>Number of Agents</i>: appArgs.get("noa") or appArgs.get("numberOfAgents")</li>
     * <li><i>Calls Per Day (Per-Agent)</i>: appArgs.get("cpd") or appArgs.get("callsPerDay")</li>
     * <li>
     *     <div><i>Number of Days</i>: appArgs.get("nod") or appArgs.get("numberOfDays")</div>
     *     <div>OR</div>
     *     <div>Number of days between Date-From and Date-To:</div>
     *     <div><i>Date-From</i>: appArgs.get("df") or appArgs.get("dateFrom")</div>
     *     <div><i>Date-To</i>: appArgs.get("dt") or appArgs.get("dateTo")</div>
     *     <div><code>int numberOfDays = (int) ChronoUnit.DAYS.between(dateFrom, dateTo) + 1;</code></div>
     * </li>
     * </ul>
     * </p>
     * @param updateOutputHandlers {@link UpdateOutputHandlers} to be used to output the segments to the target.
     * @param applicationArguments {@link ApplicationArguments} received from {@link MainCli}.
     * @param runInSeparateThread Whether to run task in separate thread or on main thread.
     */
    public BacklogPolicy(UpdateOutputHandlers updateOutputHandlers, ApplicationArguments applicationArguments, boolean runInSeparateThread) {
        this.applicationArguments = applicationArguments;
        this.updateOutputHandlers = updateOutputHandlers;
        this.runInSeparateThread = runInSeparateThread;

        this.setApplicationArguments(applicationArguments);
        int numberOfAgents = Integer.parseInt(appArgs.get("noa"));
        int uniqueNamePercentage = Integer.parseInt(appArgs.get("unp"));
        this.callsPerDay = Integer.parseInt(appArgs.get("cpd"));
        int durationOfCall = Integer.parseInt(appArgs.get("doc"));
        int numberOfDays = Integer.parseInt(appArgs.get("nod"));
        LocalDate dateFrom = (appArgs.get("df") != null) ? LocalDate.parse(appArgs.get("df")) : null;
        LocalDate dateTo = (appArgs.get("dt") != null) ? LocalDate.parse(appArgs.get("dt")) : null;
        //  Having Date-From and Date-To overrides number-of-days value
        if (dateFrom != null && dateTo != null) {
            numberOfDays = (int) ChronoUnit.DAYS.between(dateFrom, dateTo) + 1;
        }
        this.overallSegments = numberOfAgents * this.callsPerDay * numberOfDays;
        log.debug("entering backlog policy, number of segments to create: " + this.overallSegments);
        MainCli.shouldCreated += this.overallSegments;

        dbAgentsNames = DBMaker.fileDB("MapAgentsNames.db")
                .cleanerHackEnable()
                .closeOnJvmShutdown()
                .make();
        mapAgentsNames = dbAgentsNames
                .hashMap("MapAgentsNames")
                .createOrOpen();
        HTreeMap agentsNames = ApplicationContextProvider
                .getApplicationContext()
                .getBean(UserAdminRestClientMock.class)
                .getMapAgentsNames();
        mapAgentsNames = generateListOfAgents(numberOfAgents, uniqueNamePercentage, dbAgentsNames);
        //this.mapAgentsNames = (HTreeMap) agentsNames.values().stream().collect(Collectors.toMap(x -> x.getFirstName() + " " + x.getFirstName(), x -> x));
        for (Object object : agentsNames.values()) {
            Agent agent = (Agent) object;
            this.mapAgentsNames.put(agent.getFirstName() + " " + agent.getLastName(), agent);
        }

        List<LocalDate> listOfDates = generateListOfDates(dateFrom,dateTo);
        listOfDates.forEach(System.out::println);

        List<LocalTime> listOfCallsTimes = generateListOfCallsPerDay(callsPerDay, durationOfCall);
        listOfCallsTimes.forEach(System.out::println);

        this.listOfCallsPerAgent = new ArrayList<>();
        for (LocalDate date : listOfDates) {
            for (LocalTime time : listOfCallsTimes) {
                this.listOfCallsPerAgent.add(LocalDateTime.of(date, time));
            }
        }
        this.listOfCallsPerAgent.forEach(System.out::println);

        this.r = () -> {
            double startTime = System.currentTimeMillis();
            int index = 1;

            for (Object object : mapAgentsNames.getKeys()) {
                String agentName = (String) object;
                Agent agent = (Agent)mapAgentsNames.get(agentName);
                DataCreatorAgentCallsDays dataCreatorAgentCallsDays = new DataCreatorAgentCallsDays(
                        Thread.currentThread(), agent, this.listOfCallsPerAgent,
                        1, this.overallSegments, index,
                        this.updateOutputHandlers.getOutputHandlers());
                Thread thread = new Thread(dataCreatorAgentCallsDays);
                thread.start();
                index++;

                // Make sure the data creation thread gets a 5 sec head start
                try {
                    Thread.currentThread().sleep(5000);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
                this.updateOutputHandlers.setDataCreatorAgentCallsDays(dataCreatorAgentCallsDays);
                this.updateOutputHandlers.setCallsPerSec(CPS_CONST);
                this.updateOutputHandlers.setOverallSegments(this.overallSegments);

                //// Define interval time Set timer schedule
                //Timer timer = new Timer();
                //timer.scheduleAtFixedRate(this.updateOutputHandlers, 0, 1000);

            }
            System.out.println("Total run time of this backlog: " + (System.currentTimeMillis() - startTime));
            System.out.println("number of segments should be created: " + MainCli.shouldCreated);
            System.out.println("number of segments been created: " + MainCli.beenCreated);

        };
    }

    public BacklogPolicy(UpdateOutputHandlers updateOutputHandlers, int overallSegments, boolean runInSeparateThread) {
        this.updateOutputHandlers = updateOutputHandlers;
        this.overallSegments = overallSegments;
        this.runInSeparateThread = runInSeparateThread;
        log.debug("entering backlog policy, number of segments to create: " + this.overallSegments);
        MainCli.shouldCreated += overallSegments;
        this.r = () -> {
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

//    /**
//     * <div>
//     *     This constructor was created to handle <i>number-of-agents</i>, <i>number-of-calls</i> per agent per day,
//     *     and <i>number-of-days</i> OR <i>date-from</i> and <i>date-to</i>.
//     * </div>
//     * <div>
//     *     In this case the value of Overall-Segments is the result of multiplying <i>number-of-agents</i>
//     *     by <i>number-of-calls</i> (per agent per day) by <i>number-of-days</i> or number of days between
//     *     <i>date-from</i> and <i>date-to</i>
//     * </div>
//     * <div>
//     *     {@link Map}<String, Integer> containing a list of agents names, containing either completely or partially
//     *     unique names in the {@link String} key and the {@link Integer} value will hold the number of repetitions
//     *     the specific agent-name returns in the list of agencts-names.
//     * </div>
//     * <div>
//     *     <b>NOTE:</b> Giving date-from and date-to arguments will override the value of number-of-days arguments.
//     * </div>
//     *
//     */
//    public BacklogPolicy(UpdateOutputHandlers updateOutputHandlers, Map<String, List<String>> mapArgs, boolean runInSeparateThread) {
//        final int DURATION_OF_CALL_IN_MINUTES = 5;
//
//        this.updateOutputHandlers = updateOutputHandlers;
//        mapArgs.forEach((k,v) -> appArgs.put(k,v.get(0)));
//
//        this.callsPerDay = Integer.parseInt(appArgs.get("callsPerDay"));
//        this.numberOfAgents = Integer.parseInt(appArgs.get("numberOfAgents"));
//        this.numberOfDays = Integer.parseInt(appArgs.get("numberOfDays"));
//        this.stringDateFrom = appArgs.get("stringDateFrom");
//        this.stringDateTo = appArgs.get("stringDateTo");
//
//        this.hasNumOfAgentsAndCallsPerDayArgs = Boolean.parseBoolean(appArgs.get("hasNumOfAgentsAndCallsPerDayArgs"));
//        this.hasNumberOfDaysArg = Boolean.parseBoolean(appArgs.get("hasNumberOfDaysArg"));
//        this.hasDateFromDateToArgs = Boolean.parseBoolean(appArgs.get("hasDateFromDateToArgs"));
//
//        if (this.hasDateFromDateToArgs) {
//            //  We need that from 2019-11-01 to 2019-11-05 will be 5 days (November 1st, 2nd, 3rd, 4th and 5th in 2019)
//            this.numberOfDays = (int) LocalDate.parse(stringDateFrom)
//                    .until(LocalDate.parse(stringDateTo), DAYS) + 1 ;
//        }
//
//        //  overallSegments = [number of agents] x [calls per day per agent] x [number of days]
//        //  But, we need to create [callsPerDay] segments per day for every agent, so:
//        this.overallSegments = this.callsPerDay;
//
//        this.runInSeparateThread = runInSeparateThread;
//        log.debug("entering backlog policy, number of segments to create: " + this.numberOfAgents * this.callsPerDay * this.numberOfDays);
//
//        MainCli.shouldCreated += this.overallSegments;
//
//        DB dbAgentsNames = DBMaker.fileDB("uniqueAgentsNames-map.db")
//                .cleanerHackEnable()
//                .closeOnJvmShutdown()
//                .make();
//        HTreeMap<String, Integer> mapAgentsNames = generateListOfAgents(numberOfAgents, 100, dbAgentsNames);
//        System.out.println("mapAgentsNames.sizeLong() = " + mapAgentsNames.sizeLong());
//
//        List<LocalTime> listOfCalls = generateListOfCallsPerDay(this.callsPerDay,
//                DURATION_OF_CALL_IN_MINUTES);
//
//
//        this.r = () -> {
//            double startTime = System.currentTimeMillis();
//
//            DataCreator dataCreator = new DataCreator(this.overallSegments,
//                    Thread.currentThread(),
//                    CPS_CONST);
//            dataCreator.setAppArgs(this.appArgs);   //  Pass application arguments
//            dataCreator.setAgentName("agentName");
//            dataCreator.setListOfCalls(listOfCalls);
//            dataCreator.create(true);
//
//            this.updateOutputHandlers.setDataCreator(dataCreator);
//            this.updateOutputHandlers.setCallsPerSec(CPS_CONST);
//            this.updateOutputHandlers.setCallsPerDay(this.callsPerDay);
//            this.updateOutputHandlers.setNumberOfAgents (this.numberOfAgents);
//            this.updateOutputHandlers.setNumberOfDays(this.numberOfDays);
//            this.updateOutputHandlers.setStringDateFrom(this.stringDateFrom);
//            this.updateOutputHandlers.setStringDateTo(this.stringDateTo);
//            this.updateOutputHandlers.setOverallSegments(this.overallSegments);
//
//            // Make sure the data creation thread gets a 5 sec head start
//            try {
//                Thread.currentThread().sleep(5000);
//            } catch (InterruptedException ie) {
//                ie.printStackTrace();
//            }
//            Timer timer = new Timer();
//
//            // Define interval time and Set timer schedule
//            timer.scheduleAtFixedRate(this.updateOutputHandlers, 0, 1000);
//
//            //this.updateOutputHandlers.run();
//            log.debug("Total run time of this backlog: " + (System.currentTimeMillis() - startTime));
//            log.debug("Number of Agents: " + this.numberOfAgents);
//            log.debug("Calls per Agent per Day: " + this.callsPerDay);
//            log.debug("Date From: " + this.stringDateFrom);
//            log.debug("Date To: " + this.stringDateTo);
//            log.debug("Number of Days: " + this.numberOfDays);
//            log.debug("number of segments should be created: " + MainCli.shouldCreated);
//            log.debug("number of segments been created: " + MainCli.beenCreated);
//        };
//    }

    @Override
    public void run() {
        if (this.runInSeparateThread) {
            new Thread(this.r, "BacklogPolicyThread").start();
        } else {
            this.r.run();
        }
    }

    @Override
    public HTreeMap<String, Agent> generateListOfAgents(int numberOfAgents, double uniqueNamePercent, DB db) {
        List<String> firstNames = generateNames("..\\tool-elastic-search-injector\\input\\first-names.txt");
        List<String> lastNames = generateNames("..\\tool-elastic-search-injector\\input\\last-names.txt");
        List<String> middleNames = generateNames("..\\tool-elastic-search-injector\\input\\middle-names.txt");

        //  numberOfFirstNames and numberOfLastNames is the same, since using Square Root to find them
        int numberOfFirstNames = (int)Math.sqrt((double)numberOfAgents) + 1;
        log.trace("Number of First Names = " + numberOfFirstNames);

        HTreeMap htreeMap = db.hashMap("mapAgentsNames").createOrOpen();
        long start = System.currentTimeMillis();
        int i=1;
        int agentId = 1;
        for(String firstName : firstNames) {
            int j=1;
            for (String lastName : lastNames) {
                String agentName = firstName + " " + lastName;
                Agent agent = (Agent)htreeMap.get(agentName);
                if (agent != null) {
                    agent.incrementAmountInList();
                } else {
                    agent = new Agent(agentId, firstName, lastName);
                }
                htreeMap.put(agentName, agent);

                log.trace("firstName=" + firstName + " ; lastName=" + lastName);
                j++;
                if (j > numberOfFirstNames) break;
            }
            i++;
            if (i > numberOfFirstNames) break;
        }
        long end = System.currentTimeMillis();
        log.debug("Generating a list of " + numberOfAgents +
                " agents names took " + (end - start) + " milliseconds");

        return htreeMap;
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
//            System.out.println("Call #" + i + ": \n\tStarted at " + timeCallStarted +
//                    "\n\tEnded at " + timeCallStarted.plusMinutes(durationOfCallInMinutes) +
//                    "\n\tContact Start Time: " + timeCallStarted +
//                    "\n\tContact End Time: " + timeCallStarted.plusSeconds(150)
//            );
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

    /**
     * Set ApplicationArguments into a {@link Map}<String, String> to pass it to
     * the next objects.
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
