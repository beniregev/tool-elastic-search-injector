package com.nice.mcr.injector.service;

import org.json.JSONException;

import java.util.List;

public interface DataGenerator {

    List<String> createData(int numberOfBulks, int numOfInsteractions) throws JSONException;
}