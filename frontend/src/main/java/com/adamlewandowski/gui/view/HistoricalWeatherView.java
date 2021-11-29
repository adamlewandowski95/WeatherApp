package com.adamlewandowski.gui.view;

import com.adamlewandowski.dto.RequiredInformationDto;
import com.adamlewandowski.gui.config.Config;
import com.adamlewandowski.gui.language.I18NProviderImplementation;
import com.adamlewandowski.gui.model.ModelForDbView;
import com.adamlewandowski.gui.service.WeatherService;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Route(value = "weatherfromdb", layout = MainLayoutView.class)
@PageTitle("Weather from Db")
public class HistoricalWeatherView extends VerticalLayout {

    private Config config;
    private Label weatherFromDbLabel;
    private TextField filterText = new TextField();
    private Grid<ModelForDbView> grid = new Grid<>(ModelForDbView.class);
    private I18NProviderImplementation i18NProviderImplementation;
    private WeatherService weatherService;
    private Button downloadButton;
    private Anchor downloadAnchor;
    private Anchor downloadFromBackendAnchor;
    private Button downloadFromBackendButton;
    private List<ModelForDbView> weatherInformationFromDbList;
    private HorizontalLayout upLayout = new HorizontalLayout();
    private VerticalLayout leftLayout = new VerticalLayout();
    private HorizontalLayout midLayout = new HorizontalLayout();
    private VerticalLayout rightLayout = new VerticalLayout();
    private Button searchCityInDbButton = new Button();
    private ComboBox<Integer> numberOfElementsComboBox = new ComboBox<>();
    private ComboBox<Integer> pageComboBox = new ComboBox<>();
    private int numberOfPages;

    @Autowired
    public HistoricalWeatherView(I18NProviderImplementation i18NProviderImplementation, WeatherService weatherService, Config config) {
        this.i18NProviderImplementation = i18NProviderImplementation;
        this.weatherService = weatherService;
        this.config = config;
        createView();
    }

    private void createView() {
        leftLayoutDesign();
        midLayoutDesign();
        righLayoutDesign();
        configureTable();
        configureDownload();
        leftLayout.add(weatherFromDbLabel, filterText, searchCityInDbButton);
        midLayout.add(numberOfElementsComboBox, pageComboBox);
        rightLayout.add(downloadAnchor, downloadFromBackendAnchor);
        upLayout.setDefaultVerticalComponentAlignment(Alignment.END);
        upLayout.add(leftLayout, midLayout, rightLayout);
        add(upLayout);
        addAndExpand(grid);
        updateList();
        useButtonToSearch();
    }

    private void configurePaginator() {
        numberOfElementsComboBox.setHelperText(i18NProviderImplementation.getTranslation("size.combobox"));
        pageComboBox.setHelperText(i18NProviderImplementation.getTranslation("page.combobox"));
        numberOfElementsComboBox.setItems(5, 10, 15, 20, 50, 100);
        updateNumberOfPages();
        numberOfElementsComboBox.addValueChangeListener(event -> {
            config.setNumberOfRowsToDisplay(numberOfElementsComboBox.getValue());
            config.setCurrentPage(1);
            UI.getCurrent().getPage().reload();
        });
        pageComboBox.addValueChangeListener(event -> {
            config.setCurrentPage(pageComboBox.getValue());
            UI.getCurrent().getPage().reload();
        });
    }

    private void updateNumberOfPages() {
        numberOfElementsComboBox.setValue(config.getNumberOfRowsToDisplay());
        int numberOfElements = weatherService.getNumberOfElementsInDb(createRequiredInformationDto());
        numberOfPages = (int) Math.ceil((double) numberOfElements / config.getNumberOfRowsToDisplay());
        List<Integer> listOfPages = new ArrayList<>();
        for (int i = 1; i <= numberOfPages; i++) {
            listOfPages.add(i);
        }
        pageComboBox.setItems(listOfPages);
        pageComboBox.setValue(config.getCurrentPage());
    }

    private void updateList() {
        filterText.setValue(config.getCityNameFromTextField());
        weatherInformationFromDbList = List.of(weatherService.getHistoricalWeatherPage(createRequiredInformationDto()));
        for (ModelForDbView singleInformationFromList : weatherInformationFromDbList) {
            String description = singleInformationFromList.getDescription();
            singleInformationFromList.setDescription(i18NProviderImplementation.getDescriptionTranslation(description));
        }
        grid.setItems(weatherInformationFromDbList);
    }

    private RequiredInformationDto createRequiredInformationDto() {
        RequiredInformationDto requiredInformationDto = new RequiredInformationDto();
        requiredInformationDto.setCityName(config.getCityNameFromTextField());
        requiredInformationDto.setPage(config.getCurrentPage());
        requiredInformationDto.setNumberOfRowsToDisplay(config.getNumberOfRowsToDisplay());
        return requiredInformationDto;
    }

    private void leftLayoutDesign() {
        weatherFromDbLabel = new Label(i18NProviderImplementation.getTranslation("weather.info.label"));
        weatherFromDbLabel.getStyle().set("fontWeight", "bold");
        searchCityInDbButton = new Button((i18NProviderImplementation.getTranslation("check.weather.button")), new Icon(VaadinIcon.BOLT));
        filterText.setPlaceholder(i18NProviderImplementation.getTranslation("filter.textfiled"));
        filterText.setClearButtonVisible(true);
        filterText.getStyle().set("label", "Alignment.CENTER");
        filterText.getStyle().set("color", "black");
        searchCityInDbButton.setIconAfterText(true);
        searchCityInDbButton.getStyle().set("color", "white");
        searchCityInDbButton.getStyle().set("background", "PowderBlue");
        leftLayout.setWidth("600px");
    }

    private void midLayoutDesign() {
        configurePaginator();
        midLayout.setWidth("650px");
    }

    private void righLayoutDesign() {
        downloadButton = new Button(i18NProviderImplementation.getTranslation("download.anchor"), new Icon(VaadinIcon.DOWNLOAD_ALT));
        downloadFromBackendButton = new Button(i18NProviderImplementation.getTranslation("download.anchor") + "2", new Icon(VaadinIcon.DOWNLOAD_ALT));
        downloadButton.setIconAfterText(true);
        downloadButton.getStyle().set("color", "DodgerBlue");
        downloadButton.getStyle().set("background", "PowderBlue");
        rightLayout.setWidth("350px");
    }

    private void useButtonToSearch() {
        searchCityInDbButton.addClickShortcut(Key.ENTER);
        searchCityInDbButton.addClickListener(buttonClickEvent -> {
                    config.setCityNameFromTextField(filterText.getValue());
                    config.setCurrentPage(1);
                    UI.getCurrent().getPage().reload();
                }
        );
    }

    private void configureTable() {
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
            image.setSrc("http://openweathermap.org/img/wn/" + ((ModelForDbView) w).getIcon() + "@2x.png");
            image.setAlt(i18NProviderImplementation.getTranslation("icon.alt.label"));
            image.setHeight("25%");
            image.setWidth("25%");
            return image;
        })).setHeader(i18NProviderImplementation.getTranslation("icon.column"));
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void configureDownload() {
        StreamResource streamResource = new StreamResource(
                "weather.csv",
                () -> {
                    Stream<ModelForDbView> historicalWeather = weatherInformationFromDbList.stream();
                    StringWriter output = new StringWriter();
                    StatefulBeanToCsv<ModelForDbView> beanToCsv = new StatefulBeanToCsvBuilder<ModelForDbView>(output)
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
        downloadFromBackendAnchor = new Anchor(streamResource, "");
        downloadFromBackendAnchor.add(downloadFromBackendButton);
    }
}