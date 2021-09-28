package com.adamlewandowski.WeatherApp.view;

import com.adamlewandowski.WeatherApp.model.WeatherInformationToDisplay;
import com.adamlewandowski.WeatherApp.service.WeatherDatabaseService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

@Route(value = "weatherfromdb", layout = MainLayoutView.class)
@PageTitle("Weather from Db")
public class WeatherFromDbView extends VerticalLayout implements View {

    private WeatherDatabaseService weatherDatabaseService;

    @Autowired
    public WeatherFromDbView(WeatherDatabaseService weatherDatabaseService) {
        this.weatherDatabaseService = weatherDatabaseService;
        createView();
    }

    private void createView() {
        Button buttonLoadFromDb = new Button("Załaduj", new Icon(VaadinIcon.BOLT));
        Label labelTest = new Label("Pogoda z Db:");
        Label labelRespond = new Label();
        add(labelTest, buttonLoadFromDb, labelRespond);

        Grid<WeatherInformationToDisplay> table = new Grid<>(WeatherInformationToDisplay.class);

        List<WeatherInformationToDisplay> weatherList = weatherDatabaseService.getWeatherForCity().getBody();
        table.setItems(weatherList);
        table.setSizeFull();

        table.setColumns("id", "cityName", "dateAndTime", "temperature", "temperatureFeelsLike", "temperatureMax", "temperatureMin", "pressure", "humidity", "description", "icon");
        table.getColumns().forEach(col -> col.setAutoWidth(true));
        addAndExpand(table);

        buttonLoadFromDb.addClickShortcut(Key.ENTER);
        buttonLoadFromDb.addClickListener(buttonClickEvent -> {
            Objects.requireNonNull(weatherList).removeAll(weatherList);
            weatherList.addAll(Objects.requireNonNull(weatherDatabaseService.getWeatherForCity().getBody()));
            table.setItems(weatherList);
            Notification.show("Button works!");
        });
    }
}
