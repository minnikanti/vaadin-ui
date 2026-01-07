package com.mypack.ui.layouts;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.mypack.ui.views.DashboardView;

public class MainLayout extends AppLayout {

    private HorizontalLayout navLinksContainer;

    public MainLayout() {
        createHeader();
    }

    private void createHeader() {
        // Create header layout
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.setHeight("60px");
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setMargin(false);
        header.setPadding(true);
        header.addClassName("app-header");

        // Logo section
        HorizontalLayout logoContainer = createLogoSection();

        // Dashboard Icon
        Button dashboardBtn = new Button(new Icon(VaadinIcon.DASHBOARD));
        dashboardBtn.addClassName("header-icon-btn");
        dashboardBtn.getStyle().set("cursor", "pointer");
        dashboardBtn.addClickListener(_ -> getUI().ifPresent(ui -> ui.navigate("")));

        // Navigation links container (initially hidden)
        navLinksContainer = new HorizontalLayout();
        navLinksContainer.setVisible(false);
        navLinksContainer.setSpacing(true);
        navLinksContainer.setAlignItems(FlexComponent.Alignment.CENTER);
        navLinksContainer.addClassName("nav-links-container");

        // Left section: Logo, Dashboard, Nav Links
        HorizontalLayout leftSection = new HorizontalLayout();
        leftSection.setAlignItems(FlexComponent.Alignment.CENTER);
        leftSection.setSpacing(true);
        leftSection.setPadding(false);
        leftSection.getStyle().set("flex-shrink", "0");
        leftSection.add(logoContainer, dashboardBtn, navLinksContainer);

        // Spacer - takes all available space between left and right sections
        Div spacer = new Div();
        spacer.getStyle().set("flex", "1");

        // Right section: Search bar and User profile
        HorizontalLayout rightSection = new HorizontalLayout();
        rightSection.setAlignItems(FlexComponent.Alignment.CENTER);
        rightSection.setSpacing(true);
        rightSection.setPadding(false);
        rightSection.getStyle().set("flex-shrink", "0");

        // Search bar
        TextField searchField = new TextField();
        searchField.setPlaceholder("Search Dashboard...");
        searchField.setWidth("300px");
        searchField.addClassName("search-field");

        // User section with dropdown menu
        HorizontalLayout userSection = createUserSection();

        rightSection.add(searchField, userSection);

        // Add all components to header
        header.add(leftSection, spacer, rightSection);

        // Wrap header in a Div for navbar compatibility
        Div navbarContent = new Div(header);
        addToNavbar(navbarContent);
    }

    private HorizontalLayout createLogoSection() {
        HorizontalLayout logoContainer = new HorizontalLayout();
        logoContainer.addClassName("logo-container");
        logoContainer.setAlignItems(FlexComponent.Alignment.CENTER);
        logoContainer.setSpacing(true);

        // App Logo (using a funny emoji/placeholder image)
        Image logo = new Image("https://media.giphy.com/media/xT9IgEx8SbQ0teblWM/giphy.gif", "App Logo");
        logo.setWidth("40px");
        logo.setHeight("40px");
        logo.getStyle().set("border-radius", "50%");

        // App title
        H3 appTitle = new H3("MyApp");
        appTitle.addClassName("app-title");
        appTitle.setHeight("150px");
        appTitle.getStyle().set("display", "flex");
        appTitle.getStyle().set("align-items", "center");

        logoContainer.add(logo, appTitle);
        return logoContainer;
    }

    private HorizontalLayout createUserSection() {
        HorizontalLayout userSection = new HorizontalLayout();
        userSection.setAlignItems(FlexComponent.Alignment.CENTER);
        userSection.setSpacing(true);
        userSection.setPadding(false);

        // User icon button
        Button userBtn = new Button(new Icon(VaadinIcon.USER));
        userBtn.addClassName("header-icon-btn");
        userBtn.getStyle().set("cursor", "pointer");

        // Create context menu for user options
        ContextMenu contextMenu = new ContextMenu(userBtn);
        contextMenu.setOpenOnClick(true);

        contextMenu.addItem("View Profile", _ -> {
            // Handle profile view
            getUI().ifPresent(ui -> ui.navigate("profile"));
        });

        contextMenu.addItem("Settings", _ -> {
            // Handle settings
            getUI().ifPresent(ui -> ui.navigate("settings"));
        });

        contextMenu.addItem("Logout", _ -> {
            // Handle logout
            getUI().ifPresent(ui -> ui.navigate("login"));
        });

        userSection.add(userBtn);
        return userSection;
    }

    /**
     * Updates the navigation links displayed next to dashboardBtn
     * @param links Map of link text to link href
     */
    public void updateNavigationLinks(java.util.Map<String, String> links) {
        navLinksContainer.removeAll();
        
        for (java.util.Map.Entry<String, String> entry : links.entrySet()) {
            Anchor link = new Anchor(entry.getValue(), entry.getKey());
            link.addClassName("nav-link");
            
            navLinksContainer.add(link);
        }
        
        navLinksContainer.setVisible(true);
    }

    /**
     * Hides the navigation links
     */
    public void hideNavigationLinks() {
        navLinksContainer.setVisible(false);
    }

    /**
     * Sets the dashboard view reference
     * @param dashboardView The dashboard view instance
     */
    public void setDashboardView(DashboardView dashboardView) {
        // Store reference if needed for future use
    }
}
