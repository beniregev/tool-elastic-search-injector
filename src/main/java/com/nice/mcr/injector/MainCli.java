package com.nice.mcr.injector;

import com.nice.mcr.injector.service.DataGenerator;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ObjectUtils;

import java.util.List;


@SpringBootApplication(scanBasePackages = {"com.nice.mcr"})
public class MainCli implements ApplicationRunner {

    @Autowired
    private DataGenerator dataGenerator;

    public void run(ApplicationArguments args) {
        if (ObjectUtils.isEmpty(args.getSourceArgs())) {
            return; // Regular web application
        }
        int numOfBulks = Integer.valueOf(args.getOptionValues("bulks").get(0));
        int numOfSegments = Integer.valueOf(args.getOptionValues("segments").get(0));
        List policy = args.getOptionValues("policy");
        try {
            dataGenerator.createData(numOfBulks, numOfSegments);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

