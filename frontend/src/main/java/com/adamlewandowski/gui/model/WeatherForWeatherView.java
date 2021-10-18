package com.adamlewandowski.gui.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class WeatherForWeatherView {
    private String cityName;
    private long temperature;
    private long temperatureFeelsLike;
    private long temperatureMin;
    private long temperatureMax;
    private int pressure;
    private int humidity;
    private String description;
    private String icon;
}
