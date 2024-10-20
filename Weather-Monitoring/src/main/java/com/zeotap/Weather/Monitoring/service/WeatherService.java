package com.zeotap.Weather.Monitoring.service;

import com.zeotap.Weather.Monitoring.entites.DailyWeatherSummary;
import com.zeotap.Weather.Monitoring.entites.WeatherData;
import com.zeotap.Weather.Monitoring.repository.DailyWeatherSummaryRepository;
import com.zeotap.Weather.Monitoring.repository.WeatherRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WeatherService {

	private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

	private static final Map<String, List<Double>> temperatureRecords = new HashMap<>();

	@Autowired
	private WeatherRepository weatherRepository;

	@Autowired
	private DailyWeatherSummaryRepository dailyWeatherSummaryRepository;

	private final RestTemplate restTemplate = new RestTemplate();

	@Value("${weather.api.url}")
	private String weatherApiUrl;

	@Value("${weather.api.key}")
	private String apiKey;

	@Autowired
	private JavaMailSender emailSender;

	private final List<String> triggeredAlerts = new ArrayList<>();

	public void sendTemperatureAlert(String city, double temperature) {

		String alertMessage = "Alert: The temperature in " + city + " is " + temperature
				+ "°C, which exceeds the threshold!";
		triggeredAlerts.add(alertMessage);

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo("raghashetty30@gmail.com");
		message.setSubject("Temperature Alert for " + city);
		message.setText(
				"Alert: The temperature in " + city + " is " + temperature + "°C, which exceeds the threshold!");
		emailSender.send(message);
	}

	public List<String> getTriggeredAlerts() {
		return new ArrayList<>(triggeredAlerts);

	}

	private static final Map<String, double[]> CITY_COORDINATES = new HashMap<>();

	static {
		CITY_COORDINATES.put("Delhi", new double[] { 28.6139, 77.2090 });
		CITY_COORDINATES.put("Mumbai", new double[] { 19.0760, 72.8777 });
		CITY_COORDINATES.put("Chennai", new double[] { 13.0827, 80.2707 });
		CITY_COORDINATES.put("Bangalore", new double[] { 12.9716, 77.5946 });
		CITY_COORDINATES.put("Kolkata", new double[] { 22.5726, 88.3639 });
		CITY_COORDINATES.put("Hyderabad", new double[] { 17.3850, 78.4867 });
	}

	@Scheduled(fixedRate = 300000) // Every 5 minutes
	public void fetchWeatherDataForCities() {
		logger.info("Fetching weather data for cities at {}", LocalDateTime.now());
		CITY_COORDINATES.forEach((city, coordinates) -> {
			fetchAndSaveWeatherData(city, coordinates[0], coordinates[1]);

		});
		saveDailyWeatherSummaries();
	}

	public void fetchAndSaveWeatherData(String city, double lat, double lon) {

		String url = UriComponentsBuilder.fromHttpUrl(weatherApiUrl).queryParam("lat", lat).queryParam("lon", lon)
				.queryParam("appid", apiKey).toUriString();

		try {
			Map<String, Object> response = restTemplate.getForObject(url, Map.class);
			if (response == null) {
				logger.warn("No response received for city: {}", city);
				return;
			}

			Map<String, Object> main = (Map<String, Object>) response.get("main");
			String condition = ((Map<String, Object>) ((List<?>) response.get("weather")).get(0)).get("main")
					.toString();

			double temperature = extractTemperature(main);

			temperatureRecords.putIfAbsent(city, new ArrayList<>());
			List<Double> temps = temperatureRecords.get(city);
			temps.add(temperature);
			System.out.println("temp:" + temps);

			// Keep only the last 2 temperatures
			if (temps.size() > 2) {
				temps.remove(0);
			}

			// Check if the last two temperatures are more than 35
			if (temps.size() == 2 && temps.get(0) > 35.0 && temps.get(1) > 35.0) {
				logger.info("Temperature in {} has been more than 10 degrees for the last two readings.", city);
				sendTemperatureAlert(city, temperature);

			}

			// Store data only if it doesn't exist for today
			if (!weatherRepository.existsByCityAndDate(city, LocalDate.now())) {
				WeatherData data = new WeatherData();
				data.setCity(city);
				data.setTemperature(temperature);
				data.setWeatherCondition(condition);
				data.setDate(LocalDate.now());
				data.setTimestamp(LocalDateTime.now()); 
				weatherRepository.save(data);
				 logger.info("Weather data saved for city: {}", city);
			} else {
				logger.info("Weather data for city: {} already exists for today.", city);
			}

		} catch (Exception e) {
			 logger.error("Error fetching weather data for {}: {}", city, e.getMessage(),
			 e);
		}
	}

	private double extractTemperature(Map<String, Object> main) {
		Object tempObj = main.get("temp");
		double temperature = (tempObj instanceof Double) ? (Double) tempObj : ((Integer) tempObj).doubleValue();
		return temperature - 273.15; // Convert Kelvin to Celsius
	}

	public List<WeatherData> getAllWeatherData() {
		return weatherRepository.findAll();
	}

	public List<DailyWeatherSummary> getAllCitiesWeatherReport() {
		List<WeatherData> weatherDataList = weatherRepository.findAll();

		Map<String, List<WeatherData>> groupedByCity = weatherDataList.stream()
				.collect(Collectors.groupingBy(WeatherData::getCity));

		List<DailyWeatherSummary> summaries = new ArrayList<>();
		for (Map.Entry<String, List<WeatherData>> entry : groupedByCity.entrySet()) {
			String city = entry.getKey();
			List<WeatherData> dataList = entry.getValue();
			double avgTemperature = dataList.stream().mapToDouble(WeatherData::getTemperature).average().orElse(0);
		
			String commonCondition = dataList.stream()
					.collect(Collectors.groupingBy(WeatherData::getWeatherCondition, Collectors.counting())).entrySet()
					.stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("Unknown");
			

			DailyWeatherSummary summary = new DailyWeatherSummary(city, avgTemperature, commonCondition);
			summaries.add(summary);

		}

		return summaries;
	}

	public void saveDailyWeatherSummaries() {
		List<DailyWeatherSummary> summaries = getAllCitiesWeatherReport();

		for (DailyWeatherSummary summary : summaries) {
			if (!dailyWeatherSummaryRepository.existsByCityAndSummaryDate(summary.getCity(),
					summary.getSummaryDate())) {
				dailyWeatherSummaryRepository.save(summary);
				 logger.info("Saved daily weather summary for city: {}", summary.getCity());
			} else {
				logger.info("Summary for city: {} already exists for date: {}",
				summary.getCity(), summary.getSummaryDate());
			}
		}
	}
}
