package com.adamlewandowski.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class WeatherInformationDto {
    private int id;
    private String cityName;
    private long temperature;
    private long temperatureFeelsLike;
    private long temperatureMin;
    private long temperatureMax;
    private int pressure;
    private int humidity;
    private String description;
    private String icon;
    private Timestamp dateAndTime;
}
