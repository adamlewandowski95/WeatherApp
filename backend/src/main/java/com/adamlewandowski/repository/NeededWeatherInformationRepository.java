package com.adamlewandowski.repository;

import com.adamlewandowski.WeatherApp.model.WeatherInformationToDisplay;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NeededWeatherInformationRepository extends JpaRepository<WeatherInformationToDisplay, Long> {
}
