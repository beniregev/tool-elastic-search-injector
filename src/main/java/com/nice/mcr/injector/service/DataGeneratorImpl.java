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
    String stringStartDate = "01-01-2000 00:00:00.00";
    String stringEndDate = "01-01-2020 00:00:00.00";
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
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                if ( writer != null) try {
                    writer.close( );
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
            JSONObject jsonObj = new JSONObject();
            jsonObj.put( Consts.INTERACTION_ID, random.nextInt( 900000 ) + 10000000 );

            Date startTime = getRandomDate(stringStartDate, stringEndDate);
            Date stopTime = getRandomDate(stringStartDate, stringEndDate);
            jsonObj.put(Consts.INTERACTION_LOCAL_START_TIME, startTime.toString());
            jsonObj.put(Consts.INTERACTION_LOCAL_STOP_TIME, stopTime.toString());
            jsonObj.put(Consts.INTERACTION_GMT_START_TIME, new Date(startTime.getTime() - 3600* 1000 * 2).toString());
            jsonObj.put(Consts.INTERACTION_GMT_STOP_TIME, new Date(startTime.getTime() - 3600* 1000 * 2).toString());
            jsonObj.put(Consts.INTERACTION_DURATION, String.valueOf(Math.abs(stopTime.getTime() - startTime.getTime())));
            OpenCallReason openCallReason = OpenCallReason.getRandomReason();
            jsonObj.put(Consts.INTERACTION_OPEN_REASON_ID, openCallReason.getOpenCallReasonID());

            CloseCallReason closeCallReason = CloseCallReason.getRandomReason();
            jsonObj.put(Consts.INTERACTION_CLOSE_REASON_ID, closeCallReason.getCloseCallReasonID());
            jsonObj.put( Consts.SWITCH_ID, random.nextInt( 9 ) + 1 );
            jsonObj.put( Consts.INITIATOR_USER_ID, random.nextInt( 9 ) + 1 );
            jsonObj.put( Consts.OTHER_SWITCH_ID, random.nextInt( 9 ) + 1 );

            jsonObj.put( "tiInteractionTypeID", random.nextInt( 3 ) + 1 );
            jsonObj.put( "tiInteractionRecordedTypeID", 0 );
            jsonObj.put( "vcInteractionDesc", "CTI" );
            jsonObj.put( "iExternalCallId", -1 );
            jsonObj.put( "tiCallDirectionTypeID", 1 );
            jsonObj.put( "iPBXCallID", random.nextInt( 32000 - 30000 ) + 300000 );
            jsonObj.put( "iCompoundID", random.nextInt( 900000 ) + 10000000 );
            jsonObj.put( "iParticipantID", random.nextInt( 3 ) + 1 );
            jsonObj.put( "nvcStation", random.nextInt( 1000 ) + 30000 );
            jsonObj.put( "first-name", firstNames.get( random.nextInt( firstNames.size() ) ) );
            jsonObj.put( "first-name", lastNames.get( random.nextInt( firstNames.size() ) ) );

            jsonArray.put( jsonObj );
        }
        return jsonArray;
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

    private Date getRandomDate(String stringStartDate , String StringEndDate) {
        Date randomDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        try {
            Date startDate = sdf.parse(stringStartDate);
            long startDateMillis = startDate.getTime();
            Date endDate = sdf.parse(StringEndDate);
            long endDateMillis = endDate.getTime();
            long randomDateMillis = (random.nextLong() % (endDateMillis - startDateMillis)) + startDateMillis;
            randomDate = new Date(randomDateMillis);
            return randomDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return randomDate;
    }
}
