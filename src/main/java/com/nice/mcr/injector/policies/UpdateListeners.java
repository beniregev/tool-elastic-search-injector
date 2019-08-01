package com.nice.mcr.injector.policies;

import com.nice.mcr.injector.linkedout.DataListener;
import com.nice.mcr.injector.service.DataGeneratorImpl;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

/**
 * An implementation for TimerTask to run inside the Timer defined.
 */
public class UpdateListeners extends TimerTask {

    private static int counter = 0;
    private List<DataListener> dataListeners;
    private DataGeneratorImpl dataGenerator;

    public UpdateListeners() {
        this.dataGenerator = new DataGeneratorImpl();
        this.dataListeners = new ArrayList<>();
    }

    public UpdateListeners(List<DataListener> dataListeners) {
        this.dataGenerator = new DataGeneratorImpl();
        this.dataListeners = dataListeners;
    }

    public void addDataListener(DataListener dataListener) {
        this.dataListeners.add(dataListener);
    }

    public List<DataListener> getDataListeners() {
        return dataListeners;
    }

    public void removeDataListener(DataListener dataListener){
        this.dataListeners.remove(dataListener);
    }

    /**
     * The function creates a new json using the DataGenerator class and updates it's listeners.
     */
    public void run() {
        try {
            String json = this.dataGenerator.generateBulkData(1);
            this.counter++;
            for (DataListener dl : this.dataListeners) {
                dl.update(json);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getCounter() {
        return counter;
    }
}
