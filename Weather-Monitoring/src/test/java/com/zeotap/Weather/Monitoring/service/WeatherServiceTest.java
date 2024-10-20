package com.zeotap.Weather.Monitoring.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;



@SpringBootTest
public class WeatherServiceTest {

    private static final String API_KEY = "e5331d93884e6ee398563a2a6d56114e";  // Replace with your actual API key
    private static final String LAT = "28.6139";  // Delhi's latitude
    private static final String LON = "77.209";   // Delhi's longitude
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    @Test
    public void testSystemStartupAndApiConnection() {
        // Construct the API URL
        String url = BASE_URL + "?lat=" + LAT + "&lon=" + LON + "&appid=" + API_KEY;

        // Use RestTemplate to make the API call
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        // Verify the response is not null
        assertNotNull(response, "API response should not be null");

        // Optionally, verify the response contains the city name (e.g., Delhi)
        boolean containsCityName = response.contains("\"name\":\"Parliament House, Delhi\"");
        assertEquals(true, containsCityName, "The API response should contain the city name");
    }
}
