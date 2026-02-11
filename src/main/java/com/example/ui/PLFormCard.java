package com.example.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class PLFormCard extends VerticalLayout {

    private HorizontalLayout top, bottom;
    private VerticalLayout formCardCentre;

    public PLFormCard(String titleText) {
        super();
        this.setWidth("100%");
        this.setMaxWidth("1100px");
        this.setPadding(false);
        this.setSpacing(false);
        this.getStyle().set("border", "1px solid #d0d0d0");
        this.getStyle().set("border-radius", "8px");
        this.getStyle().set("background-color", "white");
        this.getStyle().set("overflow", "hidden");
        this.add(addTop(titleText), addCardCentre(), addBottom());
    }

    public void addComponents(Component... components) {
        for (Component current : components) {
            formCardCentre.add(current);
        }
    }

    public void addButtons(PLButton... buttons) {
        for (PLButton current : buttons) {
            bottom.add(current);
        }
    }

    private HorizontalLayout addTop(String titleText) {
        HorizontalLayout topBar = new HorizontalLayout();
        topBar.setWidthFull();
        topBar.getStyle().set("padding", "8px 15px");
        topBar.getStyle().set("background-color", "#0D6EFD");
        Span title = new Span(titleText);
        title.getStyle().set("font-size", "1rem")
                .set("margin", "0")
                .set("color", "white")
                .set("font-weight", "400");
        topBar.add(title);
        this.top = topBar;
        return topBar;
    }

    private VerticalLayout addCardCentre(Component... components) {
        VerticalLayout textfields = new VerticalLayout();
        textfields.setWidthFull();
        textfields.setPadding(true);
        for (Component current : components) {
            textfields.add(current);
        }
        formCardCentre = textfields;
        return textfields;
    }

    private HorizontalLayout addBottom(PLButton... buttons) {
        HorizontalLayout bottomBar = new HorizontalLayout();
        bottomBar.setWidthFull();
        bottomBar.getStyle().set("padding", "3px 15px");
        bottomBar.getStyle().set("background-color", "#F8F8F8");
        bottomBar.getStyle().set("border-top", "1px solid #e0e0e0");
        bottomBar.setJustifyContentMode(JustifyContentMode.START);
        for (PLButton current : buttons) {
            bottomBar.add(current);
        }
        this.bottom = bottomBar;
        return bottomBar;
    }
}
