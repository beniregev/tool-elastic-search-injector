package com.nice.mcr.injector.service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.Socket;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Random;

@Service
public class DataGeneratorImpl implements DataGenerator {

    private Random random = new Random();
    @Value("${socket.hostname}")
    private String hostname;
    @Value("${socket.port}")
    private int port;
    private int CURRENT_YEAR = 2019;
    private static DateTimeFormatter DATE_FORMAT = new DateTimeFormatterBuilder().appendPattern( "yyyy-MM-dd" ).optionalStart().appendPattern( " HH:mm:ss.SSS" ).optionalEnd().parseDefaulting( ChronoField.HOUR_OF_DAY, 0 ).parseDefaulting( ChronoField.MINUTE_OF_HOUR, 0 ).parseDefaulting( ChronoField.SECOND_OF_MINUTE, 0 ).parseDefaulting( ChronoField.MILLI_OF_SECOND, 0 ).toFormatter();


    public void createData(int numberOfBulks, int numOfInteractions) {
        BufferedWriter writer = null;
        Socket clientSocket = null;
        try {
            clientSocket = new Socket( hostname, port );
            for (int i = 0; i < numberOfBulks; i++) {
                writer = new BufferedWriter( new FileWriter( "..\\tool-elastic-search-injector\\output\\output" + (i + 1) + ".json" ) );
                String bulkData = generateBulkData( numOfInteractions );
                writer.write( bulkData );
                System.out.println( bulkData );
                DataOutputStream outToServer = new DataOutputStream( clientSocket.getOutputStream() );
                outToServer.writeBytes( bulkData );
            }

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (clientSocket != null) {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (writer != null) {
                try {
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String generateBulkData(int numOfInteractions) throws JSONException {

        ArrayList <String> firstNames = generateNames( numOfInteractions, "..\\tool-elastic-search-injector\\input\\first-names.txt" );
        ArrayList <String> lastNames = generateNames( numOfInteractions, "..\\tool-elastic-search-injector\\input\\last-names.txt" );
        ArrayList <String> middleNames = generateNames( numOfInteractions, "..\\tool-elastic-search-injector\\input\\middle-names.txt" );
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < numOfInteractions; i++) {
            LocalDateTime startDate = generateStartDate( CURRENT_YEAR, getRandomWithRange( 1, 12 ) );
            LocalDateTime stopDate = generateStopDate( startDate );
            OpenCallReason openCallReason = OpenCallReason.getRandomReason();
            InteractionType interactionType = InteractionType.getRandomInteractionType();
            CloseCallReason closeCallReason = CloseCallReason.getRandomReason();
            MediaTypes randomMediaType = MediaTypes.getRandomMediaType();
            InitiatorType initiatorType = InitiatorType.getRandomInitiatorType();
            ParticipantType participantType = ParticipantType.getRandomParticipantType();
            DeviceType deviceType = DeviceType.getRandomDeviceType();
            RecordingSideType recordingSideType = RecordingSideType.getRandomRecordingSideType();
            DirectionType randomDirectionType = DirectionType.getRandomDirectionType();
            ItemDataType randomItemDataType = ItemDataType.getRandomDataType();
            CreatorType creatorType = CreatorType.getRandomCreatorType();
            ItemType randomItemType = ItemType.getRandomItemType();
            RecordedType recordedType = RecordedType.getRandomRecordedType();
            ExceptionType randomExceptionType = ExceptionType.getRandomExcetionType();
            JSONObject jsonObj = new JSONObject();
            jsonObj.put( Consts.INTERACTION_ID, getRandomInt() );
            jsonObj.put( Consts.INTERACTION_GMT_START_TIME, startDate.format( DATE_FORMAT ) );
            jsonObj.put( Consts.INTERACTION_GMT_STOP_TIME, stopDate.format( DATE_FORMAT ) );
            jsonObj.put( Consts.INTERACTION_DURATION, Duration.between( startDate, stopDate ) );
            jsonObj.put( Consts.INTERACTION_OPEN_REASON_ID, openCallReason.getOpenCallReasonID() );
            jsonObj.put( Consts.INTERACTION_CLOSE_REASON_ID, closeCallReason.getCloseCallReasonID() );
            jsonObj.put( Consts.SWITCH_ID, getRandomWithRange( 1, 10 ) );
            jsonObj.put( Consts.INITIATOR_USER_ID, getRandomWithRange( 1, 10 ) );
            jsonObj.put( Consts.OTHER_SWITCH_ID, getRandomWithRange( 1, 5 ) );
            jsonObj.put( Consts.INTERACTION_TYPE_ID, interactionType.getRandomInteractionTypeID() );
            jsonObj.put( Consts.INTERACTION_RECORDED_TYPE_ID, recordedType.RecorderTypeID() );
            jsonObj.put( Consts.MEDIA_TYPES_ID, randomMediaType.getMediaTypeID() );
            jsonObj.put( Consts.INTERACTION_DESC, randomMediaType );
            jsonObj.put( Consts.INITIATOR_TYPE_ID, initiatorType.getInitiatorTypeID() );
            jsonObj.put( Consts.INITIATOR_TYPE_DESC, initiatorType );
            jsonObj.put( Consts.CLIENT_DTMF, generateRandomString( 5 ) );
            jsonObj.put( Consts.PBX_CALL_ID, getRandomInt() );
            jsonObj.put( Consts.EXTERNAL_CALL_ID, getRandomInt() );
            jsonObj.put( Consts.CALL_DIRECTION_TYPE_ID, randomDirectionType.getDirectionTypeID() );
            jsonObj.put( Consts.PBX_UNIVARSAL_CALL_INTERACTION_ID, getRandomInt() );
            jsonObj.put( Consts.COMPOUND_ID, getRandomInt() );
            jsonObj.put( Consts.NVC_BUSINESS_DATA, generateRandomString( 20 ) );
            jsonObj.put( Consts.PARTICIPANT_ID, getRandomInt() );
            jsonObj.put( Consts.STATION, generateRandomString( 20 ) );
            jsonObj.put( "nvcPhoneNumber", getRandomWithRange( 972500000, 972540000 ) );
            jsonObj.put( Consts.AGENT_ID, getRandomInt() );
            jsonObj.put( Consts.USER_ID, getRandomInt() );
            jsonObj.put( Consts.DEVICE_TYPE_ID, deviceType.DeviceTypeID() );
            jsonObj.put( Consts.DEVICE_ID, getRandomInt() );
            jsonObj.put( Consts.CTI_AGENT_NAME, firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( Consts.DEPARTMENT, generateRandomString( 64 ) );
            jsonObj.put( Consts.TRUNK_GROUP, generateRandomString( 16 ) );
            jsonObj.put( Consts.TRUNK_NUMBER, generateRandomString( 16 ) );
            jsonObj.put( Consts.TRUNK_LABEL, generateRandomString( 32 ) );
            jsonObj.put( "nvcDialedNumber", generateRandomString( 32 ) );
            jsonObj.put( Consts.CLIENT_ID, getRandomInt() );
            jsonObj.put( Consts.VIRTUAL_DEVICE_ID, getRandomInt() );
            jsonObj.put( Consts.PARTICIPANT_TYPE_ID, participantType.getparticipantTypeID() );
            jsonObj.put( Consts.PARTICIPANT_TYPE_DESC, participantType );
            jsonObj.put( "iOpenReasonTypeID", openCallReason.getOpenCallReasonID() );
            jsonObj.put( Consts.OPEN_REASON_DESC, openCallReason );
            jsonObj.put( "vcCloseReasonTypeID", openCallReason.getOpenCallReasonID() );
            jsonObj.put( Consts.CLOSE_REASON_DESC, closeCallReason );
            jsonObj.put( Consts.DEVICE_TYPE_DESC, deviceType );
            jsonObj.put( Consts.RECORDING_SIDE_TYPE_ID, recordingSideType.RecordingSideTypeID() );
            jsonObj.put( Consts.RECORDING_SIDE_DESC, recordingSideType );
            jsonObj.put( "iMediaTypeId", randomMediaType.getMediaTypeID() );
            jsonObj.put( Consts.MEDIA_DESC, randomMediaType );
            jsonObj.put( "tiDirectionTypeID", randomDirectionType.getDirectionTypeID() );
            jsonObj.put( Consts.DIRECTION_TYPE_DESC, randomDirectionType );
            jsonObj.put( Consts.RECORDING_ID, getRandomInt() );
            jsonObj.put( Consts.LOGGER, getRandomWithRange( 1, 10 ) );
            jsonObj.put( Consts.CHANNEL, "-1" );
            jsonObj.put( Consts.RECORDING_GMT_START_TIME, startDate.format( DATE_FORMAT ) );
            jsonObj.put( Consts.RECORDING_GMT_STOP_TIME, stopDate.format( DATE_FORMAT ) );
            jsonObj.put( Consts.RECORDING_RECORDED_TYPE_ID, RecordedType.getRandomRecordedType() );
            jsonObj.put( Consts.PROGRAM_ID, getRandomInt() );
            jsonObj.put( Consts.RECORDED_PARTICIPANT_ID, getRandomInt() );
            jsonObj.put( Consts.WRAPUP_TIME, getRandomLong() );
            jsonObj.put( Consts.SESSION_ID, getRandomLong() );
            jsonObj.put( Consts.ITEM_DATA_TYPE_DESC, randomItemDataType );
            jsonObj.put( Consts.CREATOR_DESC, creatorType );
            jsonObj.put( Consts.ITEM_TYPE_DESC, randomItemType );
            jsonObj.put( Consts.CONTACT_ID, getRandomLong() );
            jsonObj.put( Consts.TIME_STAMP, 0 );
            jsonObj.put( Consts.ITEM_USER_ID, getRandomInt() );
            jsonObj.put( Consts.ITEM_VALUE, generateRandomString( 256 ) );
            jsonObj.put( Consts.ITEM_IS_DELETED, getRandomBit() );
            jsonObj.put( Consts.RECORDED_TYPE_ID, recordedType.RecorderTypeID() );
            jsonObj.put( Consts.RECORDED_TYPE_DESC, recordedType );
            jsonObj.put( Consts.ARCHIVE_ID, getRandomInt() );
            jsonObj.put( Consts.MEDIA_TYPE_ID, randomMediaType.getMediaTypeID() );
            jsonObj.put( Consts.ARCHIVE_PATH, "E:\\storage1\\CD-APPS\\84\\2019\\1\\28\\CD-AIR2\\2\\1_6651516551865369305_6651516560398680065.nmf" );
            jsonObj.put( Consts.ARCHIVE_ID_HIGH, getRandomInt() );
            jsonObj.put( Consts.ARCHIVE_ID_LOW, getRandomInt() );
            jsonObj.put( Consts.ARCHIVE_CLASS, getRandomInt() );
            jsonObj.put( Consts.SC_SERVER_ID, getRandomInt() );
            jsonObj.put( Consts.SC_SITE_ID, getRandomInt() );
            jsonObj.put( Consts.SC_RULE_ID, getRandomInt() );
            jsonObj.put( Consts.SC_LOGGER_ID, getRandomInt() );
            jsonObj.put( Consts.SC_LOGGER_RESOURCE, getRandomInt() );
            jsonObj.put( Consts.ARCHIVE_UNIQUE_ID, getRandomInt() );
            jsonObj.put( Consts.RETENTION_DAYS, getRandomWithRange( 0, 365 * 30 ) );
            jsonObj.put( Consts.COMPLETE_GMT_START_TIME, startDate.format( DATE_FORMAT ) );
            jsonObj.put( Consts.COMPLETE_GMT_STOP_TIME, stopDate.format( DATE_FORMAT ) );
            jsonObj.put( Consts.COMPLETE_DURATION, Duration.between( startDate, stopDate ) );
            jsonObj.put( Consts.COMPLETE_OPEN_REASON_ID, openCallReason.getOpenCallReasonID() );
            jsonObj.put( Consts.COMPLETE_CLOSE_REASON_ID, closeCallReason.getCloseCallReasonID() );
            jsonObj.put( Consts.TRANSFER_SITE_ID, getRandomInt() );
            jsonObj.put( Consts.TRANSFER_CONTACT_ID, getRandomInt() );
            jsonObj.put( Consts.COMPLETE_RECORDED_TYPE_ID, getRandomBit() );
            jsonObj.put( Consts.COMPLETE_DIRECTION_TYPE_ID, randomDirectionType.getDirectionTypeID() );
            jsonObj.put( Consts.EXCEPTION_TYPE_ID, randomExceptionType.exceptionTypeID() );
            jsonObj.put( Consts.EXCEPTION_TYPE_DESC, randomExceptionType.exceptionDescription() );
            jsonObj.put( Consts.EXCEPTION_POSSIBLE_CAUSE, randomExceptionType.exceptionPossibleCause() );
            jsonObj.put( Consts.EXCEPTION_RECOMMENDED_ACTION, randomExceptionType.exceptionRecommendedAction() );
            jsonObj.put( Consts.EXCEPTION_NUMBER, random.nextInt() );
            jsonObj.put( Consts.EXCEPTION_TIMESTAMP, "8192" );
            jsonObj.put( Consts.EXCEPTION_DETAIL, generateRandomString( 32 ) );
            jsonObj.put( Consts.TASK_ID, getRandomInt() );
            jsonObj.put( Consts.FLAG_ID, getRandomInt() );
            jsonObj.put( Consts.USER_SITE_ID, getRandomInt() );
            jsonObj.put( Consts.SCORE, getRandomInt() );
            jsonObj.put( Consts.MODIFY_DATE, getRandomDouble() );
            jsonObj.put( Consts.NOTIFICATION_DATE, getRandomDouble() );
            jsonObj.put( Consts.LOCK_STATUS, getRandomBit() );
            jsonObj.put( "iReasonItemId", getRandomInt() );
            jsonObj.put( "biCustomerID", getRandomLong() );
            jsonObj.put( "iSetNumber", "1" );
            jsonObj.put( "tiVoiceArchiveStatus", "0" );
            jsonObj.put( "tiVoiceFSArchiveStatus", "0" );
            jsonObj.put( "dtVoiceExpirationDate", "2018-31-12 23:59:00.123" );
            jsonObj.put( "iVoiceRemainderDays", "0" );
            jsonObj.put( "tiScreenArchiveStatus", "0" );
            jsonObj.put( "tiScreenFSArchiveStatus", "0" );
            jsonObj.put( "dtScreenExpirationDate", "2018-31-12 23:59:00.123" );
            jsonObj.put( "iScreenRemainderDays", "0" );
            jsonObj.put( "tiVoiceESMArchiveStatus", "0" );
            jsonObj.put( "tiScreenESMArchiveStatus", "0" );
            jsonObj.put( "tiArchiveStatus", "0" );
            jsonObj.put( "tiESmArchiveStatus", "0" );
            jsonObj.put( "tiFSArchiveStatus", "0" );
            jsonObj.put( "dtExpirationDate", "2018-31-12 23:59:00.123" );
            jsonObj.put( "dtInsertTime", getRandomInt() );
            jsonObj.put( "iRequestId", getRandomInt() );
            jsonObj.put( "LastExtendingUser", generateRandomString( 50 ) );
            jsonObj.put( "LastInsertDate", getRandomDouble() );
            jsonObj.put( "ReasonCaption", getRandomInt() );
            jsonObj.put( Consts.FIRST_NAME, firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( Consts.LAST_NAME, lastNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( Consts.MIDDLE_NAME, middleNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "vcEmailAddress", "bari@gmail.com" );
            jsonObj.put( "nvcLoginName", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( Consts.EXTENSION, "1" );
            jsonObj.put( Consts.SWITCH_AGENT_ID, getRandomWithRange( 1, 10 ) );
            jsonObj.put( Consts.STATUS, "1" );
            jsonObj.put( Consts.FORMATTER_NAME, "1" );

            stringBuilder.append( jsonObj.toString() + "\n" );
        }
        return stringBuilder.toString();
    }

    private int getRandomInt() {
        return random.nextInt( 2147483647 );
    }

    private Long getRandomLong() {
        return random.nextLong() & Long.MAX_VALUE;
    }

    private double getRandomDouble() {
        return random.nextDouble();
    }

    private int getRandomWithRange(int min, int max) {
        return random.nextInt( max - min ) + min;
    }

    private String generateRandomString(int length) {
        final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder( length );
        for (int i = 0; i < length; i++)
            sb.append( AB.charAt( random.nextInt( AB.length() ) ) );
        return sb.toString();
    }

    private int getRandomBit() {
        return random.nextInt( 2 );
    }

    private ArrayList <String> generateNames(int numOfInteractions, String path) {
        ArrayList <String> firstNames = new ArrayList <>();
        BufferedReader bufferReader;
        try {
            bufferReader = new BufferedReader( new FileReader( path ) );
            int i = 0;
            while (i < numOfInteractions) {
                firstNames.add( bufferReader.readLine() );
                i++;
            }
            bufferReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return firstNames;
    }

    private LocalDateTime generateStartDate(int year, int month) {
        return LocalDateTime.of( year, month, getRandomWithRange( 1, 28 ), getRandomWithRange( 0, 23 ), getRandomWithRange( 1, 59 ), 0, 0 );
    }

    private LocalDateTime generateStopDate(LocalDateTime startTime) {
        return startTime.plusMinutes( getRandomWithRange( 1, 5 ) ).plusSeconds( getRandomWithRange( 1, 20 ) );
    }

}
