package com.adamlewandowski.WeatherApp.view;

import com.adamlewandowski.WeatherApp.component.WeatherInformationToDisplay;
import com.adamlewandowski.WeatherApp.service.WeatherDatabaseService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

@Route(value = "weatherfromdb", layout = MainLayoutView.class)
@PageTitle("Weather from Db")
public class WeatherFromDbView extends VerticalLayout {

    private WeatherDatabaseService weatherDatabaseService;

    @Autowired
    public WeatherFromDbView(WeatherDatabaseService weatherDatabaseService) {
        this.weatherDatabaseService = weatherDatabaseService;
        createView();
    }

    public void createView() {
        Button buttonLoadFromDb = new Button("Załaduj", new Icon(VaadinIcon.BOLT));
        Label labelTest = new Label("Pogoda z Db:");
        Label labelRespond = new Label();
        add(labelTest, buttonLoadFromDb, labelRespond);

        Grid<WeatherInformationToDisplay> table = new Grid<>();
        table.addColumn(WeatherInformationToDisplay::getId).setHeader("Id");
        table.addColumn(WeatherInformationToDisplay::getCityName).setHeader("City");
        table.addColumn(WeatherInformationToDisplay::getTemperature).setHeader("Temperature");
        table.addColumn(WeatherInformationToDisplay::getTemperatureFeelsLike).setHeader("Temp feels like");
        table.addColumn(WeatherInformationToDisplay::getTemperatureMin).setHeader("Temp min");
        table.addColumn(WeatherInformationToDisplay::getTemperatureMax).setHeader("Temp max");
        table.addColumn(WeatherInformationToDisplay::getPressure).setHeader("Pressure");
        table.addColumn(WeatherInformationToDisplay::getHumidity).setHeader("Humidity");
        table.addColumn(WeatherInformationToDisplay::getDescription).setHeader("Description");
        table.addColumn(WeatherInformationToDisplay::getDateAndTime).setHeader("Date and time");

        List<WeatherInformationToDisplay> weatherList = weatherDatabaseService.getWeatherForCity().getBody();
        table.setItems(weatherList);
        buttonLoadFromDb.addClickShortcut(Key.ENTER);
        buttonLoadFromDb.addClickListener(buttonClickEvent -> {


            Objects.requireNonNull(weatherList).removeAll(weatherList);
            weatherList.addAll(Objects.requireNonNull(weatherDatabaseService.getWeatherForCity().getBody()));
            table.setItems(weatherList);
        });
        addAndExpand(table);

    }
}
