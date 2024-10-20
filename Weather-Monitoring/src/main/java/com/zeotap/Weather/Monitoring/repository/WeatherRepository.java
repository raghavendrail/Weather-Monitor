package com.zeotap.Weather.Monitoring.repository;

import com.zeotap.Weather.Monitoring.entites.WeatherData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherData, Long> {
    boolean existsByCityAndDate(String city, LocalDate date);

    @Query("SELECT wd FROM WeatherData wd WHERE wd.city IN :cities AND wd.timestamp >= :timestamp")
    List<WeatherData> findWeatherDataByCityAndTimestamp(@Param("cities") List<String> cities, @Param("timestamp") LocalDateTime timestamp);
    
    List<WeatherData> findAllByCityInAndDateBetween(List<String> cities, LocalDate startDate, LocalDate endDate);
}
