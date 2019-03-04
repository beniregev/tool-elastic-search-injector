package com.nice.mcr.injector.service;

import org.json.JSONException;

public interface DataGenerator {

    void createData(String bulsSize, String numOfInsteractions) throws JSONException;
}