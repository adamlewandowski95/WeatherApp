package com.adamlewandowski.WeatherApp.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "weather")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Component
public class WeatherInformationToDisplay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //DAO - do bazy danych    // to na razie zostawaimy DTO - data transfer object
    //interracjonalizacja getI18m
    //klasa tyko dla widoku ModelView
    // poprawa layoutu
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
