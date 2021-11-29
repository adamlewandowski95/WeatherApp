package com.adamlewandowski.gui.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ModelForDbView {

    @CsvBindByPosition(position = 0)
    private int id;

    @CsvBindByPosition(position = 1)
    private String cityName;

    @CsvBindByPosition(position = 2)
    private long temperature;

    @CsvBindByPosition(position = 3)
    private long temperatureFeelsLike;

    @CsvBindByPosition(position = 4)
    private long temperatureMin;

    @CsvBindByPosition(position = 5)
    private long temperatureMax;

    @CsvBindByPosition(position = 6)
    private int pressure;

    @CsvBindByPosition(position = 7)
    private int humidity;

    @CsvBindByPosition(position = 8)
    private String description;

    @CsvBindByPosition(position = 9)
    private String icon;

    @CsvBindByPosition(position = 10)
    private Timestamp dateAndTime;
}


