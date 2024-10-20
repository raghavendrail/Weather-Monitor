package com.zeotap.Weather.Monitoring.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
public class AlertThresholdTest {

    private final double THRESHOLD_TEMPERATURE = 35.0; // Threshold in Celsius

    @Test
    public void testAlertOnTemperatureExceedingThreshold() {
        // Simulated weather data
        double[] temperaturesInKelvin = {310.0, 280.0, 305.0, 290.0, 340.0}; // Last temperature exceeds threshold

        boolean alertTriggered = false;

        // Check each temperature to see if it exceeds the threshold
        for (double kelvinTemp : temperaturesInKelvin) {
            if (convertKelvinToCelsius(kelvinTemp) > THRESHOLD_TEMPERATURE) {
                alertTriggered = true; // Alert is triggered if the temperature exceeds threshold
                break; // Exit loop once alert is triggered
            }
        }

        assertTrue(alertTriggered, "Alert should be triggered when temperature exceeds threshold");
    }

    // Method to convert Kelvin to Celsius
    private double convertKelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }
}
