package com.adamlewandowski.WeatherApp.view;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

/**
 * The main view is a top-level placeholder for other views.
 */
@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {
    public MainLayout() {
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

    private void createHeader() {
        H1 logo = new H1("Weather APP");
        logo.addClassName("logo");
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);
        header.addClassNames("header");
        header.setWidth("100%");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        addToNavbar(header);
    }

}