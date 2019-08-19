package com.nice.mcr.injector.config;

import com.nice.mcr.injector.output.FileOutput;
import com.nice.mcr.injector.output.RabbitMQOutput;
import com.nice.mcr.injector.output.SocketOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class InjectorConfig {

    private static final Logger log = LoggerFactory.getLogger(InjectorConfig.class);

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

}
