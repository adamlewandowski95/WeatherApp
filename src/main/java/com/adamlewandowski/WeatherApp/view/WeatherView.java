package com.adamlewandowski.WeatherApp.view;

import com.adamlewandowski.WeatherApp.config.Config;
import com.adamlewandowski.WeatherApp.config.language.I18NProviderImplementation;
import com.adamlewandowski.WeatherApp.dao.WeatherDao;
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
public class WeatherView extends VerticalLayout implements View {

    private WeatherService weatherService;
    private WeatherDatabaseService weatherDatabaseService;
    private I18NProviderImplementation i18NProviderImplementation;
    private Config config;

    private Label insertCityNameLabel = new Label();
    private TextField chooseCityTextField = new TextField();
    private Button searchCityButton = new Button();
    private Label currentTempTextLabel = new Label();
    private Label tempFeelsLikeLabel = new Label();
    private Label tempMinLabel = new Label();
    private Label tempMaxLabel = new Label();
    private Label pressureLabel = new Label();
    private Label humidityLabel = new Label();
    private Image image = new Image();
    private Label descriptionLabel = new Label();
    private HorizontalLayout mainLayout = new HorizontalLayout();
    private VerticalLayout leftLayout = new VerticalLayout();
    private VerticalLayout middleLayout = new VerticalLayout();
    private VerticalLayout rightLayout = new VerticalLayout();


    @Autowired
    public WeatherView(WeatherService weatherService,
                       WeatherDatabaseService weatherDatabaseService, I18NProviderImplementation i18NProviderImplementation, Config config) { //WeatherDao weatherDao,

        this.weatherService = weatherService;
        this.weatherDatabaseService = weatherDatabaseService;
        this.i18NProviderImplementation = i18NProviderImplementation;
        this.config = config;
        createView();
        chooseCityTextField.setValue(config.getCityNameBeforeReload());
        updateViewForGivenCity();
        useButton();
    }

    private void createView() {

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        mainLayoutDesign();
        leftLayoutDesign();
        midLayoutDesign();
        rightLayoutDesign();


        middleLayout.addAndExpand(tempFeelsLikeLabel, tempMaxLabel, tempMinLabel, pressureLabel, humidityLabel);

        rightLayout.addAndExpand(image, descriptionLabel);

        leftLayout.add(insertCityNameLabel, chooseCityTextField, searchCityButton, currentTempTextLabel);

        mainLayout.add(leftLayout, middleLayout, rightLayout);

        add(mainLayout);

    }

    private void useButton() {
        searchCityButton.addClickShortcut(Key.ENTER);
        searchCityButton.addClickListener(buttonClickEvent -> {
                    updateViewForGivenCity();
                }
        );
    }

    private void updateViewForGivenCity() {
        config.setCityNameBeforeReload(chooseCityTextField.getValue());
        WeatherInformation weatherInformation = weatherService.getWeather(chooseCityTextField.getValue(), "metric");
        WeatherDao weatherDao = weatherService.savingDatabase(weatherInformation);
        if (!chooseCityTextField.isEmpty() && weatherDao != null) {

            weatherDao.setCityName(chooseCityTextField.getValue());

            currentTempTextLabel.setText(chooseCityTextField.getValue() + " " + weatherDao.getTemperature() + "°C");
            tempFeelsLikeLabel.setText(i18NProviderImplementation.getTranslation("temp.feels.like.label") + weatherDao.getTemperatureFeelsLike() + "°C");
            tempMaxLabel.setText(i18NProviderImplementation.getTranslation("temperature.max.label") + weatherDao.getTemperatureMax() + "°C");
            tempMinLabel.setText(i18NProviderImplementation.getTranslation("temperature.min.label") + weatherDao.getTemperatureMin() + "°C");
            pressureLabel.setText(i18NProviderImplementation.getTranslation("pressure.label") + weatherDao.getPressure() + "hPa");
            humidityLabel.setText(i18NProviderImplementation.getTranslation("humidity.label") + weatherDao.getHumidity() + "%");

            descriptionLabel.setText(i18NProviderImplementation.getDescriptionTranslation(weatherDao.getDescription()));

            image.setSrc("http://openweathermap.org/img/wn/" + weatherDao.getIcon() + "@2x.png");
            image.setAlt(i18NProviderImplementation.getTranslation("icon.alt.label"));

            weatherDatabaseService.addWeatherForCity(weatherDao);
        } else
            Notification.show("Please insert correct city name!");
    }


    private void mainLayoutDesign() {

    }

    private void leftLayoutDesign() {
        insertCityNameLabel.setText(i18NProviderImplementation.getTranslation("insert.city.label"));
        searchCityButton = new Button((i18NProviderImplementation.getTranslation("check.weather.button")), new Icon(VaadinIcon.BOLT));
        insertCityNameLabel.getStyle().set("fontWeight", "bold");
        chooseCityTextField.getStyle().set("label", "Alignment.CENTER");
        chooseCityTextField.getStyle().set("color", "black");
        searchCityButton.setIconAfterText(true);
        searchCityButton.getStyle().set("color", "white");
        currentTempTextLabel.getStyle().set("fontWeight", "bold");
        chooseCityTextField.getStyle().set("background", "LightSteelBlue");
        searchCityButton.getStyle().set("background", "PowderBlue");
        leftLayout.setWidth("300px");
    }

    private void midLayoutDesign() {
        middleLayout.setWidth("300px");
        middleLayout.getStyle().set("fontWeight", "bold");
    }

    private void rightLayoutDesign() {
        rightLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        descriptionLabel.getStyle().set("fontWeight", "bold");
        rightLayout.setWidth("300px");
    }
}

