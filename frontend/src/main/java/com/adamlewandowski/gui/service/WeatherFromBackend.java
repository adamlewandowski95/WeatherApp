package com.adamlewandowski.gui.service;

import com.adamlewandowski.gui.model.WeatherForDbView;
import com.adamlewandowski.gui.model.WeatherForWeatherView;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Service
public class WeatherFromBackend {
    private RestTemplate restTemplate;
    private final String username = "user";
    private final String password = "user123";

    public WeatherFromBackend(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

//    public WeatherForWeatherView getWeather(String cityName, String unit) { //
//
//        ResponseEntity<WeatherForWeatherView> response = null;
//        try {
//            String url = ("http://localhost:8888/checkWeather/city=" + cityName + "/unit=" + unit);
//            response = restTemplate.exchange(url, HttpMethod.GET, null, WeatherForWeatherView.class);
//        } catch (HttpClientErrorException e) {
//            e.printStackTrace();
//        }
//        return response.getBody();
//    }

    private String encode64() {
        String originalInput = username + ":" + password;
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());

        String header = "Basic " + encodedString;
        return header;
    }

public WeatherForWeatherView getWeather(String cityName, String unit) { //

    ResponseEntity<WeatherForWeatherView> response = null;
    HttpHeaders headers = new HttpHeaders();
    try {
//        headers.set("authentiction","Basic dXNlcjp1c2VyMTIz");
        headers.set("authentiction",encode64());
        HttpEntity<String> entity = new HttpEntity<String>("parameters",headers);
        String url = ("http://localhost:8888/checkWeather/city=" + cityName + "/unit=" + unit);
        response = restTemplate.exchange(url, HttpMethod.GET, entity, WeatherForWeatherView.class);
    } catch (HttpClientErrorException e) {
        e.printStackTrace();
    }
    return response.getBody();
}



    public WeatherForDbView[] getHistoricalWeather(String cityName) {
        ResponseEntity<WeatherForDbView[]> response = null;
        if (cityName.isEmpty()) {
            try {
                String url = ("http://localhost:8888/checkHistoricalWeather");
                response = restTemplate.exchange(url, HttpMethod.GET, null, WeatherForDbView[].class);
            } catch (HttpClientErrorException e) {
                e.printStackTrace();
            }
        } else {
            try {
                String url = ("http://localhost:8888/checkHistoricalWeather/city=" + cityName);
                response = restTemplate.exchange(url, HttpMethod.GET, null, WeatherForDbView[].class);
            } catch (HttpClientErrorException e) {
                e.printStackTrace();
            }
        }
        return response.getBody();
    }
}
