<<<<<<< HEAD:frontend/src/main/java/com/adamlewandowski/gui/dao/WeatherDao.java
package com.adamlewandowski.WeatherApp.dao;
=======
package com.adamlewandowski.dao;
>>>>>>> testy:backend/src/main/java/com/adamlewandowski/dao/WeatherDao.java

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "weather")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class WeatherDao implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "city_name")
    private String cityName;
    @Column(name = "temperature")
    private long temperature;
    @Column(name = "temperature_feels_like")
    private long temperatureFeelsLike;
    @Column(name = "temperature_min")
    private long temperatureMin;
    @Column(name = "temperature_max")
    private long temperatureMax;
    @Column(name = "pressure")
    private int pressure;
    @Column(name = "humidity")
    private int humidity;
    @Column(name = "description")
    private String description;
    @Column(name = "icon")
    private String icon;
    @Column(name = "date_and_time")
    private Timestamp dateAndTime;

}
