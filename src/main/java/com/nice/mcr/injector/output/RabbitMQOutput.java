package com.nice.mcr.injector.output;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQOutput implements OutputHandler {


    public static final String CLI_OPTION = "rmq";
    private static final String EXCHANGE_NAME = "test1";
    Channel channel;

    @Value("${rabbitmq.host}")
    private String rmqHost;
    @Value("${rabbitmq.username}")
    private String rmqUser;
    @Value("${rabbitmq.password}")
    private String rmqPassword;


    @Override
    public boolean open() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
//        factory.setUsername(rmqUser);
//        factory.setPassword(rmqPassword);

        try {
            Connection connection = factory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "topic", true);

            String message = "info: Hello World!";

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void output(String data) {
        try {
            channel.basicPublish(EXCHANGE_NAME, "", null, data.getBytes("UTF-8"));
//            channel.
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Sent '" + data + "'");


    }
}
