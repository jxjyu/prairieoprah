package com.example.ui;

import com.example.model.Entry;
import com.example.model.PrairieOprah;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.time.format.DateTimeFormatter;

@Route("winner")
public class WinnerView extends VerticalLayout {

    private final PrairieOprah prairieOprah;
    private TextField winnerName, winnerEmail, winnerTime, department;

    public WinnerView(PrairieOprah prairieOprah) {
        this.prairieOprah = prairieOprah;
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);

        PLFormCard winner = new PLFormCard("Overwatch 2 Game Room: Get Winner");
        createWinner();
        winner.addComponents(winnerName, winnerEmail, winnerTime, department);
        winner.addButtons(winnerButton());
        add(winner);
    }

    private void createWinner() {
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
        department = new TextField("Department");
        department.setWidthFull();
        department.setClearButtonVisible(true);
        department.setReadOnly(true);
    }

    private PLButton winnerButton() {
        PLButton winnerButton = new PLButton("Get Winner");
        winnerButton.addClickShortcut(Key.ENTER);
        winnerButton.addClickListener(e -> {
            Entry winner = prairieOprah.oprah();
            if (winner != null) {
                winnerName.setValue(winner.getUser());
                winnerEmail.setValue(winner.getEmail());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss");
                winnerTime.setValue(winner.getDateTime().format(formatter));
                department.setValue(winner.getDepartment());
            } else {
                Notification.show("No entries yet!");
            }
        });
        return winnerButton;
    }
}
