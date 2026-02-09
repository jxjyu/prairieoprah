package com.example.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Layout;

@Layout
public final class MainLayout extends AppLayout {

    MainLayout() {
        HorizontalLayout header = new HorizontalLayout();

        H1 logoText = new H1("UBCstEAke");
        logoText.getStyle()
                .set("font-size", "1.125rem")
                .set("margin", "0")
                .set("color", "white")
                .set("font-weight", "400");

        header.add(logoText);

        header.setWidthFull();
        header.setPadding(true);
        header.getStyle().set("background-color", "#212529");

        addToNavbar(header);
    }
}
