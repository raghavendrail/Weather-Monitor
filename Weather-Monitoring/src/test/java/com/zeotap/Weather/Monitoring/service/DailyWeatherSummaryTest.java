package com.zeotap.Weather.Monitoring.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
@SpringBootTest
public class DailyWeatherSummaryTest {

    @Test
    public void testDailyWeatherSummary() {
        // Simulated weather data for 5 days (temperature in Kelvin and weather condition)
        List<WeatherData> weatherDataList = Arrays.asList(
            new WeatherData(300.0, "Clear"),
            new WeatherData(295.0, "Clouds"),
            new WeatherData(310.0, "Clear"),
            new WeatherData(285.0, "Rain"),
            new WeatherData(295.0, "Clear")
        );

        // Calculate daily summaries
        double averageTemp = calculateAverageTemperature(weatherDataList);
        double maxTemp = calculateMaxTemperature(weatherDataList);
        double minTemp = calculateMinTemperature(weatherDataList);
        String dominantCondition = findDominantWeatherCondition(weatherDataList);

        // Assertions
        assertEquals(297.0, averageTemp, 0.01, "Average temperature calculation is incorrect");
        assertEquals(310.0, maxTemp, 0.01, "Maximum temperature calculation is incorrect");
        assertEquals(285.0, minTemp, 0.01, "Minimum temperature calculation is incorrect");
        assertEquals("Clear", dominantCondition, "Dominant weather condition calculation is incorrect");
    }

    // Method to calculate average temperature
    private double calculateAverageTemperature(List<WeatherData> weatherDataList) {
        double totalTemp = 0;
        for (WeatherData data : weatherDataList) {
            totalTemp += data.getTemperature();
        }
        return totalTemp / weatherDataList.size();
    }

    // Method to calculate maximum temperature
    private double calculateMaxTemperature(List<WeatherData> weatherDataList) {
        double maxTemp = Double.MIN_VALUE;
        for (WeatherData data : weatherDataList) {
            maxTemp = Math.max(maxTemp, data.getTemperature());
        }
        return maxTemp;
    }

    // Method to calculate minimum temperature
    private double calculateMinTemperature(List<WeatherData> weatherDataList) {
        double minTemp = Double.MAX_VALUE;
        for (WeatherData data : weatherDataList) {
            minTemp = Math.min(minTemp, data.getTemperature());
        }
        return minTemp;
    }

    // Method to find dominant weather condition
    private String findDominantWeatherCondition(List<WeatherData> weatherDataList) {
        Map<String, Integer> conditionCount = new HashMap<>();
        for (WeatherData data : weatherDataList) {
            conditionCount.put(data.getCondition(), conditionCount.getOrDefault(data.getCondition(), 0) + 1);
        }
        return Collections.max(conditionCount.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    // Inner class to represent weather data
    private static class WeatherData {
        private final double temperature; // Temperature in Kelvin
        private final String condition; // Weather condition

        public WeatherData(double temperature, String condition) {
            this.temperature = temperature;
            this.condition = condition;
        }

        public double getTemperature() {
            return temperature;
        }

        public String getCondition() {
            return condition;
        }
    }
}
