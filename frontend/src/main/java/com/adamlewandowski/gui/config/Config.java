package com.adamlewandowski.gui.config;

import com.adamlewandowski.gui.language.I18NProviderImplementation;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import lombok.Getter;
import lombok.Setter;
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
    private String cityNameBeforeReload = "Warszawa";

    @Override
    public void serviceInit(ServiceInitEvent serviceInitEvent) {
        setProperty("vaadin.i18n.provider", I18NProviderImplementation.class.getName());
    }

}
