package com.nice.mcr.injector.service;

import org.json.JSONException;

public interface DataGenerator {

    void createData(int bulkSize, int numOfInsteractions) throws JSONException;
}