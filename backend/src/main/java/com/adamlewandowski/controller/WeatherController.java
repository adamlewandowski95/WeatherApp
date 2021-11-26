package com.adamlewandowski.controller;

import com.adamlewandowski.dto.RequiredInformationDto;
import com.adamlewandowski.dto.WeatherInformationDto;
import com.adamlewandowski.service.WeatherBackendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class WeatherController {
    private WeatherBackendService weatherBackendService;
//    private String userName = "user";
//    private String pass = "user123";

    @Autowired
    public WeatherController(WeatherBackendService weatherBackendService) {
        this.weatherBackendService = weatherBackendService;
    }

    @RequestMapping(value = "/checkWeather/city={city}/unit={unit}", method = RequestMethod.GET)
    public ResponseEntity<WeatherInformationDto> getWeatherInfo(@PathVariable("city") String city, @PathVariable("unit") String unit) {
        WeatherInformationDto weatherInformationDto = weatherBackendService.checkWeather(city, unit);
        return ResponseEntity.ok(weatherInformationDto);
    }
//    @RequestMapping(value = "/checkWeather/city={city}/unit={unit}", method = RequestMethod.GET)
//    public ResponseEntity<WeatherInformationDto> getWeatherInfo(@RequestHeader("authentiction") String auth, @PathVariable("city") String city, @PathVariable("unit") String unit) {
//        if (isAuthenticated(auth)) {
//            WeatherInformationDto weatherInformationDto = weatherService.checkWeather(city, unit);
//            return ResponseEntity.ok(weatherInformationDto);
//        }
//        return null;
//    }
//@RequestMapping(value = "/auth", method = RequestMethod.GET)
//public String checkLogin(@RequestHeader("authentiction") String auth){
//        if(isAuthenticated(auth)){
//            return "success";
//        } else {
//            return "error";
//        }
//}

//    private boolean isAuthenticated(String auth) {
//        String decodeString = "";
//        String[] authParts = auth.split("\\s+");
//        String authInfo = authParts[1];
//        byte[] bytes = null;
//        bytes = Base64.getDecoder().decode(authInfo);
//        decodeString = new String(bytes);
//        String[] details = decodeString.split(":");
//        String email = details[0];
//        String password = details[1];
//
//        return email.equals(userName) && password.equals(pass);
//    }

    // bierze wszystkie elementy
    @RequestMapping(value = "/checkHistoricalWeather", method = RequestMethod.GET)
    public ResponseEntity<WeatherInformationDto[]> getHistoricalWeather() {
        WeatherInformationDto[] weatherInformationDtoList = weatherBackendService.getHistoricalWeatherForEndpoint();
        return ResponseEntity.ok(weatherInformationDtoList);
    }

//    @RequestMapping(value = "/checkHistoricalWeather/page={page}/numberOfRowsToDisplay={numberOfRowsToDisplay}", method = RequestMethod.GET)
//    public ResponseEntity<WeatherInformationDto[]> getHistoricalWeather(@PathVariable("page") int page, @PathVariable("numberOfRowsToDisplay") int numberOfRowsToDisplay) {
//        WeatherInformationDto[] weatherInformationDtoList = weatherBackendService.getOnePageOfHistoricalWeather(page, numberOfRowsToDisplay);
//        return ResponseEntity.ok(weatherInformationDtoList);
//    }
    @RequestMapping(value = "/checkHistoricalWeather/Dto", method = RequestMethod.POST)
    public ResponseEntity<WeatherInformationDto[]> getHistoricalWeather(@RequestBody RequiredInformationDto requiredInformationDto) {
        WeatherInformationDto[] weatherInformationDtoList = weatherBackendService.getOnePageOfHistoricalWeather(requiredInformationDto);
        return ResponseEntity.ok(weatherInformationDtoList);
    }

    //    @RequestMapping(value = "/checkHistoricalWeather/city={city}/page={page}/numberOfRowsToDisplay={numberOfRowsToDisplay}", method = RequestMethod.GET)
//    public ResponseEntity<WeatherInformationDto[]> getHistoricalWeatherForCity(@PathVariable("city") String city, @PathVariable("page") int page, @PathVariable("numberOfRowsToDisplay") int numberOfRowsToDisplay) {
//        WeatherInformationDto[] weatherInformationDtoList = weatherBackendService.getOnePageOfHistoricalWeatherForOneCity(city, page, numberOfRowsToDisplay);
//        return ResponseEntity.ok(weatherInformationDtoList);
//    }
    //TEST @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    @RequestMapping(value = "/checkHistoricalWeather/DtoWithCityname", method = RequestMethod.POST)
    public ResponseEntity<WeatherInformationDto[]> getHistoricalWeatherForCity(@RequestBody RequiredInformationDto requiredInformationDto) {
        WeatherInformationDto[] weatherInformationDtoList = weatherBackendService.getOnePageOfHistoricalWeatherForOneCity(requiredInformationDto);
        return ResponseEntity.ok(weatherInformationDtoList);
    }
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    @RequestMapping(value = "/checkHistoricalWeather/city={city}", method = RequestMethod.GET)
    public ResponseEntity<WeatherInformationDto[]> getWeatherForCity(@PathVariable("city") String city) {
        WeatherInformationDto[] weatherInformationDtoList = weatherBackendService.getHistoricalWeatherForSelectedCityEndpoint(city);
        return ResponseEntity.ok(weatherInformationDtoList);
    }


    @RequestMapping(value = "/getNumberOfElements/city={city}", method = RequestMethod.GET)
    public ResponseEntity<Long> getNumberOfElements(@PathVariable("city") String city) {
        long numberOfElements = weatherBackendService.getNumberOfElements(city);
        return ResponseEntity.ok(numberOfElements);
    }

    @RequestMapping(value = "/weather/export", method = RequestMethod.GET)
    public ResponseEntity<String> exportToCSV() {

        return ResponseEntity.ok("ok");
    }

}
