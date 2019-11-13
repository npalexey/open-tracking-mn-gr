package com.qlearsite.eng.opentracking.service;

import io.micronaut.context.annotation.Requires;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.InputStream;

@Singleton
@Requires(beans = RabbitMqPublisher.class)
public class TrackingService {

    private static final Logger logger = LoggerFactory.getLogger(TrackingService.class);

    private RabbitMqPublisher rabbitMqPublisher;

    public TrackingService(RabbitMqPublisher rabbitMqPublisher) {
        this.rabbitMqPublisher = rabbitMqPublisher;
    }

    public byte[] track(String id) {
        try {
            rabbitMqPublisher.publish(id);
            InputStream in = getClass()
                    .getResourceAsStream("/image.png");
            return IOUtils.toByteArray(in);
        } catch (IOException e) {
            logger.error("Error occurred while reading image", e);
            return new byte[]{};
        }
    }
}
