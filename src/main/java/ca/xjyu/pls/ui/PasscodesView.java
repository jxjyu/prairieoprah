package ca.xjyu.pls.ui;

import ca.xjyu.pls.model.Passcode;
import ca.xjyu.pls.model.PrairieOprah;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

/**
 * Displays grids for both currently active and previously expired passcodes.
 * <p>
 * This administrative view allows staff to monitor the lifecycle of
 * issued passcodes and their respective usage counts.
 *
 * @author Jeff Yu
 * @version 0.3
 */
@Route("passcodes")
@PermitAll
public class PasscodesView extends VerticalLayout {

    private final PrairieOprah prairieOprah;
    private Grid<Passcode> currentGrid, expiredGrid;

    /**
     * Initialises the view and sets up the layout for active and expired passcode grids.
     *
     * @param prairieOprah The backend service managing passcode states.
     */
    public PasscodesView(PrairieOprah prairieOprah) {
        this.prairieOprah = prairieOprah;
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);
        createGrids();
        add(current(),expired());
    }

    /**
     * Creates the container for active passcodes.
     *
     * @return A {@link PLFormCard} containing the active passcode grid.
     */
    private PLFormCard current() {
        PLFormCard current = new PLFormCard("Overwatch 2 Game Room: View Current Passcodes");
        current.setHeight("500px");
        current.addComponents(currentGrid);
        PLButton refreshButton = new PLButton("Refresh");
        refreshButton.addClickShortcut(Key.ENTER);
        refreshButton.addClickListener(e -> {
            refreshCurrent();
            Notification.show("Data has been refreshed!");
        });
        current.addButtons(refreshButton);
        return current;
    }

    /**
     * Creates the container for expired passcodes.
     *
     * @return A {@link PLFormCard} containing the expired passcode grid.
     */
    private PLFormCard expired() {
        PLFormCard expired = new PLFormCard("Overwatch 2 Game Room: View Expired Passcodes");
        expired.setHeight("500px");
        expired.addComponents(expiredGrid);
        PLButton refreshButton = new PLButton("Refresh");
        refreshButton.addClickShortcut(Key.ENTER);
        refreshButton.addClickListener(e -> {
            refreshExpired();
            Notification.show("Data has been refreshed!");
        });
        expired.addButtons(refreshButton);
        return expired;
    }

    /**
     * Initialises the grids and populates them with initial data.
     */
    private void createGrids() {
        currentGrid = createGrid();
        currentGrid.setEmptyStateText("No in use passcodes.");
        expiredGrid = createGrid();
        expiredGrid.setEmptyStateText("No expired passcodes.");
        refreshCurrent();
        refreshExpired();
    }

    /**
     * Creates a generic passcode grid with standard columns.
     *
     * @return A pre-configured {@link Grid} for {@link Passcode} objects.
     */
    private Grid<Passcode> createGrid() {
        Grid<Passcode> grid = new Grid<>();
        grid.setHeight("300px");
        grid.addColumn(Passcode::getPassword).setHeader("Password");
        grid.addColumn(Passcode::getDepartment).setHeader("Department");
        grid.addColumn(Passcode::getCreationTimeString).setHeader("Creation");
        grid.addColumn(Passcode::getExpiryTimeString).setHeader("Expiry");
        grid.addColumn(Passcode::getUses).setHeader("Uses");
        return grid;
    }

    /**
     * Updates the current passcode grid from the service.
     */
    private void refreshCurrent() {
        currentGrid.setItems(prairieOprah.getCurrentPasscodes());
    }

    /**
     * Updates the expired passcode grid from the service.
     */
    private void refreshExpired() {
        expiredGrid.setItems(prairieOprah.getExpiredPasscodes());
    }
}
