package com.adamlewandowski.WeatherApp.repository;

import com.adamlewandowski.WeatherApp.component.WeatherInformationToDisplay;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NeededWeatherInformationRepository extends JpaRepository<WeatherInformationToDisplay, Long> {
}
