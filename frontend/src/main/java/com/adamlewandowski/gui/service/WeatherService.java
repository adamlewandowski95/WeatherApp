package com.adamlewandowski.gui.service;

import com.adamlewandowski.dto.RequiredInformationDto;
import com.adamlewandowski.gui.model.ModelForDbView;
import com.adamlewandowski.gui.model.ModelForWeatherView;
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


public ModelForWeatherView getWeather(String cityName, String unit) { //
    HttpEntity<String> entity = new HttpEntity<String>("parameters",getHeaders());
    ResponseEntity<ModelForWeatherView> response = null;
    String url = (BASE_URL + "checkWeather/city=" + cityName + "/unit=" + unit);
    try {
        response = restTemplate.exchange(url, HttpMethod.GET, entity, ModelForWeatherView.class);
    } catch (HttpClientErrorException e) {
        e.printStackTrace();
    }
    return response.getBody();
}



    public ModelForDbView[] getHistoricalWeather(String cityName) {
        ResponseEntity<ModelForDbView[]> response = null;
        HttpEntity<String> entity = new HttpEntity<String>("parameters",getHeaders());
        if (cityName.isEmpty()) {
            try {
                String url = (BASE_URL + "checkHistoricalWeather");
                response = restTemplate.exchange(url, HttpMethod.GET, entity, ModelForDbView[].class);
            } catch (HttpClientErrorException e) {
                e.printStackTrace();
            }
        } else {
            try {
                String url = (BASE_URL + "checkHistoricalWeather/city=" + cityName);
                response = restTemplate.exchange(url, HttpMethod.GET, entity, ModelForDbView[].class);
            } catch (HttpClientErrorException e) {
                e.printStackTrace();
            }
        }
        return response.getBody();
    }
    // NOWE
//    public ModelForDbView[] getHistoricalWeatherPage(String cityName, int page, int numberOfRowsToDisplay) {
//        HttpEntity<String> entity = new HttpEntity<String>("parameters",getHeaders());
//        ResponseEntity<ModelForDbView[]> response = null;
//        if (cityName.isEmpty()) {
//            try {
//                String url = (BASE_URL + "checkHistoricalWeather/page=" + page + "/numberOfRowsToDisplay=" + numberOfRowsToDisplay);
//                response = restTemplate.exchange(url, HttpMethod.GET, entity, ModelForDbView[].class);
//            } catch (HttpClientErrorException e) {
//                e.printStackTrace();
//            }
//        } else {
//            try {
//                String url = (BASE_URL +  "checkHistoricalWeather/city=" + cityName+ "/page=" + page + "/numberOfRowsToDisplay=" + numberOfRowsToDisplay);
//                response = restTemplate.exchange(url, HttpMethod.GET, entity, ModelForDbView[].class);
//            } catch (HttpClientErrorException e) {
//                e.printStackTrace();
//            }
//        }
//        return response.getBody();
//    }
    public ModelForDbView[] getHistoricalWeatherPage(RequiredInformationDto requiredInformationDto) {
        HttpEntity<RequiredInformationDto> entity = new HttpEntity<>(requiredInformationDto,getHeaders());
        ResponseEntity<ModelForDbView[]> response = null;
        if (requiredInformationDto.getCityName().isEmpty()) {
            try {
                String url = (BASE_URL + "checkHistoricalWeather/Dto");
                response = restTemplate.exchange(url, HttpMethod.POST, entity, ModelForDbView[].class);
            } catch (HttpClientErrorException e) {
                e.printStackTrace();
            }
        } else {
            try {
                String url = (BASE_URL +  "checkHistoricalWeather/DtoWithCityname");
                response = restTemplate.exchange(url, HttpMethod.POST, entity, ModelForDbView[].class);
            } catch (HttpClientErrorException e) {
                e.printStackTrace();
            }
        }
        return response.getBody();
    }




    public int getNumberOfElementsInDb(String cityName){
        ResponseEntity<Integer> response = null;
        HttpEntity<String> entity = new HttpEntity<String>("parameters",getHeaders());
        try {
            String url = (BASE_URL +  "getNumberOfElements/city=" + cityName);
            response = restTemplate.exchange(url, HttpMethod.GET, entity, Integer.class);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
        return response.getBody();
    }

}
