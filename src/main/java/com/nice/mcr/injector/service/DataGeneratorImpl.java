package com.nice.mcr.injector.service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

@Service
public class DataGeneratorImpl implements DataGenerator {

    private Random random = new Random();
    @Value("${socket.hostname}")
    private String hostname;
    @Value("${socket.port}")
    private int port;
    public void createData(int numberOfBulks, int numOfInteractions) {

        for (int i = 0; i < numberOfBulks; i++) {
            BufferedWriter writer = null;
            Socket clientSocket = null;
            try {
                writer = new BufferedWriter( new FileWriter( "..\\tool-elastic-search-injector\\output " + (i + 1) + ".json" ) );
                String bulkData = generateBulkData( numOfInteractions );
                writer.write( bulkData);
                System.out.println(bulkData);
                clientSocket = new Socket(hostname, port);
                DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                outToServer.writeBytes(bulkData);

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
    }

    private String generateBulkData(int numOfInteractions) throws JSONException {

        ArrayList <String> firstNames = generateNames( numOfInteractions, "C:\\Users\\Administrator\\Desktop\\input\\first-names.txt" );
        ArrayList <String> lastNames = generateNames( numOfInteractions, "C:\\Users\\Administrator\\Desktop\\input\\last-names.txt" );
        ArrayList <String> middleNames = generateNames( numOfInteractions, "C:\\Users\\Administrator\\Desktop\\input\\middle-names.txt" );
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < numOfInteractions; i++) {
            Date startDate = generateStartDate();
            Date stopDate = generateStopDate( startDate );
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
            jsonObj.put( Consts.INTERACTION_ID, getIntRandom() );
            jsonObj.put( Consts.INTERACTION_GMT_START_TIME, formatDate( startDate ) );
            jsonObj.put( Consts.INTERACTION_GMT_STOP_TIME, formatDate( stopDate ) );
            jsonObj.put( Consts.INTERACTION_DURATION, String.valueOf( Math.abs( stopDate.getTime() - startDate.getTime() ) ) );
            jsonObj.put( Consts.INTERACTION_OPEN_REASON_ID, openCallReason.getOpenCallReasonID() );
            jsonObj.put( Consts.INTERACTION_CLOSE_REASON_ID, closeCallReason.getCloseCallReasonID() );
            jsonObj.put( Consts.SWITCH_ID, getRandomWithRange( 10, 1 ) );
            jsonObj.put( Consts.INITIATOR_USER_ID, getRandomWithRange( 10, 1 ) );
            jsonObj.put( Consts.OTHER_SWITCH_ID, getRandomWithRange( 5, 1 ) );
            jsonObj.put( Consts.INTERACTION_TYPE_ID, interactionType.getRandomInteractionTypeID() );
            jsonObj.put( Consts.INTERACTION_RECORDED_TYPE_ID, recordedType.RecorderTypeID() );
            jsonObj.put( Consts.MEDIA_TYPES_ID, randomMediaType.getMediaTypeID() );
            jsonObj.put( Consts.INTERACTION_DESC, randomMediaType );
            jsonObj.put( Consts.INITIATOR_TYPE_ID, initiatorType.getInitiatorTypeID() );
            jsonObj.put( Consts.INITIATOR_TYPE_DESC, initiatorType );
            jsonObj.put( Consts.CLIENT_DTMF, generateRandomString( 5 ) );
            jsonObj.put( Consts.PBX_CALL_ID, getIntRandom() );
            jsonObj.put( Consts.EXTERNAL_CALL_ID, getIntRandom() );
            jsonObj.put( Consts.CALL_DIRECTION_TYPE_ID, randomDirectionType.getDirectionTypeID() );
            jsonObj.put( Consts.PBX_UNIVARSAL_CALL_INTERACTION_ID, generateRandomString( 64 ) );
            jsonObj.put( Consts.COMPOUND_ID, getIntRandom() );
            jsonObj.put( Consts.NVC_BUSINESS_DATA, generateRandomString( 20 ) );
            jsonObj.put( Consts.PARTICIPANT_ID, getIntRandom() );
            jsonObj.put( Consts.STATION, generateRandomString( 20 ) );
            jsonObj.put( "nvcPhoneNumber", generateRandomString( 20 ) );
            jsonObj.put( Consts.AGENT_ID, generateRandomString( 20 ) );
            jsonObj.put( Consts.USER_ID, getIntRandom() );
            jsonObj.put( Consts.DEVICE_TYPE_ID, deviceType.DeviceTypeID() );
            jsonObj.put( Consts.DEVICE_ID, getIntRandom() );
            jsonObj.put( Consts.CTI_AGENT_NAME, firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( Consts.DEPARTMENT, generateRandomString( 64 ) );
            jsonObj.put( Consts.TRUNK_GROUP, generateRandomString( 16 ) );
            jsonObj.put( Consts.TRUNK_NUMBER, generateRandomString( 16 ) );
            jsonObj.put( Consts.TRUNK_LABEL, generateRandomString( 32 ) );
            jsonObj.put( "nvcDialedNumber", generateRandomString( 32 ) );
            jsonObj.put( Consts.CLIENT_ID, getIntRandom() );
            jsonObj.put( Consts.VIRTUAL_DEVICE_ID, getIntRandom() );
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
            jsonObj.put( Consts.RECORDING_ID, getIntRandom() );
            jsonObj.put( Consts.LOGGER, getRandomWithRange( 10, 1 ) );
            jsonObj.put( Consts.CHANNEL, "-1" );
            jsonObj.put( Consts.RECORDING_GMT_START_TIME, formatDate( startDate ) );
            jsonObj.put( Consts.RECORDING_GMT_STOP_TIME, formatDate( stopDate ) );
            jsonObj.put( Consts.RECORDING_RECORDED_TYPE_ID, RecordedType.getRandomRecordedType() );
            jsonObj.put( Consts.PROGRAM_ID, getIntRandom() );
            jsonObj.put( Consts.RECORDED_PARTICIPANT_ID, getIntRandom() );
            jsonObj.put( "biWrapupTime", getDoubleRandomNumber() );
            jsonObj.put( Consts.SESSION_ID, getDoubleRandomNumber() );
            jsonObj.put( Consts.ITEM_DATA_TYPE_DESC, randomItemDataType );
            jsonObj.put( Consts.CREATOR_DESC, creatorType );
            jsonObj.put( Consts.ITEM_TYPE_DESC, randomItemType );
            jsonObj.put( Consts.CONTACT_ID, getLongRandom() );
            jsonObj.put( Consts.TIME_STAMP, stopDate.getTime() - startDate.getTime() );
            jsonObj.put( Consts.ITEM_USER_ID, getIntRandom() );
            jsonObj.put( Consts.ITEM_VALUE, generateRandomString( 256 ) );
            jsonObj.put( Consts.ITEM_IS_DELETED, getRandomBit() );
            jsonObj.put( Consts.RECORDED_TYPE_ID, recordedType.RecorderTypeID() );
            jsonObj.put( Consts.RECORDED_TYPE_DESC, recordedType );
            jsonObj.put( Consts.ARCHIVE_ID, getIntRandom() );
            jsonObj.put( Consts.MEDIA_TYPE_ID, randomMediaType.getMediaTypeID() );
            jsonObj.put( Consts.ARCHIVE_PATH, "E:\\storage1\\CD-APPS\\84\\2019\\1\\28\\CD-AIR2\\2\\1_6651516551865369305_6651516560398680065.nmf" );
            jsonObj.put( Consts.ARCHIVE_ID_HIGH, getIntRandom() );
            jsonObj.put( Consts.ARCHIVE_ID_LOW, getIntRandom() );
            jsonObj.put( Consts.ARCHIVE_CLASS, getIntRandom() );
            jsonObj.put( Consts.SC_SERVER_ID, generateRandomString( 100 ) );
            jsonObj.put( Consts.SC_SITE_ID, getIntRandom() );
            jsonObj.put( Consts.SC_RULE_ID, getIntRandom() );
            jsonObj.put( Consts.SC_LOGGER_ID, getIntRandom() );
            jsonObj.put( Consts.SC_LOGGER_RESOURCE, getIntRandom() );
            jsonObj.put( Consts.ARCHIVE_UNIQUE_ID, generateRandomString( 255 ) );
            jsonObj.put( Consts.RETENTION_DAYS, getRandomWithRange( 365 * 30, 0 ) );
            jsonObj.put( Consts.COMPLETE_GMT_START_TIME, formatDate( startDate ) );
            jsonObj.put( Consts.COMPLETE_GMT_STOP_TIME, formatDate( stopDate ) );
            jsonObj.put( Consts.COMPLETE_DURATION, String.valueOf( Math.abs( stopDate.getTime() - startDate.getTime() ) ) );
            jsonObj.put( Consts.COMPLETE_OPEN_REASON_ID, openCallReason.getOpenCallReasonID() );
            jsonObj.put( Consts.COMPLETE_CLOSE_REASON_ID, closeCallReason.getCloseCallReasonID() );
            jsonObj.put( Consts.TRANSFER_SITE_ID, getIntRandom() );
            jsonObj.put( Consts.TRANSFER_CONTACT_ID, getIntRandom() );
            jsonObj.put( Consts.COMPLETE_RECORDED_TYPE_ID, getRandomBit() );
            jsonObj.put( Consts.COMPLETE_DIRECTION_TYPE_ID, randomDirectionType.getDirectionTypeID() );
            jsonObj.put( Consts.EXCEPTION_TYPE_ID, randomExceptionType.exceptionTypeID() );
            jsonObj.put( Consts.EXCEPTION_TYPE_DESC, randomExceptionType.exceptionDescription() );
            jsonObj.put( Consts.EXCEPTION_POSSIBLE_CAUSE, randomExceptionType.exceptionPossibleCause() );
            jsonObj.put( Consts.EXCEPTION_RECOMMENDED_ACTION, randomExceptionType.exceptionRecommendedAction() );
            jsonObj.put( Consts.EXCEPTION_NUMBER, random.nextInt() );
            jsonObj.put( Consts.EXCEPTION_TIMESTAMP, "8192" );
            jsonObj.put( Consts.EXCEPTION_DETAIL, generateRandomString( 32 ) );
            jsonObj.put( Consts.TASK_ID, getIntRandom() );
            jsonObj.put( Consts.FLAG_ID, getIntRandom() );
            jsonObj.put( Consts.USER_SITE_ID, getIntRandom() );
            jsonObj.put( Consts.SCORE, getIntRandom() );
            jsonObj.put( Consts.MODIFY_DATE, getDoubleRandomNumber() );
            jsonObj.put( Consts.NOTIFICATION_DATE, getDoubleRandomNumber() );
            jsonObj.put( Consts.LOCK_STATUS, getRandomBit() );
            jsonObj.put( "iReasonItemId", getIntRandom() );
            jsonObj.put( "biCustomerID", getLongRandom() );
            jsonObj.put( "iSetNumber", "1" );
            jsonObj.put( "tiVoiceArchiveStatus", "0" );
            jsonObj.put( "tiVoiceFSArchiveStatus", "0" );
            jsonObj.put( "dtVoiceExpirationDate", "2018-31-12 23:59:00,123" );
            jsonObj.put( "iVoiceRemainderDays", "0" );
            jsonObj.put( "tiScreenArchiveStatus", "0" );
            jsonObj.put( "tiScreenFSArchiveStatus", "0" );
            jsonObj.put( "dtScreenExpirationDate", "2018-31-12 23:59:00,123" );
            jsonObj.put( "iScreenRemainderDays", "0" );
            jsonObj.put( "tiVoiceESMArchiveStatus", "0" );
            jsonObj.put( "tiScreenESMArchiveStatus", "0" );
            jsonObj.put( "tiArchiveStatus", "0" );
            jsonObj.put( "tiESmArchiveStatus", "0" );
            jsonObj.put( "tiFSArchiveStatus", "0" );
            jsonObj.put( "dtExpirationDate", "2018-31-12 23:59:00,123" );
            jsonObj.put( "dtInsertTime", getIntRandom() );
            jsonObj.put( "iRequestId", getIntRandom() );
            jsonObj.put( "LastExtendingUser", generateRandomString( 50 ) );
            jsonObj.put( "LastInsertDate", getDoubleRandomNumber() );
            jsonObj.put( "ReasonCaption", getIntRandom() );
            jsonObj.put( Consts.FIRST_NAME, firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( Consts.LAST_NAME, lastNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( Consts.MIDDLE_NAME, middleNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "vcEmailAddress", "bari@gmail.com" );
            jsonObj.put( "nvcLoginName", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( Consts.EXTENTION, "1" );
            jsonObj.put( Consts.SWITCH_AGENT_ID, getRandomWithRange( 10, 1 ) );
            jsonObj.put( Consts.STATUS, "1" );
            jsonObj.put( Consts.FORMATTER_NAME, "1" );

            stringBuilder.append(jsonObj.toString() + "\n");
        }
        return stringBuilder.toString();
    }

    private Object formatDate(Date startTime) {
        SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss.SS" );
        return formatter.format( startTime );
    }

    private int getIntRandom() {
        return random.nextInt( 2147483647 );
    }

    private Long getLongRandom() {
        return random.nextLong();
    }

    private int getRandomWithRange(int max, int min) {
        return random.nextInt( max - min ) + min;
    }

    private double getDoubleRandomNumber() {
        return random.nextDouble();
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
        BufferedReader in = null;
        try {
            in = new BufferedReader( new FileReader( path ) );
            int i = 0;
            while (i < numOfInteractions) {
                firstNames.add( in.readLine() );
                i++;
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return firstNames;
    }

    private Date generateStartDate() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss.SS" );
            Date minTime = formatter.parse( "2018-01-01 00:01:00.231" );
            long startDateMillis = minTime.getTime() + getRandomWithRange( 1000000000, 1000 );
            return new Date( startDateMillis );
        } catch (ParseException e1) {
            e1.printStackTrace();
            return null;
        }
    }

    private Date generateStopDate(Date startTime) {
        return new Date( startTime.getTime() + getRandomWithRange( 1000000000, 1000 ) );
    }

}
