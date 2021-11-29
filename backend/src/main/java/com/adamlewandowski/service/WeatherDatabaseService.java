package com.adamlewandowski.service;

import com.adamlewandowski.dao.WeatherDao;
import com.adamlewandowski.dto.RequiredInformationDto;
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

    public ResponseEntity addWeatherToDb(WeatherDao weatherDao) {
        WeatherDao savedWeather = weatherInformationRepository.save(weatherDao);
        return ResponseEntity.ok(savedWeather);
    }

    public ResponseEntity<Integer> countAll(String cityName) {
        int number = weatherInformationRepository.checkNumberOfElements(cityName);
        return ResponseEntity.ok(number);
    }

        public ResponseEntity<List<WeatherDao>> getListOfWeatherDao(RequiredInformationDto requiredInformationDto) {
        List<WeatherDao> weatherInformationFromDb;
            if (requiredInformationDto.getCityName().isEmpty()) {
                weatherInformationFromDb = weatherInformationRepository.searchForAllCicites(countStartRow(requiredInformationDto),requiredInformationDto.getNumberOfRowsToDisplay());
            } else {
                weatherInformationFromDb = weatherInformationRepository.searchForOneCityOnePage(requiredInformationDto.getCityName(), countStartRow(requiredInformationDto), requiredInformationDto.getNumberOfRowsToDisplay());
            }
        return ResponseEntity.ok(weatherInformationFromDb);
    }

    //    public ResponseEntity<List<WeatherDao>> getAllFromDbForAllCites() {
//        List<WeatherDao> weatherInformationFromDb = weatherInformationRepository.findAll();
//        return ResponseEntity.ok(weatherInformationFromDb);
//    }
//
//    public ResponseEntity<List<WeatherDao>> getAllFromDbForCityname(String cityname) {
//        List<WeatherDao> weatherInformationOfCity = weatherInformationRepository.search(cityname);
//        return ResponseEntity.ok(weatherInformationOfCity);
//    }

    private int countStartRow(RequiredInformationDto requiredInformationDto){
        int startRow = 0;
        if(requiredInformationDto.getPage() > 1){
            startRow = requiredInformationDto.getNumberOfRowsToDisplay() * (requiredInformationDto.getPage() - 1);
        }
        return startRow;
    }

}
