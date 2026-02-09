package com.example.ui;

import com.example.exceptions.InvalidEntryException;
import com.example.model.PrairieOprah;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("")
public class RaffleView extends VerticalLayout {

    private final PrairieOprah prairieOprah;
    private TextField nameField, passcodeField;
    private EmailField emailField;

    public RaffleView(PrairieOprah prairieOprah) {
        this.prairieOprah = prairieOprah;

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);

        add(formCard());
    }

    private VerticalLayout formCard() {
        VerticalLayout formCard = new VerticalLayout();
        formCard.setWidth("1090px");
        formCard.setMaxWidth("1100px");
        formCard.setPadding(false);
        formCard.setSpacing(false);
        formCard.getStyle().set("border", "1px solid #d0d0d0");
        formCard.getStyle().set("border-radius", "8px");
        formCard.getStyle().set("background-color", "white");
        formCard.getStyle().set("overflow", "hidden");

        formCard.add(formCardTop(), formCardTextFields(), formCardBottom());
        return formCard;
    }

    private HorizontalLayout formCardTop() {
        HorizontalLayout topBar = new HorizontalLayout();
        topBar.setWidthFull();
        topBar.getStyle().set("padding", "8px 15px");
        topBar.getStyle().set("background-color", "#0D6EFD");
        Span title = new Span("Overwatch 2 Game Room");
        title.getStyle().set("font-size", "1rem")
                .set("margin", "0")
                .set("color", "white")
                .set("font-weight", "400");
        topBar.add(title);
        return topBar;
    }

    private VerticalLayout formCardTextFields() {
        VerticalLayout textfields = new VerticalLayout();
        textfields.setWidthFull();
        textfields.setPadding(true);

        nameField = new TextField("Name");
        nameField.setWidthFull();
        nameField.setClearButtonVisible(true);
        emailField = new EmailField("Email");
        emailField.setWidthFull();
        emailField.setClearButtonVisible(true);
        passcodeField = new TextField("Passcode");
        passcodeField.setWidthFull();
        passcodeField.setClearButtonVisible(true);

        textfields.add(nameField, emailField, passcodeField);
        return textfields;
    }

    private HorizontalLayout formCardBottom() {
        HorizontalLayout bottomBar = new HorizontalLayout();
        bottomBar.setWidthFull();
        bottomBar.getStyle().set("padding", "2px 15px");
        bottomBar.getStyle().set("background-color", "#F8F8F8");
        bottomBar.getStyle().set("border-top", "1px solid #e0e0e0");
        bottomBar.setJustifyContentMode(JustifyContentMode.START);
        Button enterButton = new Button("Save & Enter");
        enterButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        enterButton.setWidth("auto");
        enterButton.getStyle()
                .set("color", "white")
                .set("font-size", "1.0rem")
                .set("padding", "3px 15px")
                .set("min-height", "0")
                .set("height", "50")
                .set("font-weight", "400");
        enterButton.addClickShortcut(Key.ENTER);
        enterButton.addClickListener(e -> {
            String name = nameField.getValue();
            String email = emailField.getValue();
            String passcode = passcodeField.getValue();
            if (name == null || name.trim().isEmpty()) {
                Notification.show("Please enter a name.");
            } else if (email == null || email.trim().isEmpty()) {
                Notification.show("Please enter an email.");
            } else {
                try {
                    prairieOprah.addEntry(name, email, passcode);
                    Notification.show("Entry saved for: " + name);
                    nameField.clear();
                } catch (InvalidEntryException error) {
                    Notification.show("Your passcode is invalid or expired!");
                }
            }
        });
        bottomBar.add(enterButton);
        return bottomBar;
    }
}