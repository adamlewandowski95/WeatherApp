package com.adamlewandowski.WeatherApp.service;

import com.adamlewandowski.WeatherApp.config.Config;
import com.adamlewandowski.WeatherApp.model.WeatherInformation;
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

    @Autowired
    public WeatherService(RestTemplateBuilder restTemplateBuilder, Config config) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = config.objectMapper();
    }

    public WeatherInformation getWeather(String cityName, String unit) {
        WeatherInformation weatherInformation = null;
        try {
            String url = ("http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&units=" + unit + "&appid=" + apiKey);
            response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            JSONObject jsonObject = new JSONObject(response.getBody());
            weatherInformation = objectMapper.readValue(jsonObject.toString(), WeatherInformation.class);

        } catch (JsonProcessingException | JSONException | HttpClientErrorException e) {
            e.printStackTrace();
        }
        return weatherInformation;
    }

}
