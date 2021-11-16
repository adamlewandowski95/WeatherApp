package com.adamlewandowski.gui.service;

import com.adamlewandowski.gui.model.WeatherForDbView;
import com.adamlewandowski.gui.model.WeatherForPrimaryView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService extends BaseServiceBean{
protected RestTemplate restTemplate;

    @Autowired
    public WeatherService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }


public WeatherForPrimaryView getWeather(String cityName, String unit) { //
    ResponseEntity<WeatherForPrimaryView> response = null;
    HttpEntity<String> entity = new HttpEntity<String>("parameters",getHeaders());
    try {
        String url = ("http://localhost:8888/checkWeather/city=" + cityName + "/unit=" + unit);
        response = restTemplate.exchange(url, HttpMethod.GET, entity, WeatherForPrimaryView.class);
    } catch (HttpClientErrorException e) {
        e.printStackTrace();
    }
    return response.getBody();
}



    public WeatherForDbView[] getHistoricalWeather(String cityName) {
        ResponseEntity<WeatherForDbView[]> response = null;
        HttpEntity<String> entity = new HttpEntity<String>("parameters",getHeaders());
        if (cityName.isEmpty()) {
            try {
                String url = ("http://localhost:8888/checkHistoricalWeather");
                response = restTemplate.exchange(url, HttpMethod.GET, entity, WeatherForDbView[].class);
            } catch (HttpClientErrorException e) {
                e.printStackTrace();
            }
        } else {
            try {
                String url = ("http://localhost:8888/checkHistoricalWeather/city=" + cityName);
                response = restTemplate.exchange(url, HttpMethod.GET, entity, WeatherForDbView[].class);
            } catch (HttpClientErrorException e) {
                e.printStackTrace();
            }
        }
        return response.getBody();
    }
    // NOWE
    public WeatherForDbView[] getHistoricalWeatherPage(String cityName, int page, int numberOfRowsToDisplay) {
        ResponseEntity<WeatherForDbView[]> response = null;
        HttpEntity<String> entity = new HttpEntity<String>("parameters",getHeaders());
        if (cityName.isEmpty()) {
            try {
                String url = ("http://localhost:8888/checkHistoricalWeather/page=" + page + "/numberOfRowsToDisplay=" + numberOfRowsToDisplay);
                response = restTemplate.exchange(url, HttpMethod.GET, entity, WeatherForDbView[].class);
            } catch (HttpClientErrorException e) {
                e.printStackTrace();
            }
        } else {
            try {
                String url = ("http://localhost:8888/checkHistoricalWeather/city=" + cityName+ "/page=" + page + "/numberOfRowsToDisplay=" + numberOfRowsToDisplay);
                response = restTemplate.exchange(url, HttpMethod.GET, entity, WeatherForDbView[].class);
            } catch (HttpClientErrorException e) {
                e.printStackTrace();
            }
        }
        return response.getBody();
    }

    public int getNumberOfElementsEndPoint(String cityName){
        ResponseEntity<Integer> response = null;
        HttpEntity<String> entity = new HttpEntity<String>("parameters",getHeaders());
        try {
            String url = ("http://localhost:8888/getNumberOfElements/city=" + cityName);
            response = restTemplate.exchange(url, HttpMethod.GET, entity, Integer.class);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
        return response.getBody();
    }

}
