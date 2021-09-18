package com.adamlewandowski.WeatherApp.Component;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name = "weather")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Component
public class NeededWeatherInformationToDisplay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    //private int currentDate;
}
