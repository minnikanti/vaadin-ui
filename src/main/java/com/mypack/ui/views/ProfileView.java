package com.mypack.ui.views;

import com.mypack.ui.layouts.MainLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "profile", layout = MainLayout.class)
@PageTitle("Profile")
public class ProfileView extends VerticalLayout {

    public ProfileView() {
        setSpacing(true);
        setPadding(true);
        setWidthFull();

        H2 title = new H2("User Profile");

        Div content = new Div();
        content.setText("This is the user profile page. Here you can display user details and settings.");
        content.getStyle()
            .set("padding", "20px")
            .set("background-color", "#f8f9fa")
            .set("border-radius", "4px");

        add(title, content);
    }
}
