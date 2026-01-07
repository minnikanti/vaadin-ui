package com.mypack.ui.views;

import com.mypack.ui.layouts.MainLayout;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.HashMap;
import java.util.Map;

/**
 * EXAMPLE VIEW - How to create a new page with the header layout
 * 
 * This demonstrates how to create a new view that uses the MainLayout.
 * All pages using this pattern will automatically show the header with:
 * - App Logo + Title
 * - Dashboard navigation icon
 * - Search bar
 * - User menu dropdown
 * 
 * To create your own page:
 * 1. Copy this file and rename it (e.g., DocumentsView.java)
 * 2. Update the class name
 * 3. Update the @Route value with your page path
 * 4. Update the @PageTitle value
 * 5. Add your content in the constructor
 */
@Route(value = "example", layout = MainLayout.class)
@PageTitle("Example Page")
public class ExamplePageView extends VerticalLayout {

    public ExamplePageView() {
        // Configure the layout
        setSpacing(true);
        setPadding(true);
        setWidthFull();
        setHeightFull();

        // Add page title
        H2 pageTitle = new H2("Example Page");

        // Create sample content
        Div contentDiv = new Div();
        contentDiv.setText(
            "This is an example page that demonstrates how to use the MainLayout. " +
            "Notice the header at the top with the logo, dashboard icon, search bar, and user menu. " +
            "Every page that uses layout = MainLayout.class in the @Route annotation will have this header."
        );
        contentDiv.getStyle()
            .set("padding", "20px")
            .set("background-color", "#f8f9fa")
            .set("border-radius", "4px")
            .set("line-height", "1.6");

        // Add interactive content
        Div instructionsDiv = new Div();
        instructionsDiv.setText("To create a new page with this header:");
        instructionsDiv.getStyle()
            .set("margin-top", "20px")
            .set("font-weight", "bold")
            .set("font-size", "14px");

        // Add step-by-step instructions
        Div stepsDiv = new Div();
        stepsDiv.add(new com.vaadin.flow.component.html.UnorderedList(
            new com.vaadin.flow.component.html.ListItem("Create a new Java class extending VerticalLayout (or any container)"),
            new com.vaadin.flow.component.html.ListItem("Add @Route(value = \"your-path\", layout = MainLayout.class)"),
            new com.vaadin.flow.component.html.ListItem("Add @PageTitle(\"Your Page Title\")"),
            new com.vaadin.flow.component.html.ListItem("Add your content in the constructor")
        ));
        stepsDiv.getStyle()
            .set("padding", "15px")
            .set("background-color", "#ffffff")
            .set("border-left", "4px solid #3b82f6")
            .set("border-radius", "4px");

        // Add a button example
        Button exampleBtn = new Button("Example Button");
        exampleBtn.addClickListener(e -> {
            System.out.println("Button clicked!");
        });
        exampleBtn.getStyle()
            .set("margin-top", "20px");

        // Add all components
        add(pageTitle, contentDiv, instructionsDiv, stepsDiv, exampleBtn);
    }

    /**
     * Registers this view with the layout and updates navigation links
     * Called after the view is attached to the UI
     */
    private void registerAndUpdateNavigation() {
        getUI().ifPresent(ui -> {
            // Get the MainLayout instance
            MainLayout layout = (MainLayout) ui.getChildren()
                .filter(component -> component instanceof MainLayout)
                .findFirst()
                .orElse(null);

            if (layout != null) {
                // Create navigation links for example routes
                Map<String, String> navLinks = new HashMap<>();
                navLinks.put("Example 1", "example1");
                navLinks.put("Example 2", "example2");
                
                // Update the layout with the navigation links
                layout.updateNavigationLinks(navLinks);
            }
        });
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        // Update navigation links when the view is attached to the UI
        registerAndUpdateNavigation();
    }
}


