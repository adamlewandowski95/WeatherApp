package com.adamlewandowski.controller;

import com.adamlewandowski.dto.WeatherInformationDto;
import com.adamlewandowski.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {
    private WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @RequestMapping(value = "/checkWeather/city={city}/unit={unit}", method = RequestMethod.GET)
    public ResponseEntity<WeatherInformationDto> getWeatherInfo(@PathVariable("city") String city, @PathVariable("unit") String unit) {
        WeatherInformationDto weatherInformationDto = weatherService.checkWeather(city, unit);
        return ResponseEntity.ok(weatherInformationDto);
    }

    @RequestMapping(value = "/checkHistoricalWeather", method = RequestMethod.GET)
    public ResponseEntity<WeatherInformationDto[]> getHistoricalWeather() {
        WeatherInformationDto[] weatherInformationDtoList = weatherService.getHistoricalWeatherForEndpoint();
        return ResponseEntity.ok(weatherInformationDtoList);
    }


}
