package com.adamlewandowski.WeatherApp.view;

import com.adamlewandowski.WeatherApp.config.Config;
import com.adamlewandowski.WeatherApp.config.language.I18NProviderImplementation;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The main view is a top-level placeholder for other views.
 */
@CssImport("./styles/shared-styles.css")
public class MainLayoutView extends AppLayout implements View {
    private Config config;
    private I18NProviderImplementation i18NProviderImplementation;

    @Autowired
    public MainLayoutView(Config config, I18NProviderImplementation i18NProviderImplementation) { //, LanguageChooser languageChooser ResourceBundle resourceBundle,
        this.config = config;
        this.i18NProviderImplementation = i18NProviderImplementation;
        createView();
    }


    private void createView() {
        createHeader();
        createDrawer();
    }

    private void createDrawer() {
        RouterLink weatherForCity = new RouterLink(i18NProviderImplementation.getTranslation("weather.for.city"), WeatherView.class);
        RouterLink weatherHistorical = new RouterLink(i18NProviderImplementation.getTranslation("historical.weather"), WeatherFromDbView.class);
        weatherForCity.setHighlightCondition(HighlightConditions.sameLocation());
        weatherHistorical.setHighlightCondition(HighlightConditions.sameLocation());
        addToDrawer(new VerticalLayout(
                weatherForCity, weatherHistorical
        ));

    }

    private void createHeader() {
        ComboBox<String> valueComboBox = new ComboBox<>(i18NProviderImplementation.getTranslation("language"));
        valueComboBox.setHelperText(i18NProviderImplementation.getTranslation("choose.language.text"));

        valueComboBox.setItems(i18NProviderImplementation.getTranslation("language.eng"), i18NProviderImplementation.getTranslation("language.pl"));
        if (config.getLang().equals("en")) {
            valueComboBox.setValue(i18NProviderImplementation.getTranslation("language.eng"));
        } else if (config.getLang().equals("pl")) {
            valueComboBox.setValue(i18NProviderImplementation.getTranslation("language.pl"));
        }

        H1 logo = new H1(i18NProviderImplementation.getTranslation("welcome.message"));
        logo.addClassName("logo");
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, valueComboBox);
        header.addClassNames("header");
        header.setWidth("100%");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        addToNavbar(header);

        valueComboBox.addValueChangeListener(event -> {
            if (valueComboBox.getValue().equals(i18NProviderImplementation.getTranslation("language.eng"))) {
                config.setLang("en");
            } else if (valueComboBox.getValue().equals(i18NProviderImplementation.getTranslation("language.pl"))) {
                config.setLang("pl");
            }
            UI.getCurrent().getPage().reload();
        });
    }
}