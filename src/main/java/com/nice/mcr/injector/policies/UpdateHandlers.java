package com.nice.mcr.injector.policies;

import com.nice.mcr.injector.linkedout.DataListener;
import com.nice.mcr.injector.output.OutputHandler;
import com.nice.mcr.injector.service.DataGeneratorImpl;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

/**
 * An implementation for TimerTask to run inside the Timer defined.
 */
public class UpdateHandlers extends TimerTask {

    private static int counter = 0;
    private List<OutputHandler> outputHandlers;
    private DataGeneratorImpl dataGenerator;
    private int numOfBulks;

    public UpdateHandlers(int numOfBulks) {
        this.dataGenerator = new DataGeneratorImpl();
        this.outputHandlers = new ArrayList<>();
        this.numOfBulks = numOfBulks;
    }

    public UpdateHandlers(List<OutputHandler> outputHandlers, int numOfBulks) {
        this.dataGenerator = new DataGeneratorImpl();
        this.outputHandlers = outputHandlers;
        this.numOfBulks = numOfBulks;
    }

    public int getNumOfBulks() {
        return numOfBulks;
    }

    public List<OutputHandler> getOutputHandler() {
        return outputHandlers;
    }

    /**
     * The function creates a new json using the DataGenerator class and updates it's listeners.
     */
    public void run() {
        try {
            String json = this.dataGenerator.generateBulkData(1);
            this.counter++;
            for (OutputHandler oh : this.outputHandlers) {
                oh.output(json);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getCounter() {
        return counter;
    }
}
