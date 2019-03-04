package com.nice.mcr.injector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"com.nice.mcr"})
public class Application {
    public static void main(String[] args) {
        /* ApplicationContext applicationContext = */
        SpringApplication.run(Application.class, args);
    }
}

