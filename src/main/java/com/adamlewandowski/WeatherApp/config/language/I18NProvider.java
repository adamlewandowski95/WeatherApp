package com.adamlewandowski.WeatherApp.config.language;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

public interface I18NProvider extends Serializable {
    List<Locale> getProvidedLocales();

    String getTranslation(String var1);
}
