package com.adamlewandowski.WeatherApp.view;

import com.adamlewandowski.WeatherApp.config.language.I18NProviderImplementation;
import com.adamlewandowski.WeatherApp.dao.WeatherDao;
import com.adamlewandowski.WeatherApp.service.WeatherDatabaseService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "weatherfromdb", layout = MainLayoutView.class)
@PageTitle("Weather from Db")
public class WeatherFromDbView extends VerticalLayout implements View {

    private WeatherDatabaseService weatherDatabaseService;
    private I18NProviderImplementation i18NProviderImplementation;


    @Autowired
    public WeatherFromDbView(WeatherDatabaseService weatherDatabaseService, I18NProviderImplementation i18NProviderImplementation) {
        this.weatherDatabaseService = weatherDatabaseService;
        this.i18NProviderImplementation = i18NProviderImplementation;

        createView();
    }

    private void createView() {
        Label weatherFromDbLabel = new Label(i18NProviderImplementation.getTranslation("weather.info.label"));
        Button loadFromDbButton = new Button(i18NProviderImplementation.getTranslation("load.button"), new Icon(VaadinIcon.BOLT));
        add(weatherFromDbLabel, loadFromDbButton);

        Grid<WeatherDao> historicalWeatherForAllCitiesTable = new Grid<>(WeatherDao.class);

        tableConfiguration(historicalWeatherForAllCitiesTable);

        historicalWeatherForAllCitiesTable.getColumns().forEach(col -> col.setAutoWidth(true));
        addAndExpand(historicalWeatherForAllCitiesTable);

        loadFromDbButton.addClickShortcut(Key.ENTER);
        loadFromDbButton.addClickListener(buttonClickEvent -> {

            Notification.show(i18NProviderImplementation.getTranslation("notification.after.button"));
        });
    }

    private Grid tableConfiguration(Grid historicalWeatherForAllCitiesTable) {
        List<WeatherDao> weatherInformationFromDbList = weatherDatabaseService.getWeatherForCity().getBody();

        for (WeatherDao singleWeatherInformationFromList : weatherInformationFromDbList) {
            String description = singleWeatherInformationFromList.getDescription();
            singleWeatherInformationFromList.setDescription(i18NProviderImplementation.getDescriptionTranslation(description));

        }


        historicalWeatherForAllCitiesTable.setItems(weatherInformationFromDbList);
        historicalWeatherForAllCitiesTable.setSizeFull();
        historicalWeatherForAllCitiesTable.setColumns("id", "cityName", "dateAndTime", "temperature", "temperatureFeelsLike", "temperatureMax", "temperatureMin", "pressure", "humidity", "description");

        historicalWeatherForAllCitiesTable.getColumnByKey("id").setHeader(i18NProviderImplementation.getTranslation("id.column"));
        historicalWeatherForAllCitiesTable.getColumnByKey("cityName").setHeader(i18NProviderImplementation.getTranslation("city.name.column"));
        historicalWeatherForAllCitiesTable.getColumnByKey("dateAndTime").setHeader(i18NProviderImplementation.getTranslation("date.and.time.column"));
        historicalWeatherForAllCitiesTable.getColumnByKey("temperature").setHeader(i18NProviderImplementation.getTranslation("temperature.column"));
        historicalWeatherForAllCitiesTable.getColumnByKey("temperatureFeelsLike").setHeader(i18NProviderImplementation.getTranslation("temperature.feels.like.column"));
        historicalWeatherForAllCitiesTable.getColumnByKey("temperatureMax").setHeader(i18NProviderImplementation.getTranslation("temperature.max.column"));
        historicalWeatherForAllCitiesTable.getColumnByKey("temperatureMin").setHeader(i18NProviderImplementation.getTranslation("temperature.min.column"));
        historicalWeatherForAllCitiesTable.getColumnByKey("pressure").setHeader(i18NProviderImplementation.getTranslation("pressure.column"));
        historicalWeatherForAllCitiesTable.getColumnByKey("humidity").setHeader(i18NProviderImplementation.getTranslation("humidity.column"));
        historicalWeatherForAllCitiesTable.getColumnByKey("description").setHeader(i18NProviderImplementation.getTranslation("description.column"));
        historicalWeatherForAllCitiesTable.addColumn(new ComponentRenderer<>(w -> {
            Image image = new Image();
            image.setSrc("http://openweathermap.org/img/wn/" + ((WeatherDao) w).getIcon() + "@2x.png");
            image.setAlt(i18NProviderImplementation.getTranslation("icon.alt.label"));
            image.setHeight("30%");
            image.setWidth("30%");
            return image;
        })).setHeader(i18NProviderImplementation.getTranslation("icon.column"));

        return historicalWeatherForAllCitiesTable;
    }
}
