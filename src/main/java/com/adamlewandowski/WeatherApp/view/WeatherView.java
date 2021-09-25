package com.adamlewandowski.WeatherApp.view;

import com.adamlewandowski.WeatherApp.model.WeatherInformationToDisplay;
import com.adamlewandowski.WeatherApp.model.pojo.WeatherInformation;
import com.adamlewandowski.WeatherApp.service.WeatherDatabaseService;
import com.adamlewandowski.WeatherApp.service.WeatherService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "", layout = MainLayoutView.class)
@RouteAlias(value = "", layout = MainLayoutView.class)
@PageTitle("Weather")
public class WeatherView extends VerticalLayout implements View{

    //przeprobić na DAO
    private WeatherInformationToDisplay WeatherInformationToDisplay;
    //
    private WeatherService weatherService;
    private WeatherDatabaseService weatherDatabaseService;

    @Autowired
    public WeatherView(WeatherInformationToDisplay WeatherInformationToDisplay, WeatherService weatherService, WeatherDatabaseService weatherDatabaseService) {
        this.WeatherInformationToDisplay = WeatherInformationToDisplay;
        this.weatherService = weatherService;
        this.weatherDatabaseService = weatherDatabaseService;
        createView();
    }

    private void createView() {
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
       HorizontalLayout mainLayout = new HorizontalLayout();
       VerticalLayout leftLayout = new VerticalLayout();
       VerticalLayout middleLayout = new VerticalLayout();
       VerticalLayout rightLayout = new VerticalLayout();

        //Testowe wdrożenie tego pomysłu z HashMapą (Zrobiłem tylko dla labelInsertCityName bo ten pomysł troche słaby mi sie wydaje.
        //HashMap <String,Label> labelHashMap = makeLabels();
       // ArrayList<Label> labelArrayList = makeLabels();

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        mainLayoutDesign(mainLayout);
        //leftLayoutDesign(labelInsertCityName, textFieldChooseCity, buttonSearchCity, labelCurrentTempText);
        //leftLayoutDesign(labelHashMap.get("labelInsertCityName"), textFieldChooseCity, buttonSearchCity, labelCurrentTempText);
        leftLayoutDesign(labelInsertCityName, textFieldChooseCity, buttonSearchCity, labelCurrentTempText);

        leftLayout.add(labelInsertCityName, textFieldChooseCity, buttonSearchCity);
        //leftLayout.add(labelHashMap.get("labelInsertCityName"), textFieldChooseCity, buttonSearchCity);
        mainLayout.add(leftLayout, middleLayout, rightLayout);

        add(mainLayout);


//        buttonSearchCity.addClickShortcut(Key.ENTER);
//        buttonSearchCity.addClickListener(buttonClickEvent -> {
//                    WeatherInformation weatherInformation = weatherService.getWeather(textFieldChooseCity.getValue(), "metric");
//                    if (!textFieldChooseCity.isEmpty() && weatherInformation != null) {
//
//                        neededWeatherInformationToDisplay.setCityName(textFieldChooseCity.getValue());
//                        leftLayout.addAndExpand(labelCurrentTempText);
//                        midLayoutDesign(middleLayout);
//                        middleLayout.addAndExpand(labelTempFeelsLike, labelTempMin, labelTempMax, labelPressure, labelHumidity);
//                        rightLayoutDesign(labelSky, rightLayout);
//                        rightLayout.addAndExpand(image, labelSky);
//
//                        labelCurrentTempText.setText(textFieldChooseCity.getValue() + " " + neededWeatherInformationToDisplay.getTemperature() + "°C");
//                        labelTempFeelsLike.setText("Temp feels like: " + neededWeatherInformationToDisplay.getTemperatureFeelsLike() + "°C");
//                        labelTempMin.setText("Temp min: " + neededWeatherInformationToDisplay.getTemperatureMin() + "°C");
//                        labelTempMax.setText("Temp max: " + neededWeatherInformationToDisplay.getTemperatureMax() + "°C");
//                        labelPressure.setText("Pressure: " + neededWeatherInformationToDisplay.getPressure() + "hPa");
//                        labelHumidity.setText("Humidity: " + neededWeatherInformationToDisplay.getHumidity() + "%");
//                        labelSky.setText(neededWeatherInformationToDisplay.getDescription());
//                        image.setSrc("http://openweathermap.org/img/wn/" + neededWeatherInformationToDisplay.getIcon() + "@2x.png");
//                        image.setAlt("Image not found");
//
//                        weatherDatabaseService.addWeatherForCity(neededWeatherInformationToDisplay);
//                    } else
//                        Notification.show("Please insert correct city name!");
//                }
//        );
        useButton(buttonSearchCity, textFieldChooseCity, leftLayout, labelCurrentTempText, middleLayout, labelTempFeelsLike, labelTempMin,
                labelTempMax, labelPressure, labelHumidity, labelSky, rightLayout, image);
    }

    private void useButton(Button buttonSearchCity, TextField textFieldChooseCity, VerticalLayout leftLayout,
                           Label labelCurrentTempText, VerticalLayout middleLayout, Label labelTempFeelsLike, Label labelTempMin,
                           Label labelTempMax, Label labelPressure, Label labelHumidity, Label labelSky, VerticalLayout rightLayout, Image image) {

            buttonSearchCity.addClickShortcut(Key.ENTER);
            buttonSearchCity.addClickListener(buttonClickEvent -> {
                    WeatherInformation weatherInformation = weatherService.getWeather(textFieldChooseCity.getValue(), "metric");
                    if (!textFieldChooseCity.isEmpty() && weatherInformation != null) {

                        WeatherInformationToDisplay.setCityName(textFieldChooseCity.getValue());
                        leftLayout.addAndExpand(labelCurrentTempText);
                        midLayoutDesign(middleLayout);
                        middleLayout.addAndExpand(labelTempFeelsLike, labelTempMin, labelTempMax, labelPressure, labelHumidity);
                        rightLayoutDesign(labelSky, rightLayout);
                        rightLayout.addAndExpand(image, labelSky);

                        labelCurrentTempText.setText(textFieldChooseCity.getValue() + " " + WeatherInformationToDisplay.getTemperature() + "°C");
                        labelTempFeelsLike.setText("Temp feels like: " + WeatherInformationToDisplay.getTemperatureFeelsLike() + "°C");
                        labelTempMin.setText("Temp min: " + WeatherInformationToDisplay.getTemperatureMin() + "°C");
                        labelTempMax.setText("Temp max: " + WeatherInformationToDisplay.getTemperatureMax() + "°C");
                        labelPressure.setText("Pressure: " + WeatherInformationToDisplay.getPressure() + "hPa");
                        labelHumidity.setText("Humidity: " + WeatherInformationToDisplay.getHumidity() + "%");
                        labelSky.setText(WeatherInformationToDisplay.getDescription());
                        image.setSrc("http://openweathermap.org/img/wn/" + WeatherInformationToDisplay.getIcon() + "@2x.png");
                        image.setAlt("Image not found");

                        weatherDatabaseService.addWeatherForCity(WeatherInformationToDisplay);
                    } else
                        Notification.show("Please insert correct city name!");
                }
        );
    }

//    private ArrayList<Label> makeLabels() {
//        ArrayList<Label> arrayList = new ArrayList();
//        Label labelInsertCityName = new Label("City name:");
//        arrayList.add(labelInsertCityName);
//        return arrayList;
//    }

//    private HashMap<String,Label> makeLabels(){
//        HashMap <String,Label> labelHashMap = new HashMap<>();
//        //Label labelInsertCityName = new Label("City name:");
////        Label labelCurrentTempText = new Label();
////        Label labelTempFeelsLike = new Label();
////        Label labelTempMin = new Label();
////        Label labelTempMax = new Label();
////        Label labelPressure = new Label();
////        Label labelHumidity = new Label();
////        Label labelSky = new Label();
//
//        labelHashMap.put("labelInsertCityName",new Label("City name:"));
//
//        return labelHashMap;
//    }

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

