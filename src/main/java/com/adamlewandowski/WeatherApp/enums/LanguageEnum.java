package com.adamlewandowski.WeatherApp.enums;

import java.util.Locale;


public enum LanguageEnum {


    PL("pl"),
    EN("en");

    private String languageShortForm;

    LanguageEnum(String languageShortForm) {
        this.languageShortForm = languageShortForm;
    }

    public Locale getLanguageLocale() {
        Locale locale = new Locale(languageShortForm);
        return locale;
    }
//    //Można zrobićstreamy ze zmianą formatowania daty i streamy z zmianą nawy broken clouds na polskie
}

