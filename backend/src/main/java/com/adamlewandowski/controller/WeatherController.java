package com.adamlewandowski.controller;

import com.adamlewandowski.dto.RequiredInformationDto;
import com.adamlewandowski.dto.WeatherInformationDto;
import com.adamlewandowski.service.WeatherBackendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {
    private WeatherBackendService weatherBackendService;

    @Autowired
    public WeatherController(WeatherBackendService weatherBackendService) {
        this.weatherBackendService = weatherBackendService;
    }

    @RequestMapping(value = "/checkWeather", method = RequestMethod.POST)
    public ResponseEntity<WeatherInformationDto> getWeatherInfo(@RequestBody RequiredInformationDto requiredInformationDto) {
        WeatherInformationDto weatherInformationDto = weatherBackendService.checkWeather(requiredInformationDto);
        return ResponseEntity.ok(weatherInformationDto);
    }

    @RequestMapping(value = "/checkHistoricalWeather", method = RequestMethod.POST)
    public ResponseEntity<WeatherInformationDto[]> getHistoricalWeather(@RequestBody RequiredInformationDto requiredInformationDto) {
        WeatherInformationDto[] weatherInformationDtoList = null;
        weatherInformationDtoList = weatherBackendService.getWeatherDtoForGivenInfo(requiredInformationDto);
        return ResponseEntity.ok(weatherInformationDtoList);
    }

    @RequestMapping(value = "/getNumberOfElements", method = RequestMethod.POST)
    public ResponseEntity<Integer> getNumberOfElements(@RequestBody RequiredInformationDto requiredInformationDto) {
        int numberOfElements = weatherBackendService.getNumberOfElements(requiredInformationDto.getCityName());
        return ResponseEntity.ok(numberOfElements);
    }
}
