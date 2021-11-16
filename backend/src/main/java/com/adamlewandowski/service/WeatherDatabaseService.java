package com.adamlewandowski.service;

import com.adamlewandowski.dao.WeatherDao;
import com.adamlewandowski.repository.WeatherInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherDatabaseService {


    private int numberOfRowsToDisplay = 25;
    private WeatherInformationRepository weatherInformationRepository;


    @Autowired
    public WeatherDatabaseService(WeatherInformationRepository weatherInformationRepository) {
        this.weatherInformationRepository = weatherInformationRepository;
    }

    public ResponseEntity<List<WeatherDao>> getWeatherForAllCites() {
        List<WeatherDao> weatherInformationFromDb = weatherInformationRepository.findAll();
        return ResponseEntity.ok(weatherInformationFromDb);
    }

    public ResponseEntity<List<WeatherDao>> getWeatherForCity(String cityname) {
        List<WeatherDao> weatherInformationOfCity = weatherInformationRepository.search(cityname);
        return ResponseEntity.ok(weatherInformationOfCity);
    }

    public ResponseEntity addWeatherForCity(WeatherDao weatherDao) {
        WeatherDao savedWeather = weatherInformationRepository.save(weatherDao);
        return ResponseEntity.ok(savedWeather);
    }
    //nowe
    public ResponseEntity<Long> countAll(String cityName) {
        long number = weatherInformationRepository.checkNumberOfElements(cityName);
        return ResponseEntity.ok(number);
    }

    public ResponseEntity countForCity(String cityname) {
        long number = weatherInformationRepository.checkNumberOfElements(cityname);
        return ResponseEntity.ok(number);
    }

    public ResponseEntity<List<WeatherDao>> getWeatherOfCityForOnePage(String cityname, int page, int numberOfRowsToDisplay) {
        this.numberOfRowsToDisplay = numberOfRowsToDisplay;
        List<WeatherDao> weatherInformationOfCity = weatherInformationRepository.searchForOneCityOnePage(cityname, countStartRow(page), numberOfRowsToDisplay);
        return ResponseEntity.ok(weatherInformationOfCity);
    }

    public ResponseEntity<List<WeatherDao>> getOnePageOfWeatherForAllCites(int page, int numberOfRowsToDisplay) {
        this.numberOfRowsToDisplay = numberOfRowsToDisplay;
        List<WeatherDao> weatherInformationFromDb = weatherInformationRepository.searchForAllCicites(countStartRow(page),numberOfRowsToDisplay);
        return ResponseEntity.ok(weatherInformationFromDb);
    }

    private int countStartRow(int page){
        int startRow = 0;
        if(page > 1){
            startRow = numberOfRowsToDisplay * (page - 1);
        }
        return startRow;
    }

}
