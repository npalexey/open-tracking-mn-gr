package com.qlearsite.eng.opentracking.api.controller;

import com.qlearsite.eng.opentracking.service.TrackingService;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Produces;

@Controller("/tracking")
@Requires(beans = TrackingService.class)
public class TrackingController {

    private TrackingService trackingService;

    public TrackingController(TrackingService trackingService) {
        this.trackingService = trackingService;
    }

    @Get("/img/transparent.png/{id}")
    @Produces(MediaType.IMAGE_PNG)
    public byte[] getDistributions(@PathVariable("id") String id) {
        return trackingService.track(id);
    }

}
