package com.adamlewandowski.gui.service;

import com.adamlewandowski.gui.pojo.HistoricalWeatherPojo;
import com.adamlewandowski.gui.pojo.WeatherForEndpoint;
import com.adamlewandowski.gui.pojo.WeatherPojo;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class WeatherFromBackend {
    private RestTemplate restTemplate;
//    private ResponseEntity<String> response;
    private ObjectMapper objectMapper;

    public WeatherFromBackend(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
    }

//    public WeatherPojo getWeather(String cityName, String unit) { //
//        WeatherPojo weatherPojo = null;
//        try {
//            String url = ("http://localhost:8888/checkWeather/city=" + cityName + "/unit=" + unit);
//            response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
//            JSONObject jsonObject = new JSONObject(response.getBody());
//            weatherPojo = objectMapper.readValue(jsonObject.toString(), WeatherPojo.class);
//
//        } catch (JsonProcessingException | JSONException | HttpClientErrorException e) {
//            e.printStackTrace();
//        }
//        return weatherPojo;
//    }

        public WeatherForEndpoint getWeather(String cityName, String unit) { //
            //WeatherPojo weatherPojo = null;
            ResponseEntity<WeatherForEndpoint> response = null;
        try {
            String url = ("http://localhost:8888/checkWeather/city=" + cityName + "/unit=" + unit);
             response = restTemplate.exchange(url, HttpMethod.GET, null, WeatherForEndpoint.class);
        } catch ( HttpClientErrorException e) {
            e.printStackTrace();
        }
        return response.getBody();
    }


    public List<HistoricalWeatherPojo> getHistoricalWeather() { //
    public HistoricalWeatherPojo[] getHistoricalWeather() { //
            HistoricalWeatherPojo[] historicalWeatherPojo = new HistoricalWeatherPojo[0];
       // List<HistoricalWeatherPojo> historicalWeatherPojo = null;
        try {
            String url = ("http://localhost:8888/checkHistoricalWeather");
            response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            JSONObject jsonObject = new JSONObject(response.getBody());
//            historicalWeatherPojo = objectMapper.readValue(jsonObject.toString(), HistoricalWeatherPojo[].class);
      //       historicalWeatherPojo = objectMapper.readValue(jsonObject.toString(), new TypeReference<List<HistoricalWeatherPojo>>(){});
           // historicalWeatherPojo = Arrays.asList(objectMapper.readValue(jsonObject.toString(), HistoricalWeatherPojo.class));
            historicalWeatherPojo = objectMapper.readValue(jsonObject.toString(), HistoricalWeatherPojo[].class);
        } catch (JsonProcessingException | JSONException | HttpClientErrorException e) {
            e.printStackTrace();
        }
        return historicalWeatherPojo;
    }
}
