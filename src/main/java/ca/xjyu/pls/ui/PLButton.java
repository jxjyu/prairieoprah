package ca.xjyu.pls.ui;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;

/**
 * A customised Vaadin {@link Button} to imitate the PrairieLearn/Bootstrap styling.
 * <p>
 * This button defaults to a "Primary" blue theme with white text and automatically
 * maps the {@code Enter} key as a click shortcut upon instantiation.
 * <p>
 * An alternate sky blue colour is available for secondary functions.
 *
 * @author Jeff Yu
 * @version 0.3
 */
public class PLButton extends Button {

    /**
     * Constructs a new PLButton with the specified label and default primary styling.
     *
     * @param message The text label to display on the button.
     */
    public PLButton(String message) {
        super(message);
        this.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        this.setWidth("auto");
        this.getElement().getStyle().set("background-color", "#0D6EFD");
        this.getStyle()
                .set("color", "white")
                .set("font-size", "1.0rem")
                .set("padding", "3px 15px")
                .set("min-height", "0")
                .set("height", "50")
                .set("font-weight", "400")
                .set("cursor", "pointer");
        this.addClickShortcut(Key.ENTER);
    }

    /**
     * Updates the button's appearance to an alternative sky blue colourscheme.
     * <p>
     * This is typically used for secondary actions.
     */
    public void setAltColor() {
        this.getElement().getStyle().set("background-color", "#0DCAF0");
        this.getStyle().set("color", "black");
    }

}
