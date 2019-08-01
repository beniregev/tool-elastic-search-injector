package com.nice.mcr.injector;

import com.nice.mcr.injector.linkedout.FileCommunication;
import com.nice.mcr.injector.policies.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@SpringBootApplication(scanBasePackages = {"com.nice.mcr"})
public class Main {
    public static void main(String[] args) {

        PoliciesController pc = new PoliciesController();
        UpdateListeners ul = new UpdateListeners();
        ul.addDataListener(new FileCommunication());

//        long startTime = System.currentTimeMillis();
//        pc.policyTest(new SpikePolicy(ul, 10, 10, 1000, 2, 3));
        new SpikePolicy(ul, 100, 20, 3, 100, 4 ).run();
//        new (ul, 2, 2).run();
//        new BacklogPolicy(ul, 100).run();
//        long endTime = System.currentTimeMillis();
//
//        System.out.println("program run timer: " + (endTime - startTime));
//        Random r = new Random();
//
//        for (int i = 0 ; i < 1000 ; i++) {
//            int d = r.nextInt(5 + 1) + 1;
//            if (d == 6)
//                System.out.println(d);
//        }
    }
}

