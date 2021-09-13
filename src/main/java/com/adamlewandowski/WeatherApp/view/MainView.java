package com.adamlewandowski.WeatherApp.view;

import com.adamlewandowski.WeatherApp.controller.WeatherService;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;


@Route("Weather")
@StyleSheet("/css/style.css")
public class MainView extends VerticalLayout {

    @Autowired
    private WeatherService weatherService;

    private long temperature;
    private long temperatureFeelsLike;
    private long temperatureMin;
    private long temperatureMax;
    private int pressure;
    private int humidity;

    private String description;
    private String icon;

    public MainView() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        HorizontalLayout mainLayout = new HorizontalLayout();
        // mainLayout.getStyle().set("border", "1px solid #9E9E9E");


        //leftLayout

        Label labelInsertCityName = new Label("City name:");
        labelInsertCityName.getStyle().set("fontWeight", "bold");
        TextField textFieldChooseCity = new TextField();
        textFieldChooseCity.getStyle().set("label", "Alignment.CENTER");
        textFieldChooseCity.getStyle().set("color", "white");
        //textFieldChooseCity.getStyle().set("background", "grey");
        Button buttonSearchCity = new Button("Check Weather", new Icon(VaadinIcon.BOLT));
        buttonSearchCity.setIconAfterText(true);
        buttonSearchCity.getStyle().set("color", "white");
        //buttonSearchCity.getStyle().set("background", "grey");
        Label labelCurrentTempText = new Label();
        labelCurrentTempText.getStyle().set("fontWeight", "bold");
        Label labelCurrentTemp = new Label();


        //middleLayout
        Label labelTemp = new Label();
        Label labelTempFeelsLike = new Label();
        Label labelTempMin = new Label();
        Label labelTempMax = new Label();
        Label labelPressure = new Label();
        Label labelHumidity = new Label();

        //rightLayout
        Image image = new Image();
//        image.setWidth("100px");
//        image.setHeight("100px");

        Label labelSky = new Label();
        labelSky.getStyle().set("fontWeight", "bold");


        VerticalLayout leftLayout = new VerticalLayout();
        leftLayout.add(labelInsertCityName, textFieldChooseCity, buttonSearchCity);


        VerticalLayout middleLayout = new VerticalLayout();
        middleLayout.getStyle().set("fontWeight", "bold");
        middleLayout.addAndExpand();

        VerticalLayout rightLayout = new VerticalLayout();

        mainLayout.add(leftLayout, middleLayout, rightLayout);

        //middleLayout.getStyle().set("border", "1px solid #9E9E9E");
        //middleLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        //leftLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        rightLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(mainLayout);


        buttonSearchCity.addClickListener(buttonClickEvent -> {
                    if (!textFieldChooseCity.isEmpty()) {
                        try {
                            //to daje info o chmurach
                            weatherService.setCityName(textFieldChooseCity.getValue());
                            weatherService.setUnit("metric");

                            JSONArray weatherObject = weatherService.returnWeatherArray();
                            JSONObject mainObject = weatherService.returnMain();
                            icon = weatherObject.getJSONObject(0).getString("icon");
                            image.setSrc("http://openweathermap.org/img/wn/" + icon + "@2x.png");
                            image.setAlt("Image not found");
                            //Image image = new Image("http://openweathermap.org/img/wn/"+ icon +"@2x.png","Image not found");
                            leftLayout.addAndExpand(labelCurrentTempText, labelCurrentTemp);
                            middleLayout.addAndExpand(labelTemp, labelTempFeelsLike, labelTempMin, labelTempMax, labelPressure, labelHumidity);
                            rightLayout.addAndExpand(image, labelSky);
                            //dodać to do oddzielnej metody
                            temperature = (Math.round((Double) mainObject.get("temp")));
                            labelCurrentTempText.setText(textFieldChooseCity.getValue() + " " + temperature + "°C");

                            temperatureFeelsLike = Math.round((Double) mainObject.get("feels_like"));
                            labelTempFeelsLike.setText("Temp feels like: " + temperatureFeelsLike + "°C");

                            temperatureMin = Math.round((Double) mainObject.get("temp_min"));
                            labelTempMin.setText("Temp min: " + temperatureMin + "°C");

                            temperatureMax = Math.round((Double) mainObject.get("temp_max"));
                            labelTempMax.setText("Temp max: " + temperatureMax + "°C");

                            pressure = (Integer) mainObject.get("pressure");
                            labelPressure.setText("Pressure: " + pressure + "hPa");

                            humidity = (Integer) mainObject.get("humidity");
                            labelHumidity.setText("Humidity: " + humidity + "%");

                            description = (weatherObject.getJSONObject(0)).getString("description");
                            labelSky.setText(description);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else
                        Notification.show("Please insert city name!");
                }
        );
    }
}

