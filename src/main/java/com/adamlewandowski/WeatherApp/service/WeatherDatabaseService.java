package com.adamlewandowski.WeatherApp.service;

import com.adamlewandowski.WeatherApp.Component.NeededWeatherInformationToDisplay;
import com.adamlewandowski.WeatherApp.config.Config;
import com.adamlewandowski.WeatherApp.repository.NeededWeatherInformationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WeatherDatabaseService {


    private NeededWeatherInformationRepository neededWeatherInformationRepository;
    private ObjectMapper objectMapper;

    @Autowired
    public WeatherDatabaseService(Config config, NeededWeatherInformationRepository neededWeatherInformationRepository){
        this.objectMapper = config.objectMapper();
        this.neededWeatherInformationRepository = neededWeatherInformationRepository;
    }


    public ResponseEntity<List<NeededWeatherInformationToDisplay>> getWeatherForCity(){
        List<NeededWeatherInformationToDisplay> weatherInformationFromDb = neededWeatherInformationRepository.findAll();
        return ResponseEntity.ok(weatherInformationFromDb);
    }
    public ResponseEntity addWeatherForCity(@RequestBody NeededWeatherInformationToDisplay neededWeatherInformationToDisplay){
        NeededWeatherInformationToDisplay savedWeather = neededWeatherInformationRepository.save(neededWeatherInformationToDisplay);
        return ResponseEntity.ok(savedWeather);
    }

}
