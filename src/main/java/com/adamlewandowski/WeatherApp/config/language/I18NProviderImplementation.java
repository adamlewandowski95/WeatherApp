package com.adamlewandowski.WeatherApp.config.language;

import com.adamlewandowski.WeatherApp.config.Config;
import com.adamlewandowski.WeatherApp.enums.LanguageEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static java.util.ResourceBundle.getBundle;

@Component
public class I18NProviderImplementation implements I18NProvider {

    @Autowired
    Config config;

    public static final String RESOURCE_BUNDLE_NAME = "vaadinapp";

    static final ResourceBundle RESOURCE_BUNDLE_EN = getBundle(RESOURCE_BUNDLE_NAME, LanguageEnum.EN.getLanguageLocale());

    static final ResourceBundle RESOURCE_BUNDLE_PL = getBundle(RESOURCE_BUNDLE_NAME, LanguageEnum.PL.getLanguageLocale());
    private static final List providedLocales = unmodifiableList(asList(LanguageEnum.EN.getLanguageLocale(), LanguageEnum.PL.getLanguageLocale()));


    @Override
    public List<Locale> getProvidedLocales() {
        return providedLocales;
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
}
