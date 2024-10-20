package com.zeotap.Weather.Monitoring.controller;

import com.zeotap.Weather.Monitoring.entites.DailyWeatherSummary;
import com.zeotap.Weather.Monitoring.entites.WeatherData;
import com.zeotap.Weather.Monitoring.service.WeatherService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/weather")
public class WeatherController {

	private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

	@Autowired
	private WeatherService weatherService;

	@GetMapping("/reports")
	public List<WeatherData> getAllWeatherReports() {
		return weatherService.getAllWeatherData();
	}

	@GetMapping("/historical-trends")
	public List<DailyWeatherSummary> getHistoricalTrends() {
		List<DailyWeatherSummary> summaries = weatherService.getAllCitiesWeatherReport();

		// Log each summary with proper info
		summaries.forEach(summary -> 
		logger.info("City: {}, Avg Temp: {}°C, Condition: {}",
				summary.getCity(), summary.getAverageTemperature(), summary.getWeatherCondition())
				);
		return summaries;
	}

	@GetMapping("/dailyWeatherSummary")
	public List<WeatherData> getAllWeatherData() {
		return weatherService.getAllWeatherData();
	}

	@GetMapping("/allCitiesReport")
	public List<DailyWeatherSummary> getAllCitiesReport() {
		return weatherService.getAllCitiesWeatherReport();
	}

	@GetMapping("/dailySummary")
	public List<DailyWeatherSummary> getDailyWeatherSummaries() {
		return weatherService.getAllCitiesWeatherReport();
	}

	@GetMapping("/triggered-alerts")
	public List<String> getTriggeredAlerts() {
		return weatherService.getTriggeredAlerts();
	}
}
