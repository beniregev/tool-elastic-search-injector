package com.nice.mcr.injector.service;

import com.nice.mcr.injector.config.ApplicationContextProvider;
import com.nice.mcr.injector.config.ArgsComponent;
import com.nice.mcr.injector.model.Agent;
import com.nice.mcr.injector.output.FileOutput;
import com.nice.mcr.injector.output.OutputHandler;
import com.nice.mcr.injector.output.RabbitMQOutput;
import com.nice.mcr.injector.output.SocketOutput;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;

@Service
public class DataGeneratorImpl implements DataGenerator {
    private static final Logger log = LoggerFactory.getLogger(DataGeneratorImpl.class);

    private final String DIRECTION_RX = "Rx";
    private final String DIRECTION_TX = "Tx";
    private final StringBuffer strEndOfYear = new StringBuffer("-12-31 23:59:00.123")
            .insert(0, LocalDate.now().getYear());

    private Random random = new Random();
    private List<OutputHandler> outputHandlersList = new ArrayList<>();

    private static DateTimeFormatter DATE_FORMAT = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd").optionalStart().appendPattern(" HH:mm:ss.SSS").optionalEnd().parseDefaulting(ChronoField.HOUR_OF_DAY, 0).parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0).parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0).parseDefaulting(ChronoField.MILLI_OF_SECOND, 0).toFormatter();
    List<String> firstNames = generateNames("..\\tool-elastic-search-injector\\input\\first-names.txt");
    List<String> lastNames = generateNames("..\\tool-elastic-search-injector\\input\\last-names.txt");
    List<String> middleNames = generateNames("..\\tool-elastic-search-injector\\input\\middle-names.txt");
    JSONObject jsonObj = new JSONObject();

    public DataGeneratorImpl() {
        boolean containsRMQOutput = ApplicationContextProvider.getApplicationContext().containsBean("RabbitMQOutput");
        boolean containsSocketOutput = ApplicationContextProvider.getApplicationContext().containsBean("SocketOutput");
        boolean containsFileOutput = ApplicationContextProvider.getApplicationContext().containsBean("FileOutput");
        if (containsRMQOutput)
            this.outputHandlersList.add(ApplicationContextProvider
                    .getApplicationContext()
                    .getBean(RabbitMQOutput.class));
        if (containsSocketOutput)
            this.outputHandlersList.add(ApplicationContextProvider
                    .getApplicationContext()
                    .getBean(SocketOutput.class));

        if (containsFileOutput)
            this.outputHandlersList.add(ApplicationContextProvider
                    .getApplicationContext()
                    .getBean(FileOutput.class));

    }

    public String createData(int numOfInteractions) {
        String tempBulk = "";
        try {
            tempBulk = generateBulkData(numOfInteractions);
        } catch (JSONException je) {
            log.error("", je);
        }
        return tempBulk;
    }

    /**
     * Create date for a single agent call sometime in the day can handle  {@link JSONException}.
     *
     * @param agent             {@link Agent} the details of the agent in the segment.
     * @param callStartDateTime {@link LocalDateTime} of the start of the agent's call.
     * @param numOfInteractions Number of interactions, for now it's 1 until we support multiple segments in a bulk.
     * @return {@link String} containing the segment created.
     */
    public String createDataAgentCallInDay(Agent agent, LocalDateTime callStartDateTime, int numOfInteractions) {
        String tempBulk = "";
        try {
            tempBulk = generateSegmentDataAgentCallInDay(agent, callStartDateTime, numOfInteractions);
        } catch (JSONException je) {
            log.error("", je);
        }
        return tempBulk;
    }

    private String generateSegmentDataAgentCallInDay(Agent agent, LocalDateTime callStartDateTime, int numOfInteractions) throws JSONException {

        StringBuffer stringBuffer = new StringBuffer(5500 * numOfInteractions);

        String strValue = ApplicationContextProvider.getApplicationContext()
                .getBean(ArgsComponent.class)
                .getAppArgs()
                .get("doc");
        int durationOfCall = Integer.parseInt(strValue);

        DirectionType randomDirectionType = DirectionType.getRandomDirectionType();

        final int contactId = getRandomInt();

        JSONArray participants = generateParticipantsJsonArray(agent);
        JSONArray recordings = generateRecordingsJsonArray(callStartDateTime, durationOfCall);
        JSONArray recordingStatus = new JSONArray();

        //  region Populate Recording-Status
        Map<String, String> mapRecordingStatus = new HashMap<>();
        mapRecordingStatus.put("Voice", "Successful");
        JSONObject joRecordingStatus = new JSONObject(mapRecordingStatus);
        //  endregion

        //  region packing it all together into one JSONObject
        //JSONObject jsonObj = new JSONObject();
        jsonObj = new JSONObject();
        jsonObj.put(Consts.VERSION, getRandomLong());
        jsonObj.put(Consts.TENANT_ID, getRandomInt());
        jsonObj.put(Consts.SWITCH_ID, getRandomInt());
        jsonObj.put(Consts.CONTACT_ID, contactId);
        jsonObj.put(Consts.CONTACT_START_TIME, callStartDateTime.format(DATE_FORMAT));
        jsonObj.put(Consts.CONTACT_END_TIME, callStartDateTime.plusMinutes(durationOfCall / 2).format(DATE_FORMAT));
        jsonObj.put(Consts.SEGMENT_ID, getRandomLong());
        jsonObj.put(Consts.SEGMENT_START_TIME, callStartDateTime.format(DATE_FORMAT));
        jsonObj.put(Consts.SEGMENT_END_TIME, callStartDateTime.plusMinutes(durationOfCall).format(DATE_FORMAT));
        jsonObj.put(Consts.CALL_DIRECTION, randomDirectionType);
        jsonObj.put(Consts.BUSINESS_DATA, generateRandomString(20));    //  T.B.D.
        jsonObj.put(Consts.DNIS, String.valueOf(getRandomWithRange(972500000, 972540000)));
        jsonObj.put(Consts.PBX_CALL_ID, getRandomInt());
        jsonObj.put(Consts.PBX_UNIQUE_CALL_ID, generateRandomString(25));
        jsonObj.put(Consts.PARTICIPANTS, participants);
        jsonObj.put(Consts.RECORDINGS, recordings);
        jsonObj.put(Consts.EXCEPTIONS, new JSONArray());
        jsonObj.put(Consts.RECORDING_STATUS, recordingStatus);
        jsonObj.put(Consts.RECORDING_STATUS, joRecordingStatus);
        stringBuffer.append(jsonObj.toString() + "\n");
        //  endregion

        return stringBuffer.toString();
    }

    /**
     * Generate participants and populate {@link JSONArray}
     *
     * @param agent
     * @return {@link JSONArray}
     * @throws JSONException
     */
    @NotNull
    private JSONArray generateParticipantsJsonArray(Agent agent) throws JSONException {
        JSONArray participants = new JSONArray();

        //  Participant is an Agent, use agentId, agent first & last name
        JSONObject jo = generateParticipantsJsonObject("Internal", false, Arrays.asList(1, 3));
        jo.put(Consts.AGENT_ID, String.valueOf(getRandomWithRange(1, 101)));
        jo.put(Consts.NVC_FIRST_NAME, agent.getFirstName());
        jo.put(Consts.NVC_LAST_NAME, agent.getLastName());
        participants.put(jo);

        //  Participant is NOT an agent
        jo = generateParticipantsJsonObject("External", false, Arrays.asList(2, 4));
        participants.put(jo);

        //  Participant is NOT an agent
        jo = generateParticipantsJsonObject("External", true, Arrays.asList(2, 3));
        participants.put(jo);

        return participants;
    }

    private JSONObject generateParticipantsJsonObject(String participantType, boolean isInitiator, List recordings) throws JSONException {
        JSONObject jsonObj = new JSONObject();

        jsonObj.put(Consts.USER_ID, getRandomWithRange(1, Integer.MAX_VALUE));
        jsonObj.put(Consts.CTI_USER_IDENTIFIER, null);
        jsonObj.put(Consts.AGENT_ID, "null");
        jsonObj.put(Consts.PHONE_NUMBER, String.valueOf(getRandomWithRange(972500000, 972540000)));
        jsonObj.put(Consts.UNIQUE_DEVICE_ID, "SEP000000008068");
        jsonObj.put(Consts.USER_GROUP_IDS, new JSONArray());
        jsonObj.put(Consts.PARTICIPANT_TYPE, participantType);
        jsonObj.put(Consts.IS_INITIATOR, isInitiator);
        jsonObj.put(Consts.RECORDINGS, recordings);
        jsonObj.put(Consts.NVC_FIRST_NAME, firstNames.get(random.nextInt(firstNames.size())));
        jsonObj.put(Consts.NVC_LAST_NAME, lastNames.get(random.nextInt(lastNames.size())));
        return jsonObj;
    }

    /**
     * Generate recordings and populate {@link JSONArray}
     *
     * @param callStartDateTime
     * @return {@link JSONArray}
     * @throws JSONException
     */
    @NotNull
    private JSONArray generateRecordingsJsonArray(LocalDateTime callStartDateTime, int durationOfCall) throws JSONException {

        JSONArray recordings = new JSONArray();
        JSONObject jo = generateRecordingsJsonObject(callStartDateTime, durationOfCall, DIRECTION_RX);
        recordings.put(jo);

        jo = generateRecordingsJsonObject(callStartDateTime, durationOfCall, DIRECTION_RX);
        recordings.put(jo);

        jo = generateRecordingsJsonObject(callStartDateTime, durationOfCall, DIRECTION_TX);
        recordings.put(jo);

        jo = generateRecordingsJsonObject(callStartDateTime, durationOfCall, DIRECTION_RX);
        recordings.put(jo);

        jo = generateRecordingsJsonObject(callStartDateTime, durationOfCall, DIRECTION_TX);
        recordings.put(jo);

        return recordings;
    }

    @NotNull
    private JSONObject generateRecordingsJsonObject(LocalDateTime callStartDateTime, int durationOfCall, String direction) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Consts.RECORDING_ID, 1);
        jsonObject.put(Consts.MEDIA_TYPE, MediaTypes.getRandomMediaType().toString());
        jsonObject.put(Consts.DIRECTION, direction);
        jsonObject.put(Consts.SESSION_ID, getRandomLong());
        jsonObject.put(Consts.RECORDER_ID, 63);
        jsonObject.put(Consts.RECORDING_START_TIME, callStartDateTime.format(DATE_FORMAT));
        jsonObject.put(Consts.RECORDING_END_TIME, callStartDateTime.plusMinutes(durationOfCall).format(DATE_FORMAT));
        jsonObject.put(Consts.RECORDING_STATUS, "Successful");
        jsonObject.put(Consts.RECORDING_POLICY_ID, 0);

        return jsonObject;
    }

    public String generateBulkData(int numOfInteractions) throws JSONException {

        StringBuilder stringBuilder = new StringBuilder(5500 * numOfInteractions);
        for (int i = 0; i < numOfInteractions; i++) {
            LocalDateTime startDate = generateStartDate();
            DirectionType randomDirectionType = DirectionType.getRandomDirectionType();

            final long sessionId = getRandomLong();

            JSONArray participants = new JSONArray();
            JSONArray recordings = new JSONArray();
            JSONArray recordingStatus = new JSONArray();

            //  region populate Participants
            StringBuffer firstName = new StringBuffer(firstNames.get(random.nextInt(firstNames.size())));
            StringBuffer lastName = new StringBuffer(lastNames.get(random.nextInt(lastNames.size())));
            JSONObject jo = new JSONObject();
            jo.put(Consts.USER_ID, getRandomWithRange(1, Integer.MAX_VALUE));
            jo.put(Consts.CTI_USER_IDENTIFIER, null);
            jo.put(Consts.AGENT_ID, null);
            jo.put(Consts.PHONE_NUMBER, String.valueOf(getRandomWithRange(972500000, 972540000)));
            jo.put(Consts.UNIQUE_DEVICE_ID, "");
            jo.put(Consts.USER_GROUP_IDS, new JSONArray());
            jo.put(Consts.PARTICIPANT_TYPE, "External");
            jo.put(Consts.IS_INITIATOR, false);
            jo.put(Consts.RECORDINGS, Arrays.asList(1, 3));
            jo.put(Consts.FIRST_NAME, firstName.toString());
            jo.put(Consts.LAST_NAME, lastName.toString());
            participants.put(jo);

            firstName = new StringBuffer(firstNames.get(random.nextInt(firstNames.size())));
            lastName = new StringBuffer(lastNames.get(random.nextInt(lastNames.size())));
            jo = new JSONObject();
            jo.put(Consts.USER_ID, getRandomWithRange(1, Integer.MAX_VALUE));
            jo.put(Consts.CTI_USER_IDENTIFIER, null);
            jo.put(Consts.AGENT_ID, null);
            jo.put(Consts.PHONE_NUMBER, String.valueOf(getRandomWithRange(972500000, 972540000)));
            jo.put(Consts.UNIQUE_DEVICE_ID, "SEP000000008068");
            jo.put(Consts.USER_GROUP_IDS, new JSONArray());
            jo.put(Consts.PARTICIPANT_TYPE, "Internal");
            jo.put(Consts.IS_INITIATOR, false);
            jo.put(Consts.RECORDINGS, Arrays.asList(2, 4));
            jo.put(Consts.FIRST_NAME, firstName.toString());
            jo.put(Consts.LAST_NAME, lastName.toString());
            participants.put(jo);

            firstName = new StringBuffer(firstNames.get(random.nextInt(firstNames.size())));
            lastName = new StringBuffer(lastNames.get(random.nextInt(lastNames.size())));
            jo = new JSONObject();
            jo.put("userId", getRandomWithRange(1, Integer.MAX_VALUE));
            jo.put("ctiUserIdentifier", null);
            jo.put("agentId", null);
            jo.put("phoneNumber", String.valueOf(getRandomWithRange(972500000, 972540000)));
            jo.put("uniqueDeviceId", "SEP000000008068");
            jo.put("userGroupIds", new JSONArray());
            jo.put("participantType", "Internal");
            jo.put("isInitiator", true);
            jo.put("recordings", Arrays.asList(2, 4));
            jo.put(Consts.FIRST_NAME, firstName.toString());
            jo.put(Consts.LAST_NAME, lastName.toString());
            participants.put(jo);
            //  endregion

            //  region Populate Recordings
            jo = new JSONObject();
            jo.put("recordingId", 1);
            String MEDIA_TYPE_VOICE = "Voice";
            jo.put("mediaType", MEDIA_TYPE_VOICE);
            jo.put("direction", DIRECTION_RX);
            jo.put("sessionId", sessionId);
            jo.put("recorderId", 63);
            jo.put("startTime", startDate.format(DATE_FORMAT));
            jo.put("endTime", startDate.plusMinutes(5).format(DATE_FORMAT));
            jo.put("recordingStatus", "Successful");
            jo.put("recordingPolicyId", 0);
            recordings.put(jo);

            jo = new JSONObject();
            jo.put("recordingId", 2);
            jo.put("mediaType", MEDIA_TYPE_VOICE);
            jo.put("direction", DIRECTION_TX);
            jo.put("sessionId", sessionId);
            jo.put("recorderId", 63);
            jo.put("startTime", startDate.format(DATE_FORMAT));
            jo.put("endTime", startDate.plusMinutes(5).format(DATE_FORMAT));
            jo.put("recordingStatus", "Successful");
            jo.put("recordingPolicyId", 0);
            recordings.put(jo);

            jo = new JSONObject();
            jo.put("recordingId", 3);
            jo.put("mediaType", MEDIA_TYPE_VOICE);
            jo.put("direction", DIRECTION_RX);
            jo.put("sessionId", sessionId);
            jo.put("recorderId", 63);
            jo.put("startTime", startDate.format(DATE_FORMAT));
            jo.put("endTime", startDate.plusMinutes(5).format(DATE_FORMAT));
            jo.put("recordingStatus", "Successful");
            jo.put("recordingPolicyId", 0);
            recordings.put(jo);

            jo = new JSONObject();
            jo.put("recordingId", 4);
            jo.put("mediaType", MEDIA_TYPE_VOICE);
            jo.put("direction", DIRECTION_TX);
            jo.put("sessionId", sessionId);
            jo.put("recorderId", 63);
            jo.put("startTime", startDate.format(DATE_FORMAT));
            jo.put("endTime", startDate.plusMinutes(5).format(DATE_FORMAT));
            jo.put("recordingStatus", "Successful");
            jo.put("recordingPolicyId", 0);
            recordings.put(jo);
            //  endregion

            //  region Populate Recording-Status
            Map<String, String> mapRecordingStatus = new HashMap<>();
            mapRecordingStatus.put("Voice", "Successful");
            JSONObject joRecordingStatus = new JSONObject(mapRecordingStatus);
            jo = new JSONObject(mapRecordingStatus);
            //  endregion

            //JSONObject jsonObj = new JSONObject();
            jsonObj = new JSONObject();
            jsonObj.put(Consts.VERSION, getRandomLong());
            jsonObj.put(Consts.TENANT_ID, getRandomInt());
            jsonObj.put(Consts.SWITCH_ID, getRandomInt());
            jsonObj.put(Consts.CONTACT_ID, getRandomInt());
            jsonObj.put(Consts.CONTACT_START_TIME, startDate.format(DATE_FORMAT));
            jsonObj.put(Consts.CONTACT_END_TIME, startDate.plusSeconds(150).format(DATE_FORMAT));
            jsonObj.put(Consts.SEGMENT_ID, getRandomLong());
            jsonObj.put(Consts.SEGMENT_START_TIME, startDate.format(DATE_FORMAT));
            jsonObj.put(Consts.SEGMENT_END_TIME, startDate.plusMinutes(5).format(DATE_FORMAT));
            jsonObj.put(Consts.CALL_DIRECTION, randomDirectionType);
            jsonObj.put(Consts.BUSINESS_DATA, generateRandomString(20));    //  T.B.D.
            jsonObj.put(Consts.DNIS, String.valueOf(getRandomWithRange(972500000, 972540000)));
            jsonObj.put(Consts.PBX_CALL_ID, getRandomInt());
            jsonObj.put(Consts.PBX_UNIQUE_CALL_ID, generateRandomString(25));
            jsonObj.put(Consts.PARTICIPANTS, participants);
            jsonObj.put(Consts.RECORDINGS, recordings);
            jsonObj.put(Consts.EXCEPTIONS, new JSONArray());
            jsonObj.put(Consts.RECORDING_STATUS, recordingStatus);
            jsonObj.put(Consts.RECORDING_STATUS, joRecordingStatus);
            stringBuilder.append(jsonObj.toString() + "\n");
        }
        return stringBuilder.toString();
    }

    private int getRandomInt() {
        return random.nextInt(Integer.MAX_VALUE);
    }

    private Long getRandomLong() {
        return random.nextLong() & Long.MAX_VALUE;
    }

    private double getRandomDouble() {
        return random.nextDouble();
    }

    private int getRandomWithRange(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    private String generateRandomString(int length) {
        final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            sb.append(AB.charAt(random.nextInt(AB.length())));
        return sb.toString();
    }

    private int getRandomBit() {
        return random.nextInt(2);
    }

    private String generateRandomName() {
        String firstName = firstNames.get(random.nextInt(firstNames.size()));
        String lastName = lastNames.get(random.nextInt(lastNames.size()));

        return firstName + " " + lastName;
    }

    private List<String> generateNames(String path) {
        List<String> names = new ArrayList<>();
        BufferedReader bufferReader = null;
        try {
            bufferReader = new BufferedReader(new FileReader(path));
            String line = null;
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
        return names;
    }

    private LocalDateTime generateStartDate() {
        int currentYear, currentMonth, randomYear, randomMonth;
        do {
            currentYear = LocalDate.now().getYear();
            currentMonth = LocalDate.now().getMonth().getValue();
            randomYear = getRandomWithRange(currentYear - 1, currentYear);
            randomMonth = getRandomWithRange(1, 12);
        } while ((randomYear == currentYear) && (randomMonth > currentMonth));

        return LocalDateTime.of(randomYear, randomMonth, getRandomWithRange(1, 28), getRandomWithRange(0, 23), getRandomWithRange(1, 59), 0, 0);
    }

    private LocalDateTime generateStopDate(LocalDateTime startTime) {
        return startTime.plusMinutes(getRandomWithRange(1, 5)).plusSeconds(getRandomWithRange(1, 20));
    }

}
