package com.adamlewandowski.WeatherApp.service;

import com.adamlewandowski.WeatherApp.model.WeatherInformation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class WeatherService {
    private OkHttpClient client;
    private Response response;
    private String apiKey = "bbe52d970e691ca4a8c62c8d5c9345d1";




    public WeatherInformation getWeather(String cityName, String unit){
        client = new OkHttpClient(); //rest template "Strping rest" zamienić
        ObjectMapper objectMapper = new ObjectMapper(); //wyrzuć maper do konfiguracji configuration
        JSONObject jsonObject = new JSONObject();
        Request request = new Request.Builder()
                .url("http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&units=" + unit + "&appid=" + apiKey)
                .build();

        try {
            response = client.newCall(request).execute();
            jsonObject = new JSONObject(response.body().string());


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        WeatherInformation weatherInformation = null;
        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            weatherInformation = objectMapper.readValue(jsonObject.toString(), WeatherInformation.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return weatherInformation;
    }
}
