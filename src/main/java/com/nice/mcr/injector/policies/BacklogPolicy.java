package com.nice.mcr.injector.policies;

import com.nice.mcr.injector.MainCli;
import com.nice.mcr.injector.service.Consts;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

public class BacklogPolicy implements Policy {
    private static final Logger log = LoggerFactory.getLogger(com.nice.mcr.injector.policies.BacklogPolicy.class);
    //  region class properties
    private final static int CPS_CONST = 1000;
    private UpdateOutputHandlers updateOutputHandlers;
    private int overallSegments;
    private boolean runInSeparateThread;
    private Runnable r;

    private int callsPerDay;
    private int numberOfAgents;
    private int numberOfDays;
    private String stringDateFrom;
    private String stringDateTo;
    boolean hasNumOfAgentsAndCallsPerDayArgs = false;
    boolean hasNumberOfDaysArg = false;
    boolean hasDateFromDateToArgs = false;

    //  ApplicationArguments --> received from MainCli class and converted into Map<String, List<String>>
    private Map<String, String> appArgs = new HashMap<>();
    //  endregion

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
    public HTreeMap<String, Integer> generateListOfAgents(int numberOfAgents, double uniqueNamePercent, DB db) {
        List<String> firstNames = generateNames("..\\tool-elastic-search-injector\\input\\first-names.txt");
        List<String> lastNames = generateNames("..\\tool-elastic-search-injector\\input\\last-names.txt");
        List<String> middleNames = generateNames("..\\tool-elastic-search-injector\\input\\middle-names.txt");

        //  numberOfFirstNames and numberOfLastNames is the same, since using Square Root to find them
        int numberOfFirstNames = (int)Math.sqrt((double)numberOfAgents) + 1;
        log.trace("Number of First Names = " + numberOfFirstNames);

        HTreeMap mapAgentsNames = db.hashMap("mapAgentsNames").createOrOpen();
        long start = System.currentTimeMillis();
        int i=1;
        for(String firstName : firstNames) {
            int j=1;
            for (String lastName : lastNames) {
                mapAgentsNames.put(firstName + " " + lastName, 1);
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

        return mapAgentsNames;
    }

    @Override
    public List<LocalTime> generateListOfCallsPerDay(int numberOfCallsPerDay, int durationOfCallInMinutes) {
        final int timeIntervalBetweenCallInMinutes = (Consts.MINUTES_PER_DAY - (durationOfCallInMinutes * (numberOfCallsPerDay + 1))) / (numberOfCallsPerDay + 1);

        //  For the first gap there's no need to add the call duration
        LocalTime timeCallStarted = LocalTime.MIN.plusMinutes(timeIntervalBetweenCallInMinutes);

        List<LocalTime> listOfCalls = new ArrayList<>();
        for (int i=1; i<=callsPerDay; i++) {
            listOfCalls.add(timeCallStarted);
            System.out.println("Call #" + i + ": \n\tStarted at " + timeCallStarted +
                    "\n\tEnded at " + timeCallStarted.plusMinutes(durationOfCallInMinutes) +
                    "\n\tContact Start Time: " + timeCallStarted +
                    "\n\tContact End Time: " + timeCallStarted.plusSeconds(150)
            );
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

    public Map<String, String> getAppArgs() {
        return appArgs;
    }

    public void setAppArgs(Map<String, String> appArgs) {
        this.appArgs = appArgs;
    }

    public boolean isHasNumOfAgentsAndCallsPerDayArgs() {
        return hasNumOfAgentsAndCallsPerDayArgs;
    }

    public void setHasNumOfAgentsAndCallsPerDayArgs(boolean hasNumOfAgentsAndCallsPerDayArgs) {
        this.hasNumOfAgentsAndCallsPerDayArgs = hasNumOfAgentsAndCallsPerDayArgs;
    }

    public boolean isHasNumberOfDaysArg() {
        return hasNumberOfDaysArg;
    }

    public void setHasNumberOfDaysArg(boolean hasNumberOfDaysArg) {
        this.hasNumberOfDaysArg = hasNumberOfDaysArg;
    }

    public boolean isHasDateFromDateToArgs() {
        return hasDateFromDateToArgs;
    }

    public void setHasDateFromDateToArgs(boolean hasDateFromDateToArgs) {
        this.hasDateFromDateToArgs = hasDateFromDateToArgs;
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
}

