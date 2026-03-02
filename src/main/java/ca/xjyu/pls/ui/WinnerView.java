package ca.xjyu.pls.ui;

import ca.xjyu.pls.model.Entry;
import ca.xjyu.pls.model.PrairieOprah;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

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
                winnerTime.setValue(winner.getDateTimeString());
                department.setValue(prairieOprah.mapDepartment(winner));
            } else {
                Notification.show("No entries yet!");
            }
        });
        return winnerButton;
    }
}
