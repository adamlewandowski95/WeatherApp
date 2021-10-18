package com.adamlewandowski.gui.view;

import com.adamlewandowski.gui.language.I18NProviderImplementation;
import com.adamlewandowski.gui.model.WeatherForDbView;
import com.adamlewandowski.gui.service.WeatherFromBackend;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Route(value = "weatherfromdb", layout = MainLayoutView.class)
@PageTitle("Weather from Db")
public class WeatherFromDbView extends VerticalLayout implements View {

    private Label weatherFromDbLabel;
    private TextField filterText = new TextField();
    private Grid<WeatherForDbView> weatherTable = new Grid<>(WeatherForDbView.class);
    private I18NProviderImplementation i18NProviderImplementation;
    private WeatherFromBackend weatherFromBackend;


    @Autowired
    public WeatherFromDbView(I18NProviderImplementation i18NProviderImplementation, WeatherFromBackend weatherFromBackend) {
        this.i18NProviderImplementation = i18NProviderImplementation;
        this.weatherFromBackend = weatherFromBackend;
        createView();
    }

    private void createView() {
        configureFilter();
        configureTable();
        add(weatherFromDbLabel, filterText);
        addAndExpand(weatherTable);
        updateList();
    }

    private void updateList() {
        List<WeatherForDbView> weatherInformationFromDbList = List.of(weatherFromBackend.getHistoricalWeather(filterText.getValue()));
        for (WeatherForDbView singleInformationFromList : weatherInformationFromDbList) {
            String description = singleInformationFromList.getDescription();
            singleInformationFromList.setDescription(i18NProviderImplementation.getDescriptionTranslation(description));
        }
        weatherTable.setItems(weatherInformationFromDbList);
    }

    private void configureFilter() {
        weatherFromDbLabel = new Label(i18NProviderImplementation.getTranslation("weather.info.label"));
        filterText.setPlaceholder(i18NProviderImplementation.getTranslation("filter.textfiled"));
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
    }


    private void configureTable() {
        weatherTable.setSizeFull();
        weatherTable.setColumns("id", "cityName", "dateAndTime", "temperature", "temperatureFeelsLike", "temperatureMax", "temperatureMin", "pressure", "humidity", "description");
        weatherTable.getColumnByKey("id").setHeader(i18NProviderImplementation.getTranslation("id.column"));
        weatherTable.getColumnByKey("cityName").setHeader(i18NProviderImplementation.getTranslation("city.name.column"));
        weatherTable.getColumnByKey("dateAndTime").setHeader(i18NProviderImplementation.getTranslation("date.and.time.column"));
        weatherTable.getColumnByKey("temperature").setHeader(i18NProviderImplementation.getTranslation("temperature.column"));
        weatherTable.getColumnByKey("temperatureFeelsLike").setHeader(i18NProviderImplementation.getTranslation("temperature.feels.like.column"));
        weatherTable.getColumnByKey("temperatureMax").setHeader(i18NProviderImplementation.getTranslation("temperature.max.column"));
        weatherTable.getColumnByKey("temperatureMin").setHeader(i18NProviderImplementation.getTranslation("temperature.min.column"));
        weatherTable.getColumnByKey("pressure").setHeader(i18NProviderImplementation.getTranslation("pressure.column"));
        weatherTable.getColumnByKey("humidity").setHeader(i18NProviderImplementation.getTranslation("humidity.column"));
        weatherTable.getColumnByKey("description").setHeader(i18NProviderImplementation.getTranslation("description.column"));
        weatherTable.addColumn(new ComponentRenderer<>(w -> {
            Image image = new Image();
            image.setSrc("http://openweathermap.org/img/wn/" + ((WeatherForDbView) w).getIcon() + "@2x.png");
            image.setAlt(i18NProviderImplementation.getTranslation("icon.alt.label"));
            image.setHeight("30%");
            image.setWidth("30%");
            return image;
        })).setHeader(i18NProviderImplementation.getTranslation("icon.column"));
        weatherTable.getColumns().forEach(col -> col.setAutoWidth(true));
    }
}