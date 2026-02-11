package com.example.ui;

import com.example.exceptions.InvalidEntryException;
import com.example.model.PrairieOprah;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.notification.Notification;
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

        PLFormCard enter = new PLFormCard("Overwatch 2 Game Room");
        createTextFields();
        enter.addComponents(nameField,emailField,passcodeField);
        enter.addButtons(createButton());

        add(enter);
    }

    private PLButton createButton() {
        PLButton enterButton = new PLButton("Save & Enter");
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
                    passcodeField.clear();
                } catch (InvalidEntryException error) {
                    Notification.show("Your passcode is invalid or expired!");
                }
            }
        });
        return enterButton;
    }

    private void createTextFields() {
        nameField = new TextField("Name");
        nameField.setWidthFull();
        nameField.setClearButtonVisible(true);
        emailField = new EmailField("Email");
        emailField.setWidthFull();
        emailField.setClearButtonVisible(true);
        passcodeField = new TextField("Passcode");
        passcodeField.setWidthFull();
        passcodeField.setClearButtonVisible(true);
    }
}