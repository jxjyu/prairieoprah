package ca.xjyu.pls.ui;

import ca.xjyu.pls.model.Entry;
import ca.xjyu.pls.model.PrairieOprah;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

/**
 * An administrative view designed to randomly select and display a winner.
 * <p>
 * This view requires authentication and displays the winner's contact
 * details, submission time, and associated department.
 *
 * @author Jeff Yu
 * @version 0.3
 */
@Route("winner")
@PermitAll
public class WinnerView extends VerticalLayout {
    private final PrairieOprah prairieOprah;
    private TextField winnerName, winnerEmail, winnerTime, department;

    /**
     * Constructs the WinnerView and sets up the read-only display fields.
     *
     * @param prairieOprah The backend service used to select a random entry.
     */
    public WinnerView(PrairieOprah prairieOprah) {
        this.prairieOprah = prairieOprah;
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);

        PLFormCard winner = new PLFormCard("Get Winner");
        createWinner();
        winner.addComponents(winnerName, winnerEmail, winnerTime, department);
        winner.addButtons(winnerButton());
        add(winner);
    }

    /**
     * Initialises the read-only fields used to display winner information.
     */
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
        department = new TextField("Exec");
        department.setWidthFull();
        department.setClearButtonVisible(true);
        department.setReadOnly(true);
    }

    /**
     * Creates the button that triggers the random selection logic.
     *
     * @return A {@link PLButton} configured to fetch and display a winner.
     */
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
