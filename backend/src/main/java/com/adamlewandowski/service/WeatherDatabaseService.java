package com.adamlewandowski.service;

import com.adamlewandowski.dao.WeatherDao;
import com.adamlewandowski.repository.WeatherInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherDatabaseService {


    private WeatherInformationRepository weatherInformationRepository;


    @Autowired
    public WeatherDatabaseService(WeatherInformationRepository weatherInformationRepository) {
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
