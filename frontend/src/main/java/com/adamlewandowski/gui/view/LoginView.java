package com.adamlewandowski.gui.view;

import com.adamlewandowski.gui.language.I18NProviderImplementation;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "login")
@PageTitle("Login")
public class LoginView extends VerticalLayout implements View, BeforeEnterListener {
    private I18NProviderImplementation i18NProviderImplementation;
    private final LoginForm login = new LoginForm();
    private Button googleLoginButton = new Button();

    @Autowired
    public LoginView(I18NProviderImplementation i18NProviderImplementation) {
        this.i18NProviderImplementation = i18NProviderImplementation;
        createView();
        useButton();
    }


    private void useButton() {
        googleLoginButton.addClickShortcut(Key.ENTER);
        googleLoginButton.addClickListener(buttonClickEvent -> {
                    loginWithGoogle();
                }
        );
    }

    private void loginWithGoogle() {

    }


    private void createView() {
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        login.setAction("login");
//        login.addLoginListener(loginEvent -> {
//
//        });


        googleLoginButton = new Button((i18NProviderImplementation.getTranslation("alt.login.button")), new Icon(VaadinIcon.GOOGLE_PLUS));
        googleLoginButton.setIconAfterText(true);
        add(new H1("Weather App"), login, googleLoginButton);
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
