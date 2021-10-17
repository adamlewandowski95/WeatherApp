package com.adamlewandowski.dao;

import com.adamlewandowski.WeatherApp.model.WeatherInformationToDisplay;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class WeatherInformationDao implements Dao<WeatherInformationToDisplay> {

    private List<WeatherInformationToDisplay> weatherInformations = new ArrayList<>();

//    @Override
//    public Optional<WeatherInformationToDisplay> get(long id) {
//        return Optional.empty();
//    }

    @Override
    public List<WeatherInformationToDisplay> getAll() {
        return weatherInformations;
    }

    @Override
    public void save(WeatherInformationToDisplay weatherInformationToDisplay) {
        weatherInformations.add(weatherInformationToDisplay);
    }

//    @Override
//    public void update(WeatherInformationToDisplay weatherInformationToDisplay, String[] params) {
//
//    }
//
//    @Override
//    public void delete(WeatherInformationToDisplay weatherInformationToDisplay) {
//
//    }
}
