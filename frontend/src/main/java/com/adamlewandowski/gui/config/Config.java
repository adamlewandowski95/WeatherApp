package com.adamlewandowski.gui.config;

import com.adamlewandowski.gui.language.I18NProviderImplementation;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.lang.System.setProperty;

@Configuration
public class Config implements VaadinServiceInitListener {
    @Getter
    @Setter
    private String lang = "en";

    @Getter
    @Setter
    private String cityNameBeforeReload = "Warszawa";

    @Getter
    @Setter
    private int currentPage = 1;

    @Getter
    @Setter
    private int numberOfRowsToDisplay = 15;

    @Getter
    @Setter
    private String cityNameFromTextField = "";

    @Getter
    private boolean drawerVisible = false;

    public boolean changeDrawerVisibility() {
        return drawerVisible = !drawerVisible;
    }

    @Override
    public void serviceInit(ServiceInitEvent serviceInitEvent) {
        setProperty("vaadin.i18n.provider", I18NProviderImplementation.class.getName());
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }
}
