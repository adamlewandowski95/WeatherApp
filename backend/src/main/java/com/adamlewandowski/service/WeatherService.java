package com.adamlewandowski.service;

import com.adamlewandowski.config.Config;
import com.adamlewandowski.dao.WeatherDao;
import com.adamlewandowski.pojo.Weather;
import com.adamlewandowski.pojo.WeatherForEndpoint;
import com.adamlewandowski.pojo.WeatherInformation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    private RestTemplate restTemplate;
    private ResponseEntity<String> response;
    private String apiKey = "bbe52d970e691ca4a8c62c8d5c9345d1";
    private ObjectMapper objectMapper;
    private String cityName;


    @Autowired
    public WeatherService(RestTemplateBuilder restTemplateBuilder, Config config ) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = config.objectMapper();
    }

    public WeatherInformation getWeather(String cityName, String unit) { //
        WeatherInformation weatherInformation = null;
        try {
            String url = ("http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&units=" + unit + "&appid=" + apiKey);
            this.cityName = cityName;
            response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            JSONObject jsonObject = new JSONObject(response.getBody());
            weatherInformation = objectMapper.readValue(jsonObject.toString(), WeatherInformation.class);
            updateWeatherData(weatherInformation);

        } catch (JsonProcessingException | JSONException | HttpClientErrorException e) {
            e.printStackTrace();
        }
        return weatherInformation;
    }

    public WeatherDao updateWeatherData(WeatherInformation weatherInformation) {
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

    public WeatherForEndpoint getWeatherForEndpoint(WeatherDao weatherDao){
        WeatherForEndpoint weatherForEndpoint = objectMapper.convertValue(weatherDao,WeatherForEndpoint.class);
        return weatherForEndpoint;
    }

//    public WeatherDao savingDatabase(WeatherInformation weatherInformation) {
//        java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
//        int arrayWithInformations = 0;
//        WeatherDao weatherDao = new WeatherDao();
//        weatherDao.setTemperature(Math.round(weatherInformation.getMain().getTemp()));
//        weatherDao.setTemperatureFeelsLike(Math.round(weatherInformation.getMain().getFeelsLike()));
//        weatherDao.setTemperatureMin(Math.round(weatherInformation.getMain().getTempMin()));
//        weatherDao.setTemperatureMax(Math.round(weatherInformation.getMain().getTempMax()));
//        weatherDao.setPressure(weatherInformation.getMain().getPressure());
//        weatherDao.setHumidity(weatherInformation.getMain().getHumidity());
//        weatherDao.setDescription(weatherInformation.getWeather().get(arrayWithInformations).getMain());
//        weatherDao.setIcon(weatherInformation.getWeather().get(arrayWithInformations).getIcon());
//        weatherDao.setDateAndTime(date);
//        return weatherDao;
//    }
}
