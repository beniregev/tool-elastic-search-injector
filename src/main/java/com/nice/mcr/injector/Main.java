package com.nice.mcr.injector;

import com.nice.mcr.injector.config.InjectorConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.util.ObjectUtils;


@SpringBootApplication(scanBasePackages = {"com.nice.mcr"})
@Import({InjectorConfig.class})
public class Main {
    public static void main(String[] args) {

        SpringApplication application = new SpringApplication(Main.class);
        WebApplicationType webAppType = WebApplicationType.NONE;
        if (ObjectUtils.isEmpty(args)) {
            webAppType = WebApplicationType.SERVLET;
        }
        application.setWebApplicationType(webAppType);
        application.run(args);

    }
}

