package com.zeotap.Weather.Monitoring.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class WeatherDataRetrievalTest {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    // Your OpenWeatherMap API key
    private final String apiKey = "e5331d93884e6ee398563a2a6d56114e";

    @Test
    public void testFetchAndParseWeatherData() throws Exception {
        // Sample city name for the API request
        String city = "Delhi"; // Define the city variable

        // Sample API URL with the defined city
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;

        // Make the API call and get the response as a String
        String response = restTemplate.getForObject(url, String.class);
        assertNotNull(response, "API response should not be null");

        // Parse the JSON response
        JsonNode jsonNode = objectMapper.readTree(response);

        // Verify key fields exist in the response
        assertTrue(jsonNode.has("weather"), "Weather field is missing");
        assertTrue(jsonNode.get("weather").isArray(), "Weather field should be an array");

        assertTrue(jsonNode.has("main"), "Main field is missing");
        JsonNode mainNode = jsonNode.get("main");

        // Check individual attributes under "main"
        assertTrue(mainNode.has("temp"), "Temperature is missing");
       
        // Optional: Ensure values are within expected ranges
        double temperature = mainNode.get("temp").asDouble();
        assertTrue(temperature > 200 && temperature < 330, "Temperature out of range");

        

        System.out.println("Weather data parsed successfully: " + response);
    }
    
    
}
