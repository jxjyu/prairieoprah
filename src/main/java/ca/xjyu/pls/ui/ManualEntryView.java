package ca.xjyu.pls.ui;

import ca.xjyu.pls.exceptions.InvalidEntryException;
import ca.xjyu.pls.model.PrairieOprah;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

/**
 * Creates a page similar to {@link GeneratorView} which permits an exec
 * to manually add a number of entries.
 *
 * @author Jeff Yu
 * @version 0.4
 */
@Route("manual")
@PermitAll
public class ManualEntryView extends VerticalLayout {

    private final PrairieOprah prairieOprah;
    private TextField nameField, execField;
    private EmailField emailField;
    private IntegerField entriesNoField;

    /**
     * Constructs the ManualEntryView and initialises the form components.
     *
     * @param prairieOprah The backend service used to process entries.
     */
    public ManualEntryView(PrairieOprah prairieOprah) {
        this.prairieOprah = prairieOprah;

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);

        PLFormCard enter = new PLFormCard("Add Entries (Manual)");
        createTextFields();
        enter.addComponents(nameField,emailField,execField, entriesNoField);
        enter.addButtons(createButton());

        add(enter);
    }

    /**
     * Creates and configures the submission button.
     *
     * @return A {@link PLButton} configured for entry submission.
     */
    private PLButton createButton() {
        PLButton enterButton = new PLButton("Save & Enter");
        enterButton.addClickShortcut(Key.ENTER);
        enterButton.addClickListener(e -> {
            String name = nameField.getValue();
            String email = emailField.getValue();
            String exec = execField.getValue();
            int noEntries = entriesNoField.getValue();
            if (name == null || name.trim().isEmpty()) {
                Notification.show("Please enter a name.");
            } else if (email == null || email.trim().isEmpty()) {
                Notification.show("Please enter an email.");
            } else if (exec == null || exec.trim().isEmpty()) {
                Notification.show("Please enter the exec's name.");
            } else if (noEntries < 1 || noEntries > 25) {
                Notification.show("You can give 1-25 entries at a time.");
            } else {
                int attempts = 0;
                int noEntriesRemaining = noEntries;
                while (noEntriesRemaining > 0 && attempts < 30) {
                    String passcode = "manual" + Integer.toString((int)(Math.random() * 999999999 + 1));
                    attempts++;
                    try {
                        prairieOprah.addPasscode(passcode, 1, 1, exec);
                        prairieOprah.addEntry(name, email, passcode);
                        noEntriesRemaining--;
                    } catch (InvalidEntryException error) {
                        Notification.show("An error occurred with an entry!");
                    }
                }
                if (attempts > 30) {
                    Notification.show("We were not able to complete all entries! " +
                            noEntriesRemaining + " were successfully inputted!");
                } else {
                    Notification.show(noEntries + " entries saved for: " + name);
                }
            }
        });
        return enterButton;
    }

    /**
     * Initialises the input fields for the user's name, email, the exec's name, and
     * the number of entries.
     */
    private void createTextFields() {
        nameField = new TextField("Name");
        nameField.setWidthFull();
        nameField.setClearButtonVisible(true);
        emailField = new EmailField("Email");
        emailField.setWidthFull();
        emailField.setClearButtonVisible(true);
        execField = new TextField("Exec");
        execField.setWidthFull();
        execField.setClearButtonVisible(true);
        entriesNoField = new IntegerField("No. of Entries");
        entriesNoField.setValue(3);
        entriesNoField.setStepButtonsVisible(true);
        entriesNoField.setMin(-1);
        entriesNoField.setMax(25);
        entriesNoField.setMaxWidth("200px");
    }
}