package com.nice.mcr.injector;

import com.nice.mcr.injector.service.DataGeneratorImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication(scanBasePackages = {"com.nice.mcr"})
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        DataGeneratorImpl dataGenerator = ctx.getBean(DataGeneratorImpl.class);
        dataGenerator.createData( 7,10 );

    }
}

