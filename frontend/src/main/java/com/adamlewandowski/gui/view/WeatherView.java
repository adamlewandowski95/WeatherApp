package com.adamlewandowski.gui.view;

import com.adamlewandowski.dto.RequiredInformationDto;
import com.adamlewandowski.gui.config.Config;
import com.adamlewandowski.gui.language.I18NProviderImplementation;
import com.adamlewandowski.gui.model.ModelForWeatherView;
import com.adamlewandowski.gui.service.WeatherService;
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
import org.springframework.web.client.HttpServerErrorException;

@Route(value = "weather", layout = MainLayoutView.class)
@RouteAlias(value = "weather", layout = MainLayoutView.class)
@PageTitle("Weather")
public class WeatherView extends VerticalLayout {

    private WeatherService weatherService;
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
    public WeatherView(WeatherService weatherService, I18NProviderImplementation i18NProviderImplementation, Config config) {
        this.weatherService = weatherService;
        this.i18NProviderImplementation = i18NProviderImplementation;
        this.config = config;
        createView();
        chooseCityTextField.setValue(config.getCityNameBeforeReload());
        updateViewForGivenCity();
        useButton();
    }

    private void createView() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
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
        ModelForWeatherView modelForWeatherView = null;
        try {
            modelForWeatherView = weatherService.getWeather(createRequiredInformationDto());
            modelForWeatherView.setCityName(chooseCityTextField.getValue());
            currentTempTextLabel.setText(chooseCityTextField.getValue() + " " + modelForWeatherView.getTemperature() + "°C");
            tempFeelsLikeLabel.setText(i18NProviderImplementation.getTranslation("temp.feels.like.label") + modelForWeatherView.getTemperatureFeelsLike() + "°C");
            tempMaxLabel.setText(i18NProviderImplementation.getTranslation("temperature.max.label") + modelForWeatherView.getTemperatureMax() + "°C");
            tempMinLabel.setText(i18NProviderImplementation.getTranslation("temperature.min.label") + modelForWeatherView.getTemperatureMin() + "°C");
            pressureLabel.setText(i18NProviderImplementation.getTranslation("pressure.label") + modelForWeatherView.getPressure() + "hPa");
            humidityLabel.setText(i18NProviderImplementation.getTranslation("humidity.label") + modelForWeatherView.getHumidity() + "%");
            descriptionLabel.setText(i18NProviderImplementation.getDescriptionTranslation(modelForWeatherView.getDescription()));
            image.setSrc("http://openweathermap.org/img/wn/" + modelForWeatherView.getIcon() + "@2x.png");
            image.setAlt(i18NProviderImplementation.getTranslation("icon.alt.label"));
        } catch (HttpServerErrorException e) {
            Notification.show("Please insert correct city name!");
        }
    }

    private RequiredInformationDto createRequiredInformationDto() {
        RequiredInformationDto requiredInformationDto = new RequiredInformationDto();
        requiredInformationDto.setCityName(chooseCityTextField.getValue());
        requiredInformationDto.setUnit("metric");
        return requiredInformationDto;
    }

    private void leftLayoutDesign() {
        insertCityNameLabel.setText(i18NProviderImplementation.getTranslation("insert.city.label"));
        searchCityButton = new Button((i18NProviderImplementation.getTranslation("check.weather.button")), new Icon(VaadinIcon.BOLT));
        insertCityNameLabel.getStyle().set("fontWeight", "bold");
        chooseCityTextField.getStyle().set("label", "Alignment.CENTER");
        chooseCityTextField.getStyle().set("color", "black");
        chooseCityTextField.setClearButtonVisible(true);
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

