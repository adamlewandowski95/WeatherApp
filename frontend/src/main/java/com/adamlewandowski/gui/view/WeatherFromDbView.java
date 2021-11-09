package com.adamlewandowski.gui.view;

import com.adamlewandowski.gui.language.I18NProviderImplementation;
import com.adamlewandowski.gui.model.WeatherForDbView;
import com.adamlewandowski.gui.service.WeatherFromBackend;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


@Route(value = "weatherfromdb", layout = MainLayoutView.class)
@PageTitle("Weather from Db")
public class WeatherFromDbView extends VerticalLayout implements View {

    private Label weatherFromDbLabel;
    private TextField filterText = new TextField();
    private Grid<WeatherForDbView> grid = new Grid<>(WeatherForDbView.class);
    private I18NProviderImplementation i18NProviderImplementation;
    private WeatherFromBackend weatherFromBackend;
    private Button downloadButton;
    private Anchor downloadAnchor;
    private List<WeatherForDbView> weatherInformationFromDbList;


    @Autowired
    public WeatherFromDbView(I18NProviderImplementation i18NProviderImplementation, WeatherFromBackend weatherFromBackend) {
        this.i18NProviderImplementation = i18NProviderImplementation;
        this.weatherFromBackend = weatherFromBackend;
        createView();
    }

    private void createView() {
        configureFilter();
        configureButton();
        configureTable();
        download();
        add(weatherFromDbLabel, filterText, downloadAnchor);
        addAndExpand(grid);
        updateList();
    }


    private void download() {

        StreamResource streamResource = new StreamResource(
                "weather.csv",
                () -> {
                    Map<String, String> mapping = new
                            HashMap<String, String>();
                    mapping.put("ID", "id");
                    mapping.put("CITYNAME", "cityname");
                    mapping.put("TEMPERATURE", "temperature");
                    mapping.put("TEMPERATUREFEELSLIKE", "temperaturefeelslike");
                    mapping.put("TEMPERATUREMIN", "temperaturemin");
                    mapping.put("TEMPERATUREMAX", "temperaturemax");
                    mapping.put("PRESSURE", "pressure");
                    mapping.put("HUMIDITY", "humidity");
                    mapping.put("DESCRIPTION", "description");
                    mapping.put("DATEANDTIME", "dateandtime");
                    mapping.put("ICON", "icon");

                    ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();
                    mappingStrategy.setType(WeatherForDbView.class);


                    Stream<WeatherForDbView> historicalWeather = weatherInformationFromDbList.stream();
                    StringWriter output = new StringWriter();
                    StatefulBeanToCsv<WeatherForDbView> beanToCsv = new StatefulBeanToCsvBuilder<WeatherForDbView>(output)
                            .withSeparator(';')
                            .withApplyQuotesToAll(false)
                            .build();


                    try {
                        beanToCsv.write(historicalWeather);

                        String contents = output.toString();
                        return new ByteArrayInputStream(contents.getBytes());
                    } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
                        e.printStackTrace();
                    }
                    return null;

                });
        downloadAnchor = new Anchor(streamResource, "");
        downloadAnchor.add(downloadButton);
    }

    private void configureButton() {
        downloadButton = new Button(i18NProviderImplementation.getTranslation("download.anchor"), new Icon(VaadinIcon.DOWNLOAD_ALT));
        downloadButton.setIconAfterText(true);
        downloadButton.getStyle().set("color", "DodgerBlue");
        downloadButton.getStyle().set("background", "PowderBlue");
    }

    private void updateList() {
        weatherInformationFromDbList = List.of(weatherFromBackend.getHistoricalWeather(filterText.getValue()));
        for (WeatherForDbView singleInformationFromList : weatherInformationFromDbList) {
            String description = singleInformationFromList.getDescription();
            singleInformationFromList.setDescription(i18NProviderImplementation.getDescriptionTranslation(description));
        }
        grid.setItems(weatherInformationFromDbList);
    }

    private void configureFilter() {
        weatherFromDbLabel = new Label(i18NProviderImplementation.getTranslation("weather.info.label"));
        filterText.setPlaceholder(i18NProviderImplementation.getTranslation("filter.textfiled"));
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
    }


    private void configureTable() {
        grid.setSizeFull();
        grid.setColumns("id", "cityName", "dateAndTime", "temperature", "temperatureFeelsLike", "temperatureMax", "temperatureMin", "pressure", "humidity", "description");
        grid.getColumnByKey("id").setHeader(i18NProviderImplementation.getTranslation("id.column"));
        grid.getColumnByKey("cityName").setHeader(i18NProviderImplementation.getTranslation("city.name.column"));
        grid.getColumnByKey("dateAndTime").setHeader(i18NProviderImplementation.getTranslation("date.and.time.column"));
        grid.getColumnByKey("temperature").setHeader(i18NProviderImplementation.getTranslation("temperature.column"));
        grid.getColumnByKey("temperatureFeelsLike").setHeader(i18NProviderImplementation.getTranslation("temperature.feels.like.column"));
        grid.getColumnByKey("temperatureMax").setHeader(i18NProviderImplementation.getTranslation("temperature.max.column"));
        grid.getColumnByKey("temperatureMin").setHeader(i18NProviderImplementation.getTranslation("temperature.min.column"));
        grid.getColumnByKey("pressure").setHeader(i18NProviderImplementation.getTranslation("pressure.column"));
        grid.getColumnByKey("humidity").setHeader(i18NProviderImplementation.getTranslation("humidity.column"));
        grid.getColumnByKey("description").setHeader(i18NProviderImplementation.getTranslation("description.column"));
        grid.addColumn(new ComponentRenderer<>(w -> {
            Image image = new Image();
            image.setSrc("http://openweathermap.org/img/wn/" + ((WeatherForDbView) w).getIcon() + "@2x.png");
            image.setAlt(i18NProviderImplementation.getTranslation("icon.alt.label"));
            image.setHeight("30%");
            image.setWidth("30%");
            return image;
        })).setHeader(i18NProviderImplementation.getTranslation("icon.column"));
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }
}