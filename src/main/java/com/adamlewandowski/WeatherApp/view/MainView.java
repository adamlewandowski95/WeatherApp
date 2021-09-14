package com.adamlewandowski.WeatherApp.view;

import com.adamlewandowski.WeatherApp.model.WeatherInformation;
import com.adamlewandowski.WeatherApp.service.WeatherService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("Weather")
@StyleSheet("/css/style.css")
public class MainView extends VerticalLayout {

    @Autowired
    private WeatherService weatherService;

    //To może być inna klasa chyba coś np City?
    private long temperature;
    private long temperatureFeelsLike;
    private long temperatureMin;
    private long temperatureMax;
    private int pressure;
    private int humidity;

    //To możliwe że do klasy ImageChooser
    private String description;
    private String icon;

    public MainView() {
        Label labelInsertCityName = new Label("City name:");
        TextField textFieldChooseCity = new TextField();
        Button buttonSearchCity = new Button("Check Weather", new Icon(VaadinIcon.BOLT));
        Label labelCurrentTempText = new Label();
        Label labelTempFeelsLike = new Label();
        Label labelTempMin = new Label();
        Label labelTempMax = new Label();
        Label labelPressure = new Label();
        Label labelHumidity = new Label();
        Image image = new Image();
        Label labelSky = new Label();

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        HorizontalLayout mainLayout = new HorizontalLayout();
        VerticalLayout leftLayout = new VerticalLayout();
        VerticalLayout middleLayout = new VerticalLayout();
        VerticalLayout rightLayout = new VerticalLayout();

        mainLayoutDesign(mainLayout);
        leftLayoutDesign(labelInsertCityName, textFieldChooseCity, buttonSearchCity, labelCurrentTempText);

        leftLayout.add(labelInsertCityName, textFieldChooseCity, buttonSearchCity);
        mainLayout.add(leftLayout, middleLayout, rightLayout);
        mainLayout.getStyle().set("color","white");
        add(mainLayout);

        buttonSearchCity.addClickShortcut(Key.ENTER);
        buttonSearchCity.addClickListener(buttonClickEvent -> {
                    if (!textFieldChooseCity.isEmpty() && (weatherService.getWeather(textFieldChooseCity.getValue(), "metric").getWeather() != null)) {
                        WeatherInformation weatherInformation = weatherService.getWeather(textFieldChooseCity.getValue(), "metric");
                        icon = weatherInformation.getWeather().get(0).getIcon();
                        image.setSrc("http://openweathermap.org/img/wn/" + icon + "@2x.png");
                        image.setAlt("Image not found");
                        leftLayout.addAndExpand(labelCurrentTempText);
                        midLayoutDesign(middleLayout);
                        middleLayout.addAndExpand(labelTempFeelsLike, labelTempMin, labelTempMax, labelPressure, labelHumidity);
                        rightLayoutDesign(labelSky, rightLayout);
                        rightLayout.addAndExpand(image, labelSky);

                        updateWeatherData(weatherInformation);

                        labelCurrentTempText.setText(textFieldChooseCity.getValue() + " " + temperature + "°C");
                        labelTempFeelsLike.setText("Temp feels like: " + temperatureFeelsLike + "°C");
                        labelTempMin.setText("Temp min: " + temperatureMin + "°C");
                        labelTempMax.setText("Temp max: " + temperatureMax + "°C");
                        labelPressure.setText("Pressure: " + pressure + "hPa");
                        labelHumidity.setText("Humidity: " + humidity + "%");
                        labelSky.setText(description);
                    } else
                        Notification.show("Please insert correct city name!");
                }
        );
    }

    private void mainLayoutDesign(HorizontalLayout mainLayout) {

    }

    private void leftLayoutDesign(Label labelInsertCityName, TextField textFieldChooseCity, Button buttonSearchCity, Label labelCurrentTempText) {
        labelInsertCityName.getStyle().set("fontWeight", "bold");
        textFieldChooseCity.getStyle().set("label", "Alignment.CENTER");
        textFieldChooseCity.getStyle().set("color", "black");
        buttonSearchCity.setIconAfterText(true);
        buttonSearchCity.getStyle().set("color", "white");
        labelCurrentTempText.getStyle().set("fontWeight", "bold");
        textFieldChooseCity.getStyle().set("background", "LightSteelBlue");
        buttonSearchCity.getStyle().set("background", "PowderBlue");
    }

    private void midLayoutDesign(VerticalLayout middleLayout) {
        middleLayout.setWidth("500px");
        middleLayout.getStyle().set("fontWeight", "bold");
    }

    private void rightLayoutDesign(Label labelSky, VerticalLayout rightLayout) {
        rightLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        labelSky.getStyle().set("fontWeight", "bold");
    }

    private void updateWeatherData(WeatherInformation weatherInformation) {
        temperature = Math.round(weatherInformation.getMain().getTemp());
        temperatureFeelsLike = Math.round(weatherInformation.getMain().getFeelsLike());
        temperatureMin = Math.round(weatherInformation.getMain().getTempMin());
        temperatureMax = Math.round(weatherInformation.getMain().getTempMax());
        pressure = weatherInformation.getMain().getPressure();
        humidity = weatherInformation.getMain().getHumidity();
        description = weatherInformation.getWeather().get(0).getDescription();
    }

}

