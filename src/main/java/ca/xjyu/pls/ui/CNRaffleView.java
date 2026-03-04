package ca.xjyu.pls.ui;

import ca.xjyu.pls.exceptions.InvalidEntryException;
import ca.xjyu.pls.model.PrairieOprah;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

/**
 * The default landing page allowing users to submit an entry into the raffle.
 * <p>
 * This view is accessible to all users without authentication. It provides
 * fields for name, email, and a passcode to validate the submission.
 *
 * @author Jeff Yu
 * @version 0.3
 */
@Route("zh")
@AnonymousAllowed
public class CNRaffleView extends VerticalLayout {

    private final PrairieOprah prairieOprah;
    private TextField nameField, passcodeField;
    private EmailField emailField;

    /**
     * Constructs the RaffleView and initialises the form components.
     *
     * @param prairieOprah The backend service used to process entries.
     */
    public CNRaffleView(PrairieOprah prairieOprah) {
        this.prairieOprah = prairieOprah;

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);

        PLFormCard enter = new PLFormCard("卑大鬥陣特攻抽籤系統");
        createTextFields();
        enter.addComponents(nameField,emailField,passcodeField);
        enter.addButtons(createButton());

        add(enter);
    }

    /**
     * Creates and configures the submission button.
     *
     * @return A {@link PLButton} configured for entry submission.
     */
    private PLButton createButton() {
        PLButton enterButton = new PLButton("輸入與存儲");
        enterButton.addClickShortcut(Key.ENTER);
        enterButton.addClickListener(e -> {
            String name = nameField.getValue();
            String email = emailField.getValue();
            String passcode = passcodeField.getValue();
            if (name == null || name.trim().isEmpty()) {
                Notification.show("請輸入貴名。");
            } else if (email == null || email.trim().isEmpty()) {
                Notification.show("請輸入電郵。");
            } else {
                try {
                    prairieOprah.addEntry(name, email, passcode);
                    Notification.show("檔案以輸入存儲，貴名： " + name);
                    passcodeField.clear();
                } catch (InvalidEntryException error) {
                    Notification.show("您的密碼無效或失效！");
                }
            }
        });
        return enterButton;
    }

    /**
     * Initialises the input fields for the user's name, email, and passcode.
     */
    private void createTextFields() {
        nameField = new TextField("貴名");
        nameField.setWidthFull();
        nameField.setClearButtonVisible(true);
        emailField = new EmailField("電郵");
        emailField.setWidthFull();
        emailField.setClearButtonVisible(true);
        passcodeField = new TextField("密碼");
        passcodeField.setWidthFull();
        passcodeField.setClearButtonVisible(true);
    }
}