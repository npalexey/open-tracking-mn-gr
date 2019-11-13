package com.qlearsite.eng.opentracking.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qlearsite.eng.opentracking.config.ModuleConfigProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.Introspected;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Introspected
@Singleton
@Requires(beans = {RabbitMqConnector.class})
public class RabbitMqPublisher {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMqPublisher.class);

    private RabbitMqConnector factory;
    private ModuleConfigProperties configProperties;
    private ObjectMapper mapper = new ObjectMapper();
    private Connection connection;
    private Channel channel;
    private ModuleConfigProperties.Notification.RabbitMq.Exchange exchange;

    public RabbitMqPublisher(RabbitMqConnector factory, ModuleConfigProperties configProperties) {
        this.factory = factory;
        this.configProperties = configProperties;
    }

    @PostConstruct
    public void init() throws IOException, TimeoutException {
        this.exchange = configProperties.getNotification().getRabbitmq().getExchange();
        connection = factory.createConnection();
        channel = connection.createChannel();
        channel.exchangeDeclare(exchange.getName(), exchange.getType(), exchange.isDurable(), exchange.isAutoDelete(), null);
    }

    public void publish(Object object) {
        try {
            channel.basicPublish(exchange.getName(), "", MessageProperties.PERSISTENT_TEXT_PLAIN, mapper.writeValueAsBytes(object));
        } catch (IOException ex) {
            logger.error("Error publishing to exchange: " + exchange.getName(), ex);
        }
    }

    @PreDestroy
    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (IOException e) {
            logger.error("Error closing service.", e);
        }
    }

}
