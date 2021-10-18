package com.adamlewandowski.gui.language;

import java.io.Serializable;

public interface I18NProvider extends Serializable {

    String getTranslation(String var1);
}
