package com.adamlewandowski.service;

import com.adamlewandowski.dao.WeatherDao;
import com.adamlewandowski.repository.WeatherInformationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherDatabaseService {


    private WeatherInformationRepository weatherInformationRepository;

    private ObjectMapper objectMapper;


    @Autowired
    public WeatherDatabaseService(ObjectMapper objectMapper, WeatherInformationRepository weatherInformationRepository) {
        this.objectMapper = objectMapper;
        this.weatherInformationRepository = weatherInformationRepository;
    }

    public ResponseEntity<List<WeatherDao>> getWeatherForCity() {
        List<WeatherDao> weatherInformationFromDb = weatherInformationRepository.findAll();
        return ResponseEntity.ok(weatherInformationFromDb);
    }

    public ResponseEntity addWeatherForCity(WeatherDao weatherDao) {
        WeatherDao savedWeather = weatherInformationRepository.save(weatherDao);
        return ResponseEntity.ok(savedWeather);
    }

}
