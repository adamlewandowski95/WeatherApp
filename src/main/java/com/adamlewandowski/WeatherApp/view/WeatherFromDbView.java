package com.adamlewandowski.WeatherApp.view;

import com.adamlewandowski.WeatherApp.service.WeatherDatabaseService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("WeatherFromDb")
@StyleSheet("/css/style.css")
public class WeatherFromDbView extends VerticalLayout {
    @Autowired
    private WeatherDatabaseService weatherDatabaseService;

    public WeatherFromDbView(){
        Button buttonLoadFromDb = new Button("Załaduj", new Icon(VaadinIcon.BOLT));
        Label labelTest = new Label("Pogoda z Db:");
        Label labelRespond = new Label();
        add(labelTest,buttonLoadFromDb,labelRespond);

        buttonLoadFromDb.addClickShortcut(Key.ENTER);
        buttonLoadFromDb.addClickListener(buttonClickEvent -> {
            labelRespond.setText(weatherDatabaseService.getWeatherForCity().toString());
        });
    }
}
