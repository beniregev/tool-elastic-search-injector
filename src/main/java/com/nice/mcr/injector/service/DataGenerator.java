package com.nice.mcr.injector.service;

import org.json.JSONException;

import java.util.List;

public interface DataGenerator {

    String createData(int numOfInsteractions) throws JSONException;
}