package com.adamlewandowski.gui.service;

import com.adamlewandowski.dto.RequiredInformationDto;
import com.adamlewandowski.dto.WeatherInformationDto;
import com.adamlewandowski.gui.config.Config;
import com.adamlewandowski.gui.model.ModelForDbView;
import com.adamlewandowski.gui.model.ModelForWeatherView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService extends BaseServiceBean {
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    @Autowired
    public WeatherService(RestTemplateBuilder restTemplateBuilder, Config config) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = config.objectMapper();
    }

    public ModelForWeatherView getWeather(RequiredInformationDto requiredInformationDto) {
        HttpEntity<RequiredInformationDto> entity = new HttpEntity<>(requiredInformationDto, getHeaders());
        ResponseEntity<WeatherInformationDto> response = null;
        String url = (BASE_URL + "checkWeather");
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, entity, WeatherInformationDto.class);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
        return convertDtoToWeatherModel(response.getBody());
    }

    public ModelForDbView[] getHistoricalWeatherPage(RequiredInformationDto requiredInformationDto) {
        HttpEntity<RequiredInformationDto> entity = new HttpEntity<>(requiredInformationDto, getHeaders());
        ResponseEntity<WeatherInformationDto[]> response = null;
        String url = (BASE_URL + "checkHistoricalWeather");
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, entity, WeatherInformationDto[].class);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
        return convertDtoToDbModel(response.getBody());
    }

    public int getNumberOfElementsInDb(RequiredInformationDto requiredInformationDto) {
        ResponseEntity<Integer> response = null;
        HttpEntity<RequiredInformationDto> entity = new HttpEntity<>(requiredInformationDto, getHeaders());
        String url = (BASE_URL + "getNumberOfElements");
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, entity, Integer.class);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
        return response.getBody();
    }

    private ModelForWeatherView convertDtoToWeatherModel(WeatherInformationDto weatherInformationDto) {
        ModelForWeatherView modelForWeatherView = objectMapper.convertValue(weatherInformationDto, ModelForWeatherView.class);
        return modelForWeatherView;
    }

    private ModelForDbView[] convertDtoToDbModel(WeatherInformationDto[] weatherInformationDto) {
        ModelForDbView[] modelForDbView = objectMapper.convertValue(weatherInformationDto, ModelForDbView[].class);
        return modelForDbView;
    }

}
