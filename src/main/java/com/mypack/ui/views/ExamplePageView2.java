package com.mypack.ui.views;

import com.mypack.ui.layouts.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Example Page View 2
 */
@Route(value = "example2", layout = MainLayout.class)
@PageTitle("Example Page 2")
public class ExamplePageView2 extends VerticalLayout {

    public ExamplePageView2() {
        // Configure the layout
        setSpacing(true);
        setPadding(true);
        setWidthFull();
        setHeightFull();

        // Add page title
        H2 pageTitle = new H2("Example Page 2");

        // Create sample content
        Div contentDiv = new Div();
        contentDiv.setText(
            "This is Example Page 2. Another example page demonstrating the versatility of the MainLayout. " +
            "All example pages share the same header navigation, making it easy to switch between them."
        );
        contentDiv.getStyle()
            .set("padding", "20px")
            .set("background-color", "#f8f9fa")
            .set("border-radius", "4px")
            .set("line-height", "1.6");

        // Add navigation buttons
        Div navigationDiv = new Div();
        navigationDiv.setText("Navigate to other examples:");
        navigationDiv.getStyle()
            .set("margin-top", "20px")
            .set("font-weight", "bold")
            .set("font-size", "14px");

        Button exampleBtn = new Button("Go to Example");
        exampleBtn.addClickListener(e -> {
            getUI().ifPresent(ui -> ui.navigate("example"));
        });
        exampleBtn.getStyle().set("margin-right", "10px");

        Button example1Btn = new Button("Go to Example 1");
        example1Btn.addClickListener(e -> {
            getUI().ifPresent(ui -> ui.navigate("example1"));
        });

        Div buttonsDiv = new Div();
        buttonsDiv.getStyle()
            .set("margin-top", "10px")
            .set("display", "flex")
            .set("gap", "10px");
        buttonsDiv.add(exampleBtn, example1Btn);

        // Add all components
        add(pageTitle, contentDiv, navigationDiv, buttonsDiv);
    }
}
