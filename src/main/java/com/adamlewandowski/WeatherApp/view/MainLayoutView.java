package com.adamlewandowski.WeatherApp.view;

import com.adamlewandowski.WeatherApp.component.LanguageChooser;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;

import java.util.Locale;
import java.util.ResourceBundle;

import static java.util.Locale.ENGLISH;

/**
 * The main view is a top-level placeholder for other views.
 */
@CssImport("./styles/shared-styles.css")
public class MainLayoutView extends AppLayout implements View {
    private ResourceBundle resourceBundle;
    private LanguageChooser languageChooser;

    @Autowired
    public MainLayoutView(ResourceBundle resourceBundle, LanguageChooser languageChooser) {
        this.resourceBundle = resourceBundle;
        this.languageChooser = languageChooser;
        createView();
    }


    private void createView(){
        createHeader();
        createDrawer();
    }

    private void createDrawer() {
        RouterLink weatherForCity = new RouterLink("Weather for City", WeatherView.class);
        RouterLink weatherHistorical = new RouterLink("Historical weather", WeatherFromDbView.class);
        weatherForCity.setHighlightCondition(HighlightConditions.sameLocation());
        weatherHistorical.setHighlightCondition(HighlightConditions.sameLocation());
        addToDrawer(new VerticalLayout(
                weatherForCity, weatherHistorical
        ));

    }


    //welcome.message = Welcome to Application
    private void createHeader() {
        ComboBox<String> valueComboBox = new ComboBox<>("Language");
        valueComboBox.setHelperText("Select the language you are most familiar with");

        valueComboBox.setItems("English", "Polish");
        valueComboBox.setValue("English");
        Locale locale = new Locale(languageChooser.getLang());
        resourceBundle = ResourceBundle.getBundle("messages",locale);
        H1 logo = new H1(resourceBundle.getString("welcome.message"));

        valueComboBox.addValueChangeListener(event -> {

            if(valueComboBox.getValue().equals("English")){
                languageChooser.setLang("");
//                valueComboBox.setItems("English", "Polish");

            } else if(valueComboBox.getValue().equals("Polish")) {
                languageChooser.setLang("pl");
//                valueComboBox.setItems("Angielski", "Polski");
            }
            updateHeader(locale, resourceBundle, logo, valueComboBox);
        });


        logo.addClassName("logo");
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, valueComboBox);
        header.addClassNames("header");
        header.setWidth("100%");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        addToNavbar(header);
    }

    private void updateHeader(Locale locale, ResourceBundle resourceBundle, H1 logo, ComboBox valueComboBox){
        locale = new Locale(languageChooser.getLang());
        resourceBundle = ResourceBundle.getBundle("messages",locale);
        logo.setText(resourceBundle.getString("welcome.message"));

        valueComboBox.setLabel(resourceBundle.getString("language"));
        valueComboBox.setHelperText(resourceBundle.getString("choose.language.text"));
    }
}