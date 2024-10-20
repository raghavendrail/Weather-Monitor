package com.zeotap.Weather.Monitoring.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
public class TemperatureConversionTest {

    @Test
    public void testTemperatureConversion() {
        // Sample temperature in Kelvin
        double kelvinTemp = 300.0;

        // Convert to Celsius
        double celsiusTemp = convertKelvinToCelsius(kelvinTemp);
        assertEquals(26.85, celsiusTemp, 0.01, "Celsius conversion is incorrect");

        // Convert to Fahrenheit
        double fahrenheitTemp = convertKelvinToFahrenheit(kelvinTemp);
        assertEquals(80.33, fahrenheitTemp, 0.01, "Fahrenheit conversion is incorrect");
    }

    // Method to convert Kelvin to Celsius
    private double convertKelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }

    // Method to convert Kelvin to Fahrenheit
    private double convertKelvinToFahrenheit(double kelvin) {
        return (kelvin - 273.15) * 9 / 5 + 32;
    }
}
