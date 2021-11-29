package com.adamlewandowski.gui.language;

import com.adamlewandowski.gui.config.Config;
import com.adamlewandowski.gui.enums.LanguageEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.ResourceBundle;

import static java.util.ResourceBundle.getBundle;

@Component
public class I18NProviderImplementation implements I18NProvider {

    private Config config;
    private static final String RESOURCE_BUNDLE_NAME = "vaadinapp";
    private static final ResourceBundle RESOURCE_BUNDLE_EN = getBundle(RESOURCE_BUNDLE_NAME, LanguageEnum.EN.getLanguageLocale());
    private final ResourceBundle RESOURCE_BUNDLE_PL = getBundle(RESOURCE_BUNDLE_NAME, LanguageEnum.PL.getLanguageLocale());

    @Autowired
    public I18NProviderImplementation(Config config){
        this.config = config;
    }

    @Override
    public String getTranslation(String key) {
        Locale locale = new Locale(config.getLang());
        ResourceBundle resourceBundle = RESOURCE_BUNDLE_EN;
        if (LanguageEnum.PL.getLanguageLocale().equals(locale)) {
            resourceBundle = RESOURCE_BUNDLE_PL;
        }
        if (!resourceBundle.containsKey(key)) {
            return key + " - " + locale;
        } else {
            return (resourceBundle.containsKey(key)) ? resourceBundle.getString(key) : key;
        }
    }

    public String getDescriptionTranslation(String description) {
        if (description.equals("Clear")) {
            description = getTranslation("description.clear.label");
        } else if (description.equals("Clouds")) {
            description = getTranslation("description.few.label");
        } else if (description.equals("scattered clouds")) {
            description = getTranslation("description.scattered.label");
        } else if (description.equals("broken clouds")) {
            description = getTranslation("description.broken.label");
        } else if (description.equals("Drizzle")) {
            description = getTranslation("description.shower.label");
        } else if (description.equals("Rain")) {
            description = getTranslation("description.rain.label");
        } else if (description.equals("Thunderstorm")) {
            description = getTranslation("description.thunderstorm.label");
        } else if (description.equals("Snow")) {
            description = getTranslation("description.snow.label");
        } else if (description.equals("Mist")) {
            description = getTranslation("description.mist.label");
        } else if (description.equals("Tornado")) {
            description = getTranslation("description.tornado.label");
        } else {
            description = getTranslation("description.another.label");
        }
        return description;
    }
}