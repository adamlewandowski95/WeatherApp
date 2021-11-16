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
    public String lang = "en";

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

    public boolean changeDrawerVisibility(){
        return drawerVisible = !drawerVisible;
    }

    @Override
    public void serviceInit(ServiceInitEvent serviceInitEvent) {
        setProperty("vaadin.i18n.provider", I18NProviderImplementation.class.getName());
    }

}
