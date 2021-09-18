package com.adamlewandowski.WeatherApp.repository;

import com.adamlewandowski.WeatherApp.Component.NeededWeatherInformationToDisplay;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NeededWeatherInformationRepository extends JpaRepository<NeededWeatherInformationToDisplay, Long> {
}
