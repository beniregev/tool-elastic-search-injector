package com.nice.mcr.injector.output;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQOutput implements OutputHandler {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQOutput.class);

    public static final String CLI_OPTION = "rmq";
    private Channel channel;

    @Value("${rabbitmq.host}")
    private String rmqHost;
    @Value("${rabbitmq.username}")
    private String rmqUser;
    @Value("${rabbitmq.password}")
    private String rmqPassword;
    @Value("${rabbitmq.exchange}")
    private String rmqExchange;


    @Override
    public boolean open() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(rmqHost);
        factory.setUsername(rmqUser);
        factory.setPassword(rmqPassword);

        try {
            Connection connection = factory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(rmqExchange, "topic", true);
            return true;
        } catch (IOException ioe) {
            log.error("Exception occurred while attaching to Rabbit MQ", ioe);
        } catch (TimeoutException te) {
            log.error("Exception occurred while attaching to Rabbit MQ", te);
        }
        return false;
    }

    @Override
    public void output(String data) {
        try {
            channel.basicPublish(rmqExchange, "", null, data.getBytes("UTF-8"));
        } catch (IOException ioe) {
            log.error("Exception occurred while publishing to Rabbit MQ", ioe);
        }
        log.debug("Sent '" + data + "'");
    }
}
