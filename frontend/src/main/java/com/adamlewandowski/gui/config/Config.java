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
    public String lang = "en";

    @Getter
    @Setter
    private String cityNameBeforeReload = "Warszawa";

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    @Override
    public void serviceInit(ServiceInitEvent serviceInitEvent) {
        setProperty("vaadin.i18n.provider", I18NProviderImplementation.class.getName());
    }

}
