package com.zeotap.Weather.Monitoring.repository;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.zeotap.Weather.Monitoring.entites.DailyWeatherSummary;

@Repository
public interface DailyWeatherSummaryRepository extends JpaRepository<DailyWeatherSummary, Long> {
    @Query("SELECT COUNT(w) > 0 FROM DailyWeatherSummary w WHERE w.city = :city AND w.summaryDate = :date")
    boolean existsByCityAndSummaryDate(@Param("city") String city, @Param("date") LocalDate date);
}
