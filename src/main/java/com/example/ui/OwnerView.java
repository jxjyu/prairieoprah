package com.example.ui;

import com.example.exceptions.InvalidPasscodeException;
import com.example.model.Entry;
import com.example.model.PrairieOprah;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("owner")
public class OwnerView extends VerticalLayout {

    private final PrairieOprah prairieOprah;
    private TextField passcodeField;
    private IntegerField timeField;
    private TextField winnerName, winnerEmail, winnerTime;

    public OwnerView(PrairieOprah prairieOprah) {
        this.prairieOprah = prairieOprah;

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);

        add(formCardNewPasscode(),formCardWinner());
    }

    private VerticalLayout formCardNewPasscode() {
        VerticalLayout formCard = formCard();
        HorizontalLayout bottom = formCardBottom();
        bottom.add(enterButton());
        formCard.add(formCardTop("Overwatch 2 Game Room: Passcode Generator"),
                formCardPasscodeTextFields(), bottom);

        return formCard;
    }

    private VerticalLayout formCardWinner() {
        VerticalLayout formCard = formCard();
        HorizontalLayout bottom = formCardBottom();
        bottom.add(winnerButton());
        formCard.add(formCardTop("Overwatch 2 Game Room: Get Winner"),
                formCardGetWinner(), bottom);
        return formCard;
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
        return formCard;
    }

    private HorizontalLayout formCardTop(String cardTitle) {
        HorizontalLayout topBar = new HorizontalLayout();
        topBar.setWidthFull();
        topBar.getStyle().set("padding", "8px 15px");
        topBar.getStyle().set("background-color", "#0D6EFD");
        Span title = new Span(cardTitle);
        title.getStyle().set("font-size", "1rem")
                .set("margin", "0")
                .set("color", "white")
                .set("font-weight", "400");
        topBar.add(title);
        return topBar;
    }

    private VerticalLayout formCardPasscodeTextFields() {
        VerticalLayout textfields = new VerticalLayout();
        textfields.setWidthFull();
        textfields.setPadding(true);

        passcodeField = new TextField("Passcode");
        passcodeField.setWidthFull();
        passcodeField.setClearButtonVisible(true);
        timeField = new IntegerField();
        timeField.setValue(3);
        timeField.setStepButtonsVisible(true);
        timeField.setMin(1);
        timeField.setMax(10);
        timeField.setMaxWidth("200px");
        textfields.add(passcodeField, timeField);
        return textfields;
    }

    private VerticalLayout formCardGetWinner() {
        VerticalLayout textfields = new VerticalLayout();
        textfields.setWidthFull();
        textfields.setPadding(true);

        winnerName = new TextField("Winner Name");
        winnerName.setWidthFull();
        winnerName.setClearButtonVisible(true);
        winnerName.setReadOnly(true);
        winnerEmail = new TextField("Winner Email");
        winnerEmail.setWidthFull();
        winnerEmail.setClearButtonVisible(true);
        winnerEmail.setReadOnly(true);
        winnerTime = new TextField("Entry Time");
        winnerTime.setWidthFull();
        winnerTime.setClearButtonVisible(true);
        winnerTime.setReadOnly(true);
        textfields.add(winnerName, winnerEmail, winnerTime);
        return textfields;
    }

    private HorizontalLayout formCardBottom() {
        HorizontalLayout bottomBar = new HorizontalLayout();
        bottomBar.setWidthFull();
        bottomBar.getStyle().set("padding", "2px 15px");
        bottomBar.getStyle().set("background-color", "#F8F8F8");
        bottomBar.getStyle().set("border-top", "1px solid #e0e0e0");
        bottomBar.setJustifyContentMode(JustifyContentMode.START);
        return bottomBar;
    }

    private Button enterButton() {
        Button enterButton = new Button("Save Passcode");
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
            String passcode = passcodeField.getValue();
            int length = timeField.getValue();
            if (passcode == null || passcode.trim().isEmpty()) {
                Notification.show("Please enter a passcode.");
            } else {
                try {
                    prairieOprah.addPasscode(passcode, length);
                    Notification.show(passcode + " has been saved as a passcode for " + length + " minutes.");
                    passcodeField.clear();
                } catch (InvalidPasscodeException error) {
                    Notification.show("Your passcode is already in use!");
                }
            }
        });
        return enterButton;
    }

    private Button winnerButton() {
        Button winnerButton = new Button("Get Winner");
        winnerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        winnerButton.setWidth("auto");
        winnerButton.getStyle()
                .set("color", "white")
                .set("font-size", "1.0rem")
                .set("padding", "3px 15px")
                .set("min-height", "0")
                .set("height", "50")
                .set("font-weight", "400");
        winnerButton.addClickShortcut(Key.ENTER);
        winnerButton.addClickListener(e -> {
            Entry winner = prairieOprah.oprah();
            if (winner != null) {
                winnerName.setValue(winner.getUser());
                winnerEmail.setValue(winner.getEmail());
                winnerTime.setValue(winner.getDateTime().toString());
            } else {
                Notification.show("No entries yet!");
            }
        });
        return winnerButton;
    }


}
