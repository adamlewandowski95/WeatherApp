package com.adamlewandowski.gui.view;

import com.adamlewandowski.gui.language.I18NProviderImplementation;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "login")
@PageTitle("Login")
public class LoginView extends VerticalLayout implements BeforeEnterListener {
    private I18NProviderImplementation i18NProviderImplementation;
    private final LoginForm login = new LoginForm();
    private Button registerButton = new Button();

    @Autowired
    public LoginView(I18NProviderImplementation i18NProviderImplementation) {
        this.i18NProviderImplementation = i18NProviderImplementation;
        createView();
        useButton();
    }

    private void useButton() {
        registerButton.addClickShortcut(Key.ENTER);
        registerButton.addClickListener(buttonClickEvent -> {
                }
        );
    }

    private void createView() {
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        login.setAction("login");
        login.setI18n(createI18nForLogin());
        registerButton = new Button((i18NProviderImplementation.getTranslation("register.button")));
        registerButton.setIconAfterText(true);
        add(new H1(i18NProviderImplementation.getTranslation("welcome.message")), login, registerButton);
    }

    private LoginI18n createI18nForLogin() {
        final LoginI18n i18n = LoginI18n.createDefault();
        i18n.getForm().setTitle(i18NProviderImplementation.getTranslation("login.title"));
        i18n.getForm().setUsername(i18NProviderImplementation.getTranslation("login.username"));
        i18n.getForm().setPassword(i18NProviderImplementation.getTranslation("login.password"));
        i18n.getForm().setSubmit(i18NProviderImplementation.getTranslation("login.login.button"));
        i18n.getForm().setForgotPassword(i18NProviderImplementation.getTranslation("login.forgot.button"));
        i18n.setAdditionalInformation(i18NProviderImplementation.getTranslation("login.information"));
        return i18n;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }
}
