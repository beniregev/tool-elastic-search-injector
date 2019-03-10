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
import java.util.ArrayList;
import java.util.Random;

@Service
public class DataGeneratorImpl implements DataGenerator {

    static Logger logger = (Logger) LoggerFactory.getLogger(DataGeneratorImpl.class);

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
        Random random = new Random();
        ArrayList <String> firstNames = generateNames( numOfInteractions, "..\\tool-elastic-search-injector\\input\\first-names.txt" );
        ArrayList <String> lastNames = generateNames( numOfInteractions, "..\\tool-elastic-search-injector\\input\\last-names.txt" );
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < numOfInteractions; i++) {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put( "iInteraction", random.nextInt( 900000 ) + 10000000 );
            jsonObj.put( "iSwitchID", random.nextInt( 9 ) + 1 );
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
}
