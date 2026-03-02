package ca.xjyu.pls.ui;

import ca.xjyu.pls.model.Passcode;
import ca.xjyu.pls.model.PrairieOprah;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("passcodes")
public class PasscodesView extends VerticalLayout {

    private final PrairieOprah prairieOprah;
    private Grid<Passcode> currentGrid, expiredGrid;

    public PasscodesView(PrairieOprah prairieOprah) {
        this.prairieOprah = prairieOprah;
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);
        createGrids();
        add(current(),expired());
    }

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

    private void createGrids() {
        currentGrid = createGrid();
        currentGrid.setEmptyStateText("No in use passcodes.");
        expiredGrid = createGrid();
        expiredGrid.setEmptyStateText("No expired passcodes.");
        refreshCurrent();
        refreshExpired();
    }

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

    private void refreshCurrent() {
        currentGrid.setItems(prairieOprah.getCurrentPasscodes());
    }

    private void refreshExpired() {
        expiredGrid.setItems(prairieOprah.getExpiredPasscodes());
    }
}
