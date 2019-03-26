package com.nice.mcr.injector;

import com.nice.mcr.injector.service.DataGeneratorImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication(scanBasePackages = {"com.nice.mcr"})
public class Main {
    public static void main(String[] args) {
        SpringApplication.run( Main.class, args);
    }
}

