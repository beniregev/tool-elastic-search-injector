package com.nice.mcr.injector.config;

import com.nice.mcr.injector.mock.UserAdminRestClientMock;
import com.nice.mcr.injector.output.FileOutput;
import com.nice.mcr.injector.output.RabbitMQOutput;
import com.nice.mcr.injector.output.SocketOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.*;

import java.util.*;

@Configuration
@ComponentScan(basePackages = "com.nice.mcr.injector")
public class InjectorConfig {

    private static final Logger log = LoggerFactory.getLogger(InjectorConfig.class);
    private Map<String, String> appArgs = new HashMap<>();

    @Bean
    @Lazy(true)
    public RabbitMQOutput rabbitMQOutput()
    {
        return new RabbitMQOutput();
    }

    @Bean
    @Lazy(true)
    public SocketOutput socketOutput()
    {
        return new SocketOutput();
    }

    @Bean
    @Lazy(true)
    public FileOutput fileOutput()
    {
        return new FileOutput();
    }

    @Bean
    public UserAdminRestClientMock userAdminRestClientMock() {
        return new UserAdminRestClientMock();
    }
}
