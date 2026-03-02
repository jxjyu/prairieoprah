package ca.xjyu.pls.ui;

import ca.xjyu.pls.model.Entry;
import ca.xjyu.pls.model.PrairieOprah;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("entries")
public class EntryView extends VerticalLayout {

    private final PrairieOprah prairieOprah;
    private Grid<Entry> entryGrid;

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

    private PLButton refreshButton() {
        PLButton refreshButton = new PLButton("Refresh");
        refreshButton.addClickShortcut(Key.ENTER);
        refreshButton.addClickListener(e -> {
            setData();
            Notification.show("Data has been refreshed!");
        });
        return refreshButton;
    }

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

    private void setData() {
        entryGrid.setItems(prairieOprah.getEntries());
    }
}
