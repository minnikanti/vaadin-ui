package com.mypack.ui.views;

import com.mypack.ui.dto.DashboardDto;
import com.mypack.ui.dto.SubItemDto;
import com.mypack.ui.service.DashboardService;
import com.mypack.ui.layouts.MainLayout;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Dashboard")
public class DashboardView extends VerticalLayout {

    private HorizontalLayout cardsContainer;
    private final DashboardService dashboardService;

    public DashboardView(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
        renderDashboard();
        
        // Create routes section (initially hidden)
        VerticalLayout routesSection = new VerticalLayout();
        routesSection.setVisible(false);
        routesSection.setWidth("400px");
        routesSection.setPadding(true);
        routesSection.addClassName("routes-section");
        
        this.add(cardsContainer, routesSection);
        this.setWidthFull();
        this.setPadding(true);

        // Register this dashboard view with the MainLayout
        getUI().ifPresent(ui -> {
            MainLayout layout = (MainLayout) ui.getChildren()
                .filter(component -> component instanceof MainLayout)
                .findFirst()
                .orElse(null);
            if (layout != null) {
                layout.setDashboardView(this);
            }
        });
    }

    /**
     * Renders the dashboard by loading tiles from JSON configuration
     */
    private void renderDashboard() {
        List<DashboardDto> dashboardItems = dashboardService.getDashboardItems();
        
        // Create a horizontal layout to display cards
        cardsContainer = new HorizontalLayout();
        cardsContainer.setWidthFull();
        cardsContainer.setPadding(true);
        cardsContainer.setSpacing(true);
        cardsContainer.addClassName("cards-container");
        cardsContainer.getStyle().set("flex-wrap", "wrap");
        
        // Generate cards dynamically from dashboard items
        for (DashboardDto item : dashboardItems) {
            Div cardWrapper = createDashboardCardWrapper(item);
            cardsContainer.add(cardWrapper);
        }
    }

    /**
     * Creates a dashboard card with the given item data
     */
    private Card createDashboardCard(DashboardDto item) {
        Card card = new Card();
        card.setWidth("300px");
        card.setHeight("150px");
        card.addClassName("dashboard-card");
        
        // Create icon component from string
        Icon icon = createIconFromString(item.getIcon());
        icon.setSize("2rem");
        
        // Create description div with scrollable content
        Div descriptionDiv = new Div();
        descriptionDiv.setText(item.getDescription());
        descriptionDiv.setWidth("100%");
        descriptionDiv.setHeight("calc(100% - 80px)");
        descriptionDiv.addClassName("card-description");
        
        // Create title div
        Div titleDiv = new Div();
        titleDiv.setText(item.getTitle());
        titleDiv.addClassName("card-title");
        titleDiv.setWidth("100%");
        
        // Create horizontal layout for icon and content
        HorizontalLayout contentLayout = new HorizontalLayout();
        contentLayout.setAlignItems(FlexComponent.Alignment.START);
        contentLayout.setSpacing(true);
        contentLayout.setWidth("100%");
        contentLayout.setHeight("100%");
        contentLayout.setPadding(true);
        
        // Create vertical layout for title and description
        VerticalLayout textLayout = new VerticalLayout();
        textLayout.setSpacing(false);
        textLayout.setPadding(false);
        textLayout.setWidth("calc(100% - 50px)");
        textLayout.setHeight("100%");
        textLayout.add(titleDiv, descriptionDiv);
        
        // Add icon and text layout to content layout
        contentLayout.add(icon, textLayout);
        
        // Add content layout to card
        card.add(contentLayout);
        
        return card;
    }

    /**
     * Creates a VaadinIcon from a string representation
     * @param iconName The name of the icon (e.g., "CHECK", "HOME", "LAPTOP")
     * @return A VaadinIcon, or VaadinIcon.QUESTION if the icon name is not found
     */
    private Icon createIconFromString(String iconName) {
        try {
            VaadinIcon vaadinIcon = VaadinIcon.valueOf(iconName);
            return new Icon(vaadinIcon);
        } catch (IllegalArgumentException e) {
            System.err.println("Icon not found: " + iconName + ", using default icon");
            return new Icon(VaadinIcon.QUESTION);
        }
    }

    /**
     * Creates and returns a wrapper div containing the card for click handling
     */
    public Div createDashboardCardWrapper(DashboardDto item) {
        Card card = createDashboardCard(item);
        Div cardWrapper = new Div(card);
        cardWrapper.getStyle().set("cursor", "pointer");
        cardWrapper.addClickListener(_ -> {
            // If the item has subItems, display them in a dialog as tiles
            if (item.getSubItems() != null && !item.getSubItems().isEmpty()) {
                showSubItemsDialog(item);
            } else {
                // Otherwise, navigate to the route
                String route = item.getRoute();
                if (route != null && !route.isEmpty()) {
                    getUI().ifPresent(ui -> ui.navigate(route));
                }
            }
        });
        return cardWrapper;
    }
    
    /**
     * Shows a dialog box with sub-items displayed as tiles
     */
    private void showSubItemsDialog(DashboardDto parentItem) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle(parentItem.getTitle() + " - Sub Items");
        dialog.setWidth("800px");
        dialog.setHeight("600px");
        
        // Create a container for sub-item tiles
        HorizontalLayout tilesContainer = new HorizontalLayout();
        tilesContainer.setWidthFull();
        tilesContainer.setHeight("100%");
        tilesContainer.setPadding(true);
        tilesContainer.setSpacing(true);
        tilesContainer.getStyle().set("flex-wrap", "wrap");
        tilesContainer.getStyle().set("align-content", "flex-start");
        
        // Create tiles for each sub-item
        for (SubItemDto subItem : parentItem.getSubItems()) {
            Div subItemTile = createSubItemTile(subItem, dialog, parentItem);
            tilesContainer.add(subItemTile);
        }
        
        // Create a close button
        Button closeButton = new Button("Close", event -> dialog.close());
        
        // Create main layout for the dialog
        VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.setWidthFull();
        dialogLayout.setHeightFull();
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        
        // Add scroll wrapper for tiles
        Div scrollWrapper = new Div(tilesContainer);
        scrollWrapper.setWidthFull();
        scrollWrapper.setHeight("100%");
        scrollWrapper.getStyle().set("overflow-y", "auto");
        scrollWrapper.getStyle().set("overflow-x", "hidden");
        
        dialogLayout.add(scrollWrapper);
        dialogLayout.setFlexGrow(1, scrollWrapper);
        
        dialog.add(dialogLayout);
        dialog.getFooter().add(closeButton);
        
        dialog.open();
    }

    /**
     * Creates a tile for a sub-item
     */
    private Div createSubItemTile(SubItemDto subItem, Dialog dialog, DashboardDto parentItem) {
        Card tileCard = new Card();
        tileCard.setWidth("250px");
        tileCard.setHeight("120px");
        tileCard.addClassName("sub-item-tile");
        
        // Create title div
        Div titleDiv = new Div();
        titleDiv.setText(subItem.getTitle());
        titleDiv.addClassName("sub-item-title");
        titleDiv.setWidth("100%");
        titleDiv.getStyle().set("font-size", "1.1rem");
        titleDiv.getStyle().set("font-weight", "500");
        
        // Create content layout
        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setSpacing(false);
        contentLayout.setPadding(true);
        contentLayout.setWidth("100%");
        contentLayout.setHeight("100%");
        contentLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        contentLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        contentLayout.add(titleDiv);
        
        tileCard.add(contentLayout);
        
        // Create wrapper with click handling
        Div tileWrapper = new Div(tileCard);
        tileWrapper.getStyle().set("cursor", "pointer");
        tileWrapper.addClickListener(_ -> {
            // Update navigation bar with subitems
            updateNavigationBarWithSubItems(parentItem);
            
            // Navigate to the sub-item route
            String route = subItem.getRoute();
            if (route != null && !route.isEmpty()) {
                dialog.close();
                getUI().ifPresent(ui -> ui.navigate(route));
            }
        });
        
        return tileWrapper;
    }
    
    /**
     * Updates the navigation bar with subItems from a DashboardDto item
     */
    private void updateNavigationBarWithSubItems(DashboardDto item) {
        getUI().ifPresent(ui -> {
            MainLayout layout = (MainLayout) ui.getChildren()
                .filter(component -> component instanceof MainLayout)
                .findFirst()
                .orElse(null);
            if (layout != null) {
                java.util.Map<String, String> links = new java.util.LinkedHashMap<>();
                for (com.mypack.ui.dto.SubItemDto subItem : item.getSubItems()) {
                    links.put(subItem.getTitle(), subItem.getRoute());
                }
                layout.updateNavigationLinks(links);
            }
        });
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        // Clean up navigation links when returning to the dashboard
        getUI().ifPresent(ui -> {
            MainLayout layout = (MainLayout) ui.getChildren()
                .filter(component -> component instanceof MainLayout)
                .findFirst()
                .orElse(null);
            if (layout != null) {
                layout.hideNavigationLinks();
            }
        });
    }
}
