package com.qlearsite.eng.opentracking.service;

import com.qlearsite.eng.opentracking.config.ModuleConfigProperties;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.impl.ForgivingExceptionHandler;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.Introspected;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Introspected
@Singleton
@Requires(beans = ModuleConfigProperties.class)
public class RabbitMqConnector {

    private ModuleConfigProperties configProperties;

    private ConnectionFactory factory;

    public RabbitMqConnector(ModuleConfigProperties configProperties) {
        this.configProperties = configProperties;
    }

    public final ModuleConfigProperties getConfig() {
        return configProperties;
    }

    @PostConstruct
    public void init() {
        ModuleConfigProperties.Notification.RabbitMq rabbitMq = getConfig().getNotification().getRabbitmq();
        factory = new ConnectionFactory();
        factory.setHost(rabbitMq.getHost());
        factory.setPort(rabbitMq.getPort());
        factory.setUsername(rabbitMq.getUsername());
        factory.setPassword(rabbitMq.getPassword());
        factory.setExceptionHandler(new ForgivingExceptionHandler());
    }

    public Connection createConnection() throws IOException, TimeoutException {
        return factory.newConnection();
    }
}
