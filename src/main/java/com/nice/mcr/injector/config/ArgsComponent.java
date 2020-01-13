package com.nice.mcr.injector.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class ArgsComponent {
    private Map<String, String> appArgs = new HashMap<>();

    @Autowired
    public ArgsComponent(ApplicationArguments args) {
        System.out.println("application arguments = " + args);
        setApplicationArguments(args);
    }

    public void setApplicationArguments(ApplicationArguments args) {
        Arrays.asList(args.getSourceArgs()).forEach(x -> {
                    System.out.println(x.substring(2));
                    String[] item = x.substring(2).split("=");
                    this.appArgs.put(item[0], item[1]);
                }
        );
        this.appArgs.forEach((k, v) -> System.out.println("key=" + k + ", value=" + v));
    }

    public Map<String, String> getAppArgs() {
        return appArgs;
    }
}

