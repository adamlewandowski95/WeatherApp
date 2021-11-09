package com.adamlewandowski.controller;

import com.adamlewandowski.dto.WeatherInformationDto;
import com.adamlewandowski.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
public class WeatherController {
    private WeatherService weatherService;
    private String userName = "user";
    private String pass = "user123";

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    //    @RequestMapping(value = "/checkWeather/city={city}/unit={unit}", method = RequestMethod.GET)
//    public ResponseEntity<WeatherInformationDto> getWeatherInfo(@PathVariable("city") String city, @PathVariable("unit") String unit) {
//        WeatherInformationDto weatherInformationDto = weatherService.checkWeather(city, unit);
//        return ResponseEntity.ok(weatherInformationDto);
//    }
    @RequestMapping(value = "/checkWeather/city={city}/unit={unit}", method = RequestMethod.GET)
    public ResponseEntity<WeatherInformationDto> getWeatherInfo(@RequestHeader("authentiction") String auth, @PathVariable("city") String city, @PathVariable("unit") String unit) {
        if (isAuthenticated(auth)) {
            WeatherInformationDto weatherInformationDto = weatherService.checkWeather(city, unit);
            return ResponseEntity.ok(weatherInformationDto);
        }
        return null;
    }
//@RequestMapping(value = "/auth", method = RequestMethod.GET)
//public String checkLogin(@RequestHeader("authentiction") String auth){
//        if(isAuthenticated(auth)){
//            return "success";
//        } else {
//            return "error";
//        }
//}

    private boolean isAuthenticated(String auth) {
        String decodeString = "";
        String[] authParts = auth.split("\\s+");
        String authInfo = authParts[1];
        byte[] bytes = null;
        bytes = Base64.getDecoder().decode(authInfo);
        decodeString = new String(bytes);
        String[] details = decodeString.split(":");
        String email = details[0];
        String password = details[1];

        return email.equals(userName) && password.equals(pass);
    }


    @RequestMapping(value = "/checkHistoricalWeather", method = RequestMethod.GET)
    public ResponseEntity<WeatherInformationDto[]> getHistoricalWeather() {
        WeatherInformationDto[] weatherInformationDtoList = weatherService.getHistoricalWeatherForEndpoint();
        return ResponseEntity.ok(weatherInformationDtoList);
    }

    @RequestMapping(value = "/checkHistoricalWeather/city={city}", method = RequestMethod.GET)
    public ResponseEntity<WeatherInformationDto[]> getWeatherForCity(@PathVariable("city") String city) {
        WeatherInformationDto[] weatherInformationDtoList = weatherService.getHistoricalWeatherForSelectedCityEndpoint(city);
        return ResponseEntity.ok(weatherInformationDtoList);
    }

}
