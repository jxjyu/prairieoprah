package com.example.ui;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;

public class PLButton extends Button {
    public PLButton(String message) {
        super(message);
        this.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        this.setWidth("auto");
        this.getElement().getStyle().set("background-color", "#0D6EFD");
        this.getStyle()
                .set("color", "white")
                .set("font-size", "1.0rem")
                .set("padding", "3px 15px")
                .set("min-height", "0")
                .set("height", "50")
                .set("font-weight", "400");
        this.addClickShortcut(Key.ENTER);
    }

    public void setAltColor() {
        this.getElement().getStyle().set("background-color", "#0DCAF0");
        this.getStyle().set("color", "black");
    }

}
