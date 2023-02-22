package com.example.application;

import java.util.Arrays;

import com.vaadin.flow.server.auth.AnonymousAllowed;

import dev.hilla.Endpoint;
import dev.hilla.Nonnull;

@Endpoint
@AnonymousAllowed
public class SensorEndpoint {

    @Nonnull
    public SensorInfo getSensorInfo(@Nonnull String sensorId) {

        SensorInfo sensorInfo = new SensorInfo();

        sensorInfo.setSensorId("698af98f");
        sensorInfo.setRoom("Living Room");
        sensorInfo.setTarget(21);
        sensorInfo.setTemperature(24.6);
        sensorInfo.setPower(1200);
        sensorInfo.setHeating(false);
        Double[] history = new Double[] { 24.0, 23.9, 23.8, 23.6, 23.5, 23.4, 23.3, 23.2, 23.1, 23.0, 23.2, 23.3,
                23.4, 23.3, 23.0, 23.5, 23.4, 23.6, 24.2, 24.4, 24.4, 23.6, 24.0, 24.6, 24.6 };
        sensorInfo.setHistory(Arrays.asList(history));

        return sensorInfo;
    }
}
