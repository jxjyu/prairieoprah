package ca.xjyu.pls.ui;

import ca.xjyu.pls.model.Entry;
import ca.xjyu.pls.model.PrairieOprah;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

/**
 * Provides a tabular overview of all successful raffle entries.
 * <p>
 * This view is restricted to authenticated users and displays detailed information
 * for each entry, including user identity, timing, and department mapping.
 *
 * @author Jeff Yu
 * @version 0.3
 */
@Route("entries")
@PermitAll
public class EntryView extends VerticalLayout {

    private final PrairieOprah prairieOprah;
    private Grid<Entry> entryGrid;

    /**
     * Initialises the entry view and populates the data grid.
     *
     * @param prairieOprah The backend service providing entry data.
     */
    public EntryView(PrairieOprah prairieOprah) {
        this.prairieOprah = prairieOprah;
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);
        createGrid();
        PLFormCard winner = new PLFormCard("Overwatch 2 Game Room: View Data");
        winner.addComponents(entryGrid);
        winner.addButtons(refreshButton());
        add(winner);
    }

    /**
     * Creates a refresh button to update the grid data manually.
     *
     * @return A {@link PLButton} that triggers a data refresh.
     */
    private PLButton refreshButton() {
        PLButton refreshButton = new PLButton("Refresh");
        refreshButton.addClickShortcut(Key.ENTER);
        refreshButton.addClickListener(e -> {
            setData();
            Notification.show("Data has been refreshed!");
        });
        return refreshButton;
    }

    /**
     * Configures the grid columns and column headers.
     */
    private void createGrid() {
        entryGrid = new Grid<>();
        entryGrid.addColumn(Entry::getUser).setHeader("User");
        entryGrid.addColumn(Entry::getEmail).setHeader("Email");
        entryGrid.addColumn(Entry::getDateTimeString).setHeader("Time");
        entryGrid.addColumn(Entry::getPassword).setHeader("Password");
        entryGrid.addColumn(e -> prairieOprah.mapDepartment(e)).setHeader("Department");
        entryGrid.setEmptyStateText("No entries found.");
        setData();
    }

    /**
     * Fetches the latest entries from the service and updates the grid items.
     */
    private void setData() {
        entryGrid.setItems(prairieOprah.getEntries());
    }
}
