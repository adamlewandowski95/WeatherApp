package com.adamlewandowski.converter;


import com.adamlewandowski.dao.WeatherDao;
import com.adamlewandowski.pojo.WeatherForEndpoint;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ObjectToJson {
    ObjectMapper objectMapper;
    //WeatherForEndpoint weatherForEndpoint;

    @Autowired
    public ObjectToJson(ObjectMapper objectMapper) { //WeatherForEndpoint weatherForEndpoint
        this.objectMapper = objectMapper;
       // this.weatherForEndpoint = weatherForEndpoint;
    }

    public String convertToJson(WeatherForEndpoint weatherForEndpoint){
        String jsonStr = "Bledne dane";
        try{
            jsonStr = objectMapper.writeValueAsString(weatherForEndpoint);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(jsonStr);
        return jsonStr;
    }

}
