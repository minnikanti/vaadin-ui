package com.mypack.ui;

import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.page.AppShellConfigurator;

@SpringBootApplication
@StyleSheet(Lumo.STYLESHEET)
@StyleSheet(Lumo.UTILITY_STYLESHEET)
@StyleSheet("META-INF/resources/styles.css")
public class VaadinUiApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(VaadinUiApplication.class, args);
    }
}
