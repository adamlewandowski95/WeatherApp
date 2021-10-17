package com.adamlewandowski.controller;

import com.adamlewandowski.converter.ObjectToJson;
import com.adamlewandowski.dao.WeatherDao;
import com.adamlewandowski.pojo.WeatherForEndpoint;
import com.adamlewandowski.pojo.WeatherInformation;
import com.adamlewandowski.service.WeatherDatabaseService;
import com.adamlewandowski.service.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WeatherController {
    private ObjectMapper objectMapper;
    private WeatherService weatherService;
    private ObjectToJson objectToJson;
    private WeatherDatabaseService weatherDatabaseService;
    @Autowired
    public WeatherController(WeatherService weatherService, ObjectToJson objectToJson, WeatherDatabaseService weatherDatabaseService, ObjectMapper objectMapper) {
        this.weatherService = weatherService;
        this.objectToJson = objectToJson;
        this.weatherDatabaseService = weatherDatabaseService;
        this.objectMapper = objectMapper;

    }

    @RequestMapping(value = "/checkWeather/city={city}/unit={unit}", method = RequestMethod.GET)
    public ResponseEntity<WeatherForEndpoint> getWeatherInfo(@PathVariable("city") String city, @PathVariable("unit") String unit){
         WeatherInformation weatherInformation = weatherService.getWeather(city,unit);
         WeatherDao weatherDao =  weatherService.updateWeatherData(weatherInformation);
         WeatherForEndpoint weatherForEndpoint = weatherService.getWeatherForEndpoint(weatherDao);
        weatherDatabaseService.addWeatherForCity(weatherDao);
//        return objectToJson.convertToJson(weatherForEndpoint);
        return ResponseEntity.ok(weatherForEndpoint);
    }

    @RequestMapping(value = "/checkHistoricalWeather", method = RequestMethod.GET)
    public String getHistoricalWeather(){
        List<WeatherDao> weatherDaoList = weatherDatabaseService.getWeatherForCity().getBody();
        //WeatherDao weatherDao = objectMapper.convertValue(weatherDao,WeatherForEndpoint.class);
        String jsonStr = "";
        try{
            jsonStr = objectMapper.writeValueAsString(weatherDaoList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }


}
