package com.adamlewandowski.service;

import com.adamlewandowski.config.SpringConfig;
import com.adamlewandowski.dao.WeatherDao;
import com.adamlewandowski.dto.RequiredInformationDto;
import com.adamlewandowski.dto.WeatherInformationDto;
import com.adamlewandowski.pojo.WeatherInformation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class WeatherBackendService {
    @Value("${api.key}")
    private String apiKey;

    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;
    private String cityName;
    private WeatherDatabaseService weatherDatabaseService;


    @Autowired
    public WeatherBackendService(RestTemplateBuilder restTemplateBuilder, SpringConfig springConfig, WeatherDatabaseService weatherDatabaseService) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = springConfig.objectMapper();
        this.weatherDatabaseService = weatherDatabaseService;
    }

    public WeatherInformationDto checkWeather(String cityName, String unit){
        WeatherInformation weatherInformation = getWeather(cityName,unit);
        WeatherDao weatherDao =  updateWeatherData(weatherInformation);
        WeatherInformationDto weatherInformationDto = getWeatherForEndpoint(weatherDao);
        weatherDatabaseService.addWeatherForCity(weatherDao);
        return weatherInformationDto;
    }

    private WeatherInformation getWeather(String cityName, String unit) { //
        WeatherInformation weatherInformation = null;
        try {
            String url = ("http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&units=" + unit + "&appid=" + apiKey);
            this.cityName = cityName;
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            JSONObject jsonObject = new JSONObject(response.getBody());
            weatherInformation = objectMapper.readValue(jsonObject.toString(), WeatherInformation.class);

        } catch (JsonProcessingException | JSONException | HttpClientErrorException e) {
            e.printStackTrace();
        }
        return weatherInformation;
    }

    private WeatherDao updateWeatherData(WeatherInformation weatherInformation) {
        java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        int arrayWithInformations = 0;
        WeatherDao weatherDao = new WeatherDao();
        weatherDao.setCityName(cityName);
        weatherDao.setTemperature(Math.round(weatherInformation.getMain().getTemp()));
        weatherDao.setTemperatureFeelsLike(Math.round(weatherInformation.getMain().getFeelsLike()));
        weatherDao.setTemperatureMin(Math.round(weatherInformation.getMain().getTempMin()));
        weatherDao.setTemperatureMax(Math.round(weatherInformation.getMain().getTempMax()));
        weatherDao.setPressure(weatherInformation.getMain().getPressure());
        weatherDao.setHumidity(weatherInformation.getMain().getHumidity());
        weatherDao.setDescription(weatherInformation.getWeather().get(arrayWithInformations).getMain());
        weatherDao.setIcon(weatherInformation.getWeather().get(arrayWithInformations).getIcon());
        weatherDao.setDateAndTime(date);
        return weatherDao;
    }

    private WeatherInformationDto getWeatherForEndpoint(WeatherDao weatherDao){
        WeatherInformationDto weatherInformationDto = objectMapper.convertValue(weatherDao, WeatherInformationDto.class);
        return weatherInformationDto;
    }

    public WeatherInformationDto[] getHistoricalWeatherForEndpoint(){
        List<WeatherDao> weatherDaoList = weatherDatabaseService.getWeatherForAllCites().getBody();
        WeatherInformationDto[] weatherInformationDtoList = convertWeatherDaoToDto(weatherDaoList);
        return weatherInformationDtoList;
    }

    public WeatherInformationDto[] getHistoricalWeatherForSelectedCityEndpoint(String cityName){
        List<WeatherDao> weatherDaoList = weatherDatabaseService.getWeatherForCity(cityName).getBody();
        WeatherInformationDto[] weatherInformationDtoList = convertWeatherDaoToDto(weatherDaoList);
        return weatherInformationDtoList;
    }


    private WeatherInformationDto[] convertWeatherDaoToDto(List<WeatherDao> weatherDaoList){
        WeatherInformationDto[] weatherInformationDtos = objectMapper.convertValue(weatherDaoList, WeatherInformationDto[].class);
        return weatherInformationDtos;
    }

    //nowe
    public Long getNumberOfElements(String cityName){
        long numberOfElements = weatherDatabaseService.countAll(cityName).getBody();
        return numberOfElements;
    }

//    public WeatherInformationDto[] getOnePageOfHistoricalWeather(int page, int numberOfRowsToDisplay){
//        List<WeatherDao> weatherDaoList = weatherDatabaseService.getOnePageOfWeatherForAllCites(page, numberOfRowsToDisplay).getBody();
//        WeatherInformationDto[] weatherInformationDtoList = convertWeatherDaoToDto(weatherDaoList);
//        return weatherInformationDtoList;
//    }
//TEST @@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    public WeatherInformationDto[] getOnePageOfHistoricalWeather(RequiredInformationDto requiredInformationDto){
        List<WeatherDao> weatherDaoList = weatherDatabaseService.getOnePageOfWeatherForAllCites(requiredInformationDto.getPage(), requiredInformationDto.getNumberOfRowsToDisplay()).getBody();
        WeatherInformationDto[] weatherInformationDtoList = convertWeatherDaoToDto(weatherDaoList);
        return weatherInformationDtoList;
    }

//    public WeatherInformationDto[] getOnePageOfHistoricalWeatherForOneCity(String cityname, int page, int numberOfRowsToDisplay){
//        List<WeatherDao> weatherDaoList = weatherDatabaseService.getWeatherOfCityForOnePage(cityname, page, numberOfRowsToDisplay).getBody();
//        WeatherInformationDto[] weatherInformationDtoList = convertWeatherDaoToDto(weatherDaoList);
//        return weatherInformationDtoList;
//    }
//TEST @@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    public WeatherInformationDto[] getOnePageOfHistoricalWeatherForOneCity(RequiredInformationDto requiredInformationDto){

        List<WeatherDao> weatherDaoList = weatherDatabaseService.getWeatherOfCityForOnePage(requiredInformationDto.getCityName(), requiredInformationDto.getPage(), requiredInformationDto.getNumberOfRowsToDisplay()).getBody();
        WeatherInformationDto[] weatherInformationDtoList = convertWeatherDaoToDto(weatherDaoList);
        return weatherInformationDtoList;
    }

}
