//package com.adamlewandowski.gui.view;
//
//
//import com.adamlewandowski.gui.language.I18NProviderImplementation;
//import com.adamlewandowski.gui.pojo.HistoricalWeatherPojo;
//import com.adamlewandowski.gui.service.WeatherFromBackend;
//import com.vaadin.flow.component.Key;
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.grid.Grid;
//import com.vaadin.flow.component.html.Image;
//import com.vaadin.flow.component.html.Label;
//import com.vaadin.flow.component.icon.Icon;
//import com.vaadin.flow.component.icon.VaadinIcon;
//import com.vaadin.flow.component.notification.Notification;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.data.renderer.ComponentRenderer;
//import com.vaadin.flow.router.PageTitle;
//import com.vaadin.flow.router.Route;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.Arrays;
//import java.util.List;
//
//
//@Route(value = "weatherfromdb", layout = MainLayoutView.class)
//@PageTitle("Weather from Db")
//public class WeatherFromDbView extends VerticalLayout implements View {
//
//
//    private I18NProviderImplementation i18NProviderImplementation;
//    private WeatherFromBackend weatherFromBackend;
//
//
//    @Autowired
//    public WeatherFromDbView(I18NProviderImplementation i18NProviderImplementation, WeatherFromBackend weatherFromBackend) {
//        this.i18NProviderImplementation = i18NProviderImplementation;
//        this.weatherFromBackend = weatherFromBackend;
//        createView();
//    }
//
//    private void createView() {
//        Label weatherFromDbLabel = new Label(i18NProviderImplementation.getTranslation("weather.info.label"));
//        Button loadFromDbButton = new Button(i18NProviderImplementation.getTranslation("load.button"), new Icon(VaadinIcon.BOLT));
//        add(weatherFromDbLabel, loadFromDbButton);
//
//        Grid<HistoricalWeatherPojo> historicalWeatherTable = new Grid<>(HistoricalWeatherPojo.class);
//
//        tableConfiguration(historicalWeatherTable);
//
//        historicalWeatherTable.getColumns().forEach(col -> col.setAutoWidth(true));
//        addAndExpand(historicalWeatherTable);
//
//        loadFromDbButton.addClickShortcut(Key.ENTER);
//        loadFromDbButton.addClickListener(buttonClickEvent -> {
//
//            Notification.show(i18NProviderImplementation.getTranslation("notification.after.button"));
//        });
//    }
//
//    private Grid tableConfiguration(Grid historicalWeatherTable) {
////        List<HistoricalWeatherPojo> weatherInformationFromDbList = weatherFromBackend.getHistoricalWeather();
//        List<HistoricalWeatherPojo> weatherInformationFromDbList = List.of(weatherFromBackend.getHistoricalWeather());////////////////////////////
//
//        for (HistoricalWeatherPojo singleWeatherInformationFromList : weatherInformationFromDbList) {
//            String description = singleWeatherInformationFromList.getDescription();
//            singleWeatherInformationFromList.setDescription(i18NProviderImplementation.getDescriptionTranslation(description));
//
//        }
//
//
//        historicalWeatherTable.setItems(weatherInformationFromDbList);
//        historicalWeatherTable.setSizeFull();
//        historicalWeatherTable.setColumns("id", "cityName", "dateAndTime", "temperature", "temperatureFeelsLike", "temperatureMax", "temperatureMin", "pressure", "humidity", "description");
//
//        historicalWeatherTable.getColumnByKey("id").setHeader(i18NProviderImplementation.getTranslation("id.column"));
//        historicalWeatherTable.getColumnByKey("cityName").setHeader(i18NProviderImplementation.getTranslation("city.name.column"));
//        historicalWeatherTable.getColumnByKey("dateAndTime").setHeader(i18NProviderImplementation.getTranslation("date.and.time.column"));
//        historicalWeatherTable.getColumnByKey("temperature").setHeader(i18NProviderImplementation.getTranslation("temperature.column"));
//        historicalWeatherTable.getColumnByKey("temperatureFeelsLike").setHeader(i18NProviderImplementation.getTranslation("temperature.feels.like.column"));
//        historicalWeatherTable.getColumnByKey("temperatureMax").setHeader(i18NProviderImplementation.getTranslation("temperature.max.column"));
//        historicalWeatherTable.getColumnByKey("temperatureMin").setHeader(i18NProviderImplementation.getTranslation("temperature.min.column"));
//        historicalWeatherTable.getColumnByKey("pressure").setHeader(i18NProviderImplementation.getTranslation("pressure.column"));
//        historicalWeatherTable.getColumnByKey("humidity").setHeader(i18NProviderImplementation.getTranslation("humidity.column"));
//        historicalWeatherTable.getColumnByKey("description").setHeader(i18NProviderImplementation.getTranslation("description.column"));
//        historicalWeatherTable.addColumn(new ComponentRenderer<>(w -> {
//            Image image = new Image();
//            image.setSrc("http://openweathermap.org/img/wn/" + ((HistoricalWeatherPojo) w).getIcon() + "@2x.png");
//            image.setAlt(i18NProviderImplementation.getTranslation("icon.alt.label"));
//            image.setHeight("30%");
//            image.setWidth("30%");
//            return image;
//        })).setHeader(i18NProviderImplementation.getTranslation("icon.column"));
//
//        return historicalWeatherTable;
//    }
//}
