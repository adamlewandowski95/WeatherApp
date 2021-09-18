package com.adamlewandowski.WeatherApp.view;

import com.adamlewandowski.WeatherApp.Component.NeededWeatherInformationToDisplay;
import com.adamlewandowski.WeatherApp.model.WeatherInformation;
import com.adamlewandowski.WeatherApp.service.WeatherDatabaseService;
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
public class WeatherView extends VerticalLayout {

    @Autowired
    private NeededWeatherInformationToDisplay neededWeatherInformationToDisplay;
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private WeatherDatabaseService weatherDatabaseService;


    public WeatherView() {
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
        mainLayout.getStyle().set("color", "white");
        add(mainLayout);

        buttonSearchCity.addClickShortcut(Key.ENTER);
        buttonSearchCity.addClickListener(buttonClickEvent -> {
                    WeatherInformation weatherInformation = weatherService.getWeather(textFieldChooseCity.getValue(), "metric");
                    if (!textFieldChooseCity.isEmpty() && weatherInformation != null) {

                        neededWeatherInformationToDisplay.setCityName(textFieldChooseCity.getValue());
                        leftLayout.addAndExpand(labelCurrentTempText);
                        midLayoutDesign(middleLayout);
                        middleLayout.addAndExpand(labelTempFeelsLike, labelTempMin, labelTempMax, labelPressure, labelHumidity);
                        rightLayoutDesign(labelSky, rightLayout);
                        rightLayout.addAndExpand(image, labelSky);

                        labelCurrentTempText.setText(textFieldChooseCity.getValue() + " " + neededWeatherInformationToDisplay.getTemperature() + "°C");
                        labelTempFeelsLike.setText("Temp feels like: " + neededWeatherInformationToDisplay.getTemperatureFeelsLike() + "°C");
                        labelTempMin.setText("Temp min: " + neededWeatherInformationToDisplay.getTemperatureMin() + "°C");
                        labelTempMax.setText("Temp max: " + neededWeatherInformationToDisplay.getTemperatureMax() + "°C");
                        labelPressure.setText("Pressure: " + neededWeatherInformationToDisplay.getPressure() + "hPa");
                        labelHumidity.setText("Humidity: " + neededWeatherInformationToDisplay.getHumidity() + "%");
                        labelSky.setText(neededWeatherInformationToDisplay.getDescription());
                        image.setSrc("http://openweathermap.org/img/wn/" + neededWeatherInformationToDisplay.getIcon() + "@2x.png");
                        image.setAlt("Image not found");

                        weatherDatabaseService.addWeatherForCity(neededWeatherInformationToDisplay);
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
}

