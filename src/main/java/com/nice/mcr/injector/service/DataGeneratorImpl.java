package com.nice.mcr.injector.service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Random;

@Service
public class DataGeneratorImpl implements DataGenerator {

    public void createData(int bulkSize, int numOfInteractions) throws JSONException {
        Random random = new Random();
        ObjectMapper mapper = new ObjectMapper();

        JSONArray jsonArray = new JSONArray();

        for(int i=0 ; i<numOfInteractions;i++) {
            JSONObject obj = new JSONObject();
            obj.put( "Interaction", random.nextInt( 10000000 - 1000000 ) + 10000000 );
            System.out.println("tali");

            jsonArray.put( obj );
            try {
                mapper.writeValue( new File( "C:\\Users\\talioh\\IdeaProjects\\tool-elastic-search-injector\\output\\carmit.json" ), jsonArray );
            } catch (JsonGenerationException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}