package ca.xjyu.pls.ui;

import ca.xjyu.pls.exceptions.InvalidPasscodeException;
import ca.xjyu.pls.model.PrairieOprah;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

/**
 * Administrative interface for generating and configuring passcodes.
 * <p>
 * Supports the creation of standard multi-use passcodes with custom expiry
 * durations and the generation of randomised single-use passcodes.
 *
 * @author Jeff Yu
 * @version 0.3
 */
@Route("generate")
@PermitAll
public class GeneratorView extends VerticalLayout {

    private final PrairieOprah prairieOprah;
    private TextField passcodeField, departmentField;
    private IntegerField timeField, maxUses;

    /**
     * Constructs the GeneratorView and organises the input fields.
     *
     * @param prairieOprah The backend service for passcode registration.
     */
    public GeneratorView(PrairieOprah prairieOprah) {
        this.prairieOprah = prairieOprah;

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);

        PLFormCard normalPasscode = new PLFormCard("Overwatch 2 Game Room: Passcode Generator");
        createNormalFields();
        normalPasscode.addComponents(passcodeField, timeField, departmentField, maxUses);
        normalPasscode.addButtons(createNormalButton(), createSingleUseButton());

        add(normalPasscode);
    }

    /**
     * Initialises the input fields for passcode properties.
     */
    private void createNormalFields() {
        passcodeField = new TextField("Passcode");
        passcodeField.setWidthFull();
        passcodeField.setClearButtonVisible(true);
        timeField = new IntegerField("Length (minutes)");
        timeField.setValue(3);
        timeField.setStepButtonsVisible(true);
        timeField.setMin(1);
        timeField.setMax(10);
        timeField.setMaxWidth("200px");
        maxUses = new IntegerField("Max Uses");
        maxUses.setValue(25);
        maxUses.setStepButtonsVisible(true);
        maxUses.setMin(-1);
        maxUses.setMax(25);
        maxUses.setMaxWidth("200px");
        departmentField = new TextField("Department");
        departmentField.setWidthFull();
        departmentField.setClearButtonVisible(true);
    }

    /**
     * Creates a button to save a custom passcode.
     * <p>
     * Also includes JavaScript to copy the saved passcode to the system clipboard.
     *
     * @return A {@link PLButton} for standard passcode creation.
     */
    private PLButton createNormalButton() {
        PLButton enterButton = new PLButton("Save Passcode");
        enterButton.addClickShortcut(Key.ENTER);
        enterButton.addClickListener(e -> {
            String passcode = passcodeField.getValue();
            int length = timeField.getValue();
            int uses = maxUses.getValue();
            String department = departmentField.getValue();
            if (passcode == null || passcode.trim().isEmpty()) {
                Notification.show("Please enter a passcode.");
            } else if (department == null || department.trim().isEmpty()) {
                Notification.show("Please enter your department.");
            } else {
                try {
                    prairieOprah.addPasscode(passcode, length, uses, department);
                    Notification.show(passcode + " has been saved as a passcode for " + length + " minutes.");
                    UI.getCurrent().getPage().executeJs(
                            "navigator.clipboard.writeText($0).then(function() {" +
                                    "  console.log('Copying to clipboard was successful!');" +
                                    "}).catch(function(err) {" +
                                    "  console.error('Could not copy text: ', err);" +
                                    "});",
                            passcode);
                } catch (InvalidPasscodeException error) {
                    Notification.show(error.getMessage());
                }
            }
        });
        return enterButton;
    }

    /**
     * Creates a button to generate a random single-use passcode.
     *
     * @return A {@link PLButton} for one-time passcode generation.
     */
    private PLButton createSingleUseButton() {
        PLButton enterButton = new PLButton("Single-use Passcode");
        enterButton.setAltColor();
        enterButton.addClickListener(e -> {
            String passcode = Integer.toString((int)(Math.random() * 999999999 + 1));
            String department = departmentField.getValue();
            if (department == null || department.trim().isEmpty()) {
                Notification.show("Please enter your department.");
            } else {
                try {
                    prairieOprah.addPasscode(passcode, 3, 1, department);
                    passcodeField.setValue(passcode);
                    Notification.show("Your single-use passcode is: " + passcode);
                    UI.getCurrent().getPage().executeJs(
                            "navigator.clipboard.writeText($0).then(function() {" +
                                    "  console.log('Copying to clipboard was successful!');" +
                                    "}).catch(function(err) {" +
                                    "  console.error('Could not copy text: ', err);" +
                                    "});",
                            passcode);
                } catch (InvalidPasscodeException error) {
                    Notification.show("Try again!");
                }
            }
        });
        return enterButton;
    }

}
