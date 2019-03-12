package com.nice.mcr.injector.service;

import ch.qos.logback.classic.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

@Service
public class DataGeneratorImpl implements DataGenerator {

    static Logger logger = (Logger) LoggerFactory.getLogger(DataGeneratorImpl.class);
    Random random = new Random();

    String startData = "01-01-2000 00:00:00.00";
    String endData = "01-01-2020 00:00:00.00";

    public void createData(int bulkSize, int numOfInteractions) {
        for (int i = 0; i < bulkSize; i++) {
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter( "..\\tool-elastic-search-injector\\output\\output "+(i+1)+".json"));
                JSONArray jsonArray = generateData( numOfInteractions );
                writer.write( jsonArray.toString() );
                logger.info( jsonArray.toString() );
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
                if (writer != null) try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private JSONArray generateData(int numOfInteractions) throws JSONException {

        ArrayList <String> firstNames = generateNames( numOfInteractions, "..\\tool-elastic-search-injector\\input\\first-names.txt" );
        ArrayList <String> lastNames = generateNames( numOfInteractions, "..\\tool-elastic-search-injector\\input\\last-names.txt" );
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < numOfInteractions; i++) {
            Date startTime = getRandomDate( this.startData, endData );
            Date stopTime = getRandomDate( this.startData, endData );
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
            RecordingRecordedType recordedType = RecordingRecordedType.getRandomRecorderType();
            ExceptionType randomExceptionType = ExceptionType.getRandomExcetionType();

            JSONObject jsonObj = new JSONObject();
            jsonObj.put( Consts.INTERACTION_ID, getRandomNumber( 900000, 100000 ) );
            jsonObj.put( Consts.INTERACTION_LOCAL_START_TIME, startTime.toString() );
            jsonObj.put( Consts.INTERACTION_LOCAL_STOP_TIME, stopTime.toString() );
            jsonObj.put( Consts.INTERACTION_GMT_START_TIME, new Date( getGMTTime( startTime ) ).toString() );
            jsonObj.put( Consts.INTERACTION_GMT_STOP_TIME, new Date( getGMTTime( stopTime ) ).toString() );
            jsonObj.put( Consts.INTERACTION_DURATION, String.valueOf( Math.abs( stopTime.getTime() - startTime.getTime() ) ) );
            jsonObj.put( Consts.INTERACTION_OPEN_REASON_ID, openCallReason.getOpenCallReasonID() );
            jsonObj.put( Consts.INTERACTION_CLOSE_REASON_ID, closeCallReason.getCloseCallReasonID() );
            jsonObj.put( Consts.SWITCH_ID, getRandomNumber( 9, 1 ) );
            jsonObj.put( Consts.INITIATOR_USER_ID, getRandomNumber( 9, 1 ) );
            jsonObj.put( Consts.OTHER_SWITCH_ID, getRandomNumber( 9, 1 ) );
            jsonObj.put( Consts.INTERACTION_QA_TYPE_ID, getRandomBit() );
            jsonObj.put( Consts.INTERACTION_QA_TYPE_ID, getRandomBit() );
            jsonObj.put( Consts.INTERACTION_TYPE_ID, interactionType.getRandomInteractionTypeID() );
            jsonObj.put( Consts.INTERACTION_RECORDED_TYPE_ID, interactionType.getRandomInteractionTypeID() );
            jsonObj.put( Consts.IS_EVALUATED, getRandomBit() );
            jsonObj.put( Consts.IS_CUSTOMER_EVALUATED, getRandomBit() );
            jsonObj.put( Consts.MEDIA_TYPES_ID, randomMediaType.getMediaTypeID() );
            jsonObj.put( Consts.INTERACTION_TYPE_DESC, randomMediaType );
            jsonObj.put( Consts.INITIATOR_TYPE_ID, initiatorType.getInitiatorTypeID() );
            jsonObj.put( Consts.INITIATOR_TYPE_DESC, initiatorType );
            jsonObj.put( Consts.CLIENT_DTMF, generateRandomString( 5 ) );
            jsonObj.put( Consts.PBX_CALL_ID, getRandomNumber( 2000, 1000 ) );
            jsonObj.put( Consts.EXTERNAL_CALL_ID, getRandomNumber( 16, 0 ) );
            jsonObj.put( Consts.EXTERNAL_CALL_ID, getRandomNumber( 16, 0 ) );
            jsonObj.put( Consts.CALL_DIRECTION_TYPE_ID, randomDirectionType.getDirectionTypeID() );
            jsonObj.put( Consts.VECTOR_NUMBER, generateRandomString( 5 ) );
            jsonObj.put( Consts.PBX_UNIVARSAL_CALL_INTERACTION_ID, generateRandomString( 6 ) );
            jsonObj.put( Consts.COMPOUND_ID, getRandomNumber( 900000, 100000 ) );
            jsonObj.put( Consts.NDC_BUSINESS_DATA, generateRandomString( 4 ) );
            jsonObj.put( Consts.BIT_IS_PLAYBACK_CALL, getRandomBit() );
            jsonObj.put( Consts.PARTICIPANT_ID, random.nextInt( 16 ) );
            jsonObj.put( Consts.STATION, getRandomNumber( 9, 1 ) );
            jsonObj.put( Consts.AGENT_ID, getRandomNumber( 9, 1 ) );
            jsonObj.put( Consts.USER_ID, random.nextInt( 16 ) );
            jsonObj.put( Consts.FIRST_USER, getRandomBit() );
            jsonObj.put( Consts.IS_INERACTION_INITIATOR, getRandomBit() );
            jsonObj.put( Consts.DEVICE_TYPE_ID, deviceType.DeviceTypeID() );
            jsonObj.put( Consts.DEVICE_ID, random.nextInt( 256 ) );
            jsonObj.put( Consts.CTI_AGENT_NAME, firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( Consts.DEPARTMENT, generateRandomString( 6 ) );
            jsonObj.put( Consts.TRUNK_GROUP, generateRandomString( 4 ) );
            jsonObj.put( Consts.TRUNK_NUMBER, generateRandomString( 5 ) );
            jsonObj.put( Consts.TRUNK_LABEL, generateRandomString( 5 ) );
            jsonObj.put( Consts.CLIENT_ID, getRandomNumber( 900000, 100000 ) );
            jsonObj.put( Consts.VIRTUAL_DEVICE_ID, getRandomNumber( 16, 0 ) );
            jsonObj.put( Consts.PARTICIPANT_TYPE_ID, participantType.getparticipantTypeID() );
            jsonObj.put( Consts.PARTICIPANT_TYPE_DESC, participantType );
            jsonObj.put( Consts.OPEN_REASON_DESC, openCallReason );
            jsonObj.put( Consts.CLOSE_REASON_DESC, closeCallReason );
            jsonObj.put( Consts.DEVICE_TYPE_DESC, deviceType );
            jsonObj.put( Consts.RECORDING_SIDE_TYPE_ID, recordingSideType.RecordingSideTypeID() );
            jsonObj.put( Consts.RECORDING_SIDE_DESC, recordingSideType );
            jsonObj.put( Consts.MEDIA_DESC, randomMediaType );
            jsonObj.put( Consts.DIRECTION_TYPE_DESC, randomDirectionType );
            jsonObj.put( Consts.RECORDING_ID, getRandomNumber( 900000, 100000 ) );
            jsonObj.put( Consts.LOGGER, getRandomNumber( 16, 0 ) );
            jsonObj.put( Consts.CHANNEL, getRandomNumber( 100, 1 ) );
            jsonObj.put( Consts.MML_RECORDING_HINT, generateRandomString( 6 ) );
            jsonObj.put( Consts.RECORDING_GMT_START_TIME, getGMTTime( startTime ) );
            jsonObj.put( Consts.RECORDING_GMT_STOP_TIME, getGMTTime( stopTime ) );
            jsonObj.put( Consts.RECORDING_GMT_STOP_TIME, getGMTTime( stopTime ) );
            jsonObj.put( Consts.RECORDING_RECORDED_TYPE_ID, RecordingRecordedType.getRandomRecorderType() );
            jsonObj.put( Consts.RECORDING_RECORDED_TYPE_ID, RecordingRecordedType.getRandomRecorderType() );
            jsonObj.put( Consts.PROGRAM_ID, getRandomNumber( 16, 1 ) );
            jsonObj.put( Consts.RECORDED_PARTICIPANT_ID, getRandomNumber( 16, 1 ) );
            jsonObj.put( Consts.TIME_DIFF, String.valueOf( Math.abs( stopTime.getTime() - startTime.getTime() ) ) );
            jsonObj.put( Consts.TIME_DIFF, getRandomNumber( 16, 1 ) );
            jsonObj.put( Consts.TIME_DIFF, getRandomNumber( 16, 1 ) );
            jsonObj.put( Consts.SESSION_ID, getRandomNumber( 90000, 10000 ) );
            jsonObj.put( Consts.ITEM_DATA_TYPE_ID, randomItemDataType.itemDataTypeID() );
            jsonObj.put( Consts.ITEM_DATA_TYPE_DESC, randomItemDataType );
            jsonObj.put( Consts.CREATOR_TYPE_ID, creatorType.creatorTypeID() );
            jsonObj.put( Consts.CREATOR_TYPE_DESC, creatorType );
            jsonObj.put( Consts.ITEM_TYPE_ID, randomItemType.itemTypeID() );
            jsonObj.put( Consts.ITEM_TYPE_ID, randomItemType );
            jsonObj.put( Consts.CONTACT_ID, getRandomNumber( 16, 0 ) );
            jsonObj.put( Consts.ITEM_SEQUENCE_NUMBER, getRandomNumber( 16, 0 ) );
            jsonObj.put( Consts.TIME_STAMP, getRandomDate( startData, endData ) );
            jsonObj.put( Consts.ITEM_USER_ID, getRandomNumber( 16, 0 ) );
            jsonObj.put( Consts.ITEM_VALUE, generateRandomString( 8 ) );
            jsonObj.put( Consts.ITEM_IS_DELETED, getRandomBit() );
            jsonObj.put( Consts.IS_PUBLIC, getRandomBit() );
            jsonObj.put( Consts.ITEM_OFFSET, getRandomNumber( 16, 1 ) );
            jsonObj.put( Consts.RECORDED_TYPE_ID, recordedType.RecorderTypeID() );
            jsonObj.put( Consts.RECORDED_TYPE_DESC, recordedType );
            jsonObj.put( Consts.ARCHIVE_ID, getRandomNumber( 16, 0 ) );
            jsonObj.put( Consts.MEDIA_TYPE_ID, randomMediaType.getMediaTypeID() );
            jsonObj.put( Consts.MEDIA_TYPE_ID, randomMediaType.getMediaTypeID() );
            jsonObj.put( Consts.ARCHIVE_PATH, generateRandomString( 255 ) );
            jsonObj.put( Consts.ARCHIVE_ID_HIGH, getRandomNumber( 16, 0 ) );
            jsonObj.put( Consts.ARCHIVE_ID_LOW, getRandomNumber( 16, 1 ) );
            jsonObj.put( Consts.ARCHIVE_CLASS, getRandomNumber( 16, 1 ) );
            jsonObj.put( Consts.SC_SERVER_ID, generateRandomString( 100 ) );
            jsonObj.put( Consts.SC_SITE_ID, getRandomNumber( 256, 0 ) );
            jsonObj.put( Consts.SC_RULE_ID, getRandomNumber( 256, 0 ) );
            jsonObj.put( Consts.SC_SERVER_ID, getRandomNumber( 100, 0 ) );
            jsonObj.put( Consts.SC_LOGGER_ID, getRandomNumber( 256, 0 ) );
            jsonObj.put( Consts.SC_LOGGER_RESOURCE, getRandomNumber( 256, 0 ) );
            jsonObj.put( Consts.ARCHIVE_UNIQUE_ID, generateRandomString( 255 ) );
            jsonObj.put( Consts.RETENTION_DAYS, getRandomNumber( 365 * 30, 0 ) );
            jsonObj.put( Consts.RETENTION_DAYS, getRandomNumber( 365 * 30, 0 ) );
            jsonObj.put( Consts.CONTACT_GMT_START_TIME, startTime );
            jsonObj.put( Consts.CONTACT_GMT_STOP_TIME, stopTime );
            jsonObj.put( Consts.CONTACT_DURATION, String.valueOf( Math.abs( stopTime.getTime() - startTime.getTime() ) ) );
            jsonObj.put( Consts.CONTACT_OPEN_REASON_ID, openCallReason.getOpenCallReasonID() );
            jsonObj.put( Consts.CONTACT_CLOSE_REASON_ID, closeCallReason.getCloseCallReasonID() );
            jsonObj.put( Consts.TRANSFER_SITE_ID, getRandomNumber( 256, 0 ) );
            jsonObj.put( Consts.TRANSFER_CONTACT_ID, getRandomNumber( 256, 0 ) );
            jsonObj.put( Consts.TRANSFER_SITE_ID, getRandomNumber( 256, 0 ) );
            jsonObj.put( Consts.CONTACT_RECORDED_TYPE_ID, getRandomNumber( 256, 0 ) );
             jsonObj.put( Consts.CONTACT_QA_TYPE_ID, random.nextInt(256));
            jsonObj.put( Consts.CONTACT_DIRECTION_TYPE_ID, randomDirectionType.getDirectionTypeID());
            jsonObj.put( Consts.EXCEPTION_TYPE_ID, randomExceptionType.exceptionTypeID());
            jsonObj.put( Consts.EXCEPTION_TYPE_DESC, randomExceptionType.exceptionDescription());
            jsonObj.put( Consts.EXCEPTION_POSSIBLE_CAUSE, randomExceptionType.exceptionPossibleCause());
            jsonObj.put( Consts.EXCEPTION_RECOMMENDED_ACTION, randomExceptionType.exceptionRecommendedAction());
            jsonObj.put( Consts.EXCEPTION_NUMBER, random.nextInt());
            jsonObj.put( Consts.EXCEPTION_TIMESTAMP, getRandomDate(startData, endData));
            jsonObj.put( Consts.EXCEPTION_DETAIL, generateRandomString(32));
            jsonObj.put( Consts.CREATED_BY_ID, random.nextInt(100));
            jsonObj.put( Consts.CREATION_DATE, getRandomDate(startData, endData));
            jsonObj.put( Consts.CREATION_DATE, getRandomDate(startData, endData));
            jsonObj.put( Consts.CLIP_NAME, generateRandomString(50));
            jsonObj.put( Consts.CATEGORY_ID, random.nextInt(16));
            jsonObj.put( Consts.IS_PUBLISHED, getRandomBit());
            jsonObj.put( Consts.IS_PUBLIC, getRandomBit());
            jsonObj.put( "nvcChatRoomName", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "iParticipantSenderID", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "iParticipantRecipientID", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "nvcSenderPhoneNumber", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "nvcRecipientPhoneNumber", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "dSurveyScore", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "tiSurveyChannelID", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "iSurveyID", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "nvcSurveyChannelName", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "iAnswerCategoryID", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "iQuestionID", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "iInteractionOriginalID", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "siAuthenticationLevel", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "iPacketLossRx", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "iPacketLossTx", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "biOriginalCallId", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "nvcCustomerId", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "nvcCaseId", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "RuleDefaultSystemScore", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "RuleDefaultSystemEmotion", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "RuleDefaultProductivity", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "RuleDefaultQuality", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "iSetNumber", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "tiVoiceArchiveStatus", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "tiVoiceFSArchiveStatus", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "dtVoiceExpirationDate", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "iVoiceRemainderDays", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "tiScreenArchiveStatus", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "tiScreenFSArchiveStatus", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "iScreenRemainderDays", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "dtScreenExpirationDate", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "iScreenRemainderDays", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "tiVoiceESMArchiveStatus", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "tiScreenESMArchiveStatus", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "tiArchiveStatus", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "tiESmArchiveStatus", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "tiFSArchiveStatus", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "dtExpirationDate", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "LastExtendingUser", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "dtInsertTime", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "iRequestId", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "LastExtendingUser", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "LastInsertDate", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "ReasonCaption", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "tiTextArchiveStatus", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "tiTextArchiveStatus", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "tiTextFSArchiveStatus", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "tiTextESMArchiveStatus", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "iTextRemainderDays", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "dtTextExpirationDate", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "dtScreenForceDeleteDate", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "dtTextForceDeleteDate", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( Consts.FIRST_NAME, firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( Consts.LAST_NAME, lastNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "dtHireDate", "bar@gmail.com");
            jsonObj.put( "dtGradDate", "bar@gmail.com");
            jsonObj.put( "iJobSkillID", "bar@gmail.com");
            jsonObj.put( "iJobClassID", "bar@gmail.com");
            jsonObj.put( "iDepartmentID", "bar@gmail.com");
            jsonObj.put( "iPlannedEvaluations", "bar@gmail.com");
            jsonObj.put( "iPlannedCalibrations", "bar@gmail.com");
            jsonObj.put( "vcEmailAddress", "bar@gmail.com");
            jsonObj.put( "nvcLoginName", "1");
            jsonObj.put( "iActiveSiteID", "1");
            jsonObj.put( "iAgentId", "1");
            jsonObj.put( Consts.EXTENTION, "1");
            jsonObj.put( Consts.SWITCH_AGENT_ID, "1");
            jsonObj.put( Consts.LOCATION_ID, "1");
            jsonObj.put( Consts.JOB_SKILL, "1");
            jsonObj.put( Consts.JOB_FUNCTION, "1");
            jsonObj.put( Consts.JOB_CLASS, "1");
            jsonObj.put( Consts.DEPARTMENT, "1");
            jsonObj.put( Consts.LOCATION, "1");
            jsonObj.put( Consts.ITEM_ID, "1");
            jsonObj.put( Consts.GRAD_SCORE, "1");
            jsonObj.put( Consts.STATUS, "1");
            jsonObj.put( Consts.FORMATTER_NAME, "1");
            jsonObj.put( Consts.MANAGED_ID, "1");
            jsonObj.put( Consts.GROUP_ID, "1");
            jsonObj.put( Consts.USER_ID, "1");
            jsonObj.put( Consts.SURVEY_NAME , "1");
            jsonObj.put( Consts.TOPIC_ID, "1");
            jsonObj.put( Consts.TOPIC_NAME, "FizzBack");
            jsonObj.put( Consts.CATEGORY_SCORE, getRandomNumber( 100, 0 ));
            jsonObj.put( Consts.NVC_NAME, firstNames.get(random.nextInt( firstNames.size())));

            jsonArray.put( jsonObj );
        }
        return jsonArray;
    }

    private int getRandomNumber(int max, int min) {
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

    private long getGMTTime(Date time) {
        return time.getTime() - 3600 * 1000 * 2;
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

    private Date getRandomDate(String stringStartDate, String StringEndDate) {
        Date randomDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat( "dd-M-yyyy hh:mm:ss" );
        try {
            Date startDate = sdf.parse( stringStartDate );
            long startDateMillis = startDate.getTime();
            Date endDate = sdf.parse( StringEndDate );
            long endDateMillis = endDate.getTime();
            long randomDateMillis = (random.nextLong() % (endDateMillis - startDateMillis)) + startDateMillis;
            randomDate = new Date( randomDateMillis );
            return randomDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return randomDate;
    }
}
