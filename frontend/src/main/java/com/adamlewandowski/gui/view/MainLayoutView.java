package com.adamlewandowski.gui.view;

import com.adamlewandowski.gui.config.Config;
import com.adamlewandowski.gui.language.I18NProviderImplementation;
import com.adamlewandowski.gui.security.SecurityService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;

@CssImport("./styles/shared-styles.css")
public class MainLayoutView extends AppLayout implements View {
    private Config config;
    private I18NProviderImplementation i18NProviderImplementation;
    private final SecurityService securityService;

    @Autowired
    public MainLayoutView(Config config, I18NProviderImplementation i18NProviderImplementation, SecurityService securityService) {
        this.securityService = securityService;
        this.config = config;
        this.i18NProviderImplementation = i18NProviderImplementation;
        createView();
    }

    private void createView() {
        createHeader();
        createDrawer();
    }

    private void createDrawer() {
        RouterLink checkWeatherForCityRouterLink = new RouterLink(i18NProviderImplementation.getTranslation("weather.for.city"), WeatherView.class);
        RouterLink browseHistoricalWeatherRouterLink = new RouterLink(i18NProviderImplementation.getTranslation("historical.weather"), HistoricalWeatherView.class);
        checkWeatherForCityRouterLink.setHighlightCondition(HighlightConditions.sameLocation());
        browseHistoricalWeatherRouterLink.setHighlightCondition(HighlightConditions.sameLocation());
        addToDrawer(new VerticalLayout(checkWeatherForCityRouterLink, browseHistoricalWeatherRouterLink));
        setDrawerOpened(config.isDrawerVisible());

    }

    private void createHeader() {
//        ComboBox<String> languageChooserComboBox = new ComboBox<>(i18NProviderImplementation.getTranslation("language"));
        ComboBox<String> languageChooserComboBox = new ComboBox<>();
        Button logOut = new Button(i18NProviderImplementation.getTranslation("log.out"),e -> securityService.logout());
        //languageChooserComboBox.setHelperText(i18NProviderImplementation.getTranslation("choose.language.text"));

        languageChooserComboBox.setItems(i18NProviderImplementation.getTranslation("language.eng"), i18NProviderImplementation.getTranslation("language.pl"));
        if (config.getLang().equals("en")) {
            languageChooserComboBox.setValue(i18NProviderImplementation.getTranslation("language.eng"));
        } else if (config.getLang().equals("pl")) {
            languageChooserComboBox.setValue(i18NProviderImplementation.getTranslation("language.pl"));
        }

        H1 logo = new H1(i18NProviderImplementation.getTranslation("welcome.message"));
        logo.addClassName("logo");
        DrawerToggle drawerToggle = new DrawerToggle();
        HorizontalLayout header = new HorizontalLayout(drawerToggle, logo, languageChooserComboBox, logOut);
        header.setSizeFull();
        header.expand(logo);
        header.addClassNames("header");
        header.setWidth("100%");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        addToNavbar(header);

        languageChooserComboBox.addValueChangeListener(event -> {
            if (languageChooserComboBox.getValue().equals(i18NProviderImplementation.getTranslation("language.eng"))) {
                config.setLang("en");
            } else if (languageChooserComboBox.getValue().equals(i18NProviderImplementation.getTranslation("language.pl"))) {
                config.setLang("pl");
            }
            UI.getCurrent().getPage().reload();
        });

        drawerToggle.addClickListener(buttonClickEvent -> config.changeDrawerVisibility());
    }
}