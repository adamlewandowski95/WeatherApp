package com.adamlewandowski.WeatherApp.service;

import com.adamlewandowski.WeatherApp.component.WeatherInformationToDisplay;
import com.adamlewandowski.WeatherApp.repository.NeededWeatherInformationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class WeatherDatabaseService {


    private NeededWeatherInformationRepository neededWeatherInformationRepository;

    private ObjectMapper objectMapper;


    @Autowired
    public WeatherDatabaseService(ObjectMapper objectMapper, NeededWeatherInformationRepository neededWeatherInformationRepository) {
        this.objectMapper = objectMapper;
        this.neededWeatherInformationRepository = neededWeatherInformationRepository;

    }


    public ResponseEntity<List<WeatherInformationToDisplay>> getWeatherForCity() {
        List<WeatherInformationToDisplay> weatherInformationFromDb = neededWeatherInformationRepository.findAll();
        return ResponseEntity.ok(weatherInformationFromDb);
    }

    public ResponseEntity addWeatherForCity(@RequestBody WeatherInformationToDisplay neededWeatherInformationToDisplay) {
        WeatherInformationToDisplay savedWeather = neededWeatherInformationRepository.save(neededWeatherInformationToDisplay);
        return ResponseEntity.ok(savedWeather);
    }

}
