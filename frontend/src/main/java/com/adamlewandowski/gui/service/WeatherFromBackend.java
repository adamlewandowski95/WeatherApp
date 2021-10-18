package com.adamlewandowski.gui.service;

import com.adamlewandowski.gui.model.WeatherForDbView;
import com.adamlewandowski.gui.model.WeatherForWeatherView;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherFromBackend {
    private RestTemplate restTemplate;

    public WeatherFromBackend(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public WeatherForWeatherView getWeather(String cityName, String unit) { //

        ResponseEntity<WeatherForWeatherView> response = null;
        try {
            String url = ("http://localhost:8888/checkWeather/city=" + cityName + "/unit=" + unit);
            response = restTemplate.exchange(url, HttpMethod.GET, null, WeatherForWeatherView.class);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
        return response.getBody();
    }


    public WeatherForDbView[] getHistoricalWeather() {
        ResponseEntity<WeatherForDbView[]> response = null;
        try {
            String url = ("http://localhost:8888/checkHistoricalWeather");
            response = restTemplate.exchange(url, HttpMethod.GET, null, WeatherForDbView[].class);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
        return response.getBody();
    }
}
