package ca.xjyu.pls.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * A composite component that renders a titled card, imitating PrairieLearn/Bootstrap.
 * <p>
 * The card is styled with:
 * <ul>
 *      <li>A blue header bar containing the card title.
 *      <li>A central body area for form inputs or grids.
 *      <li>A grey footer bar dedicated to action buttons.
 * </ul>
 *
 * @author Jeff Yu
 * @version 0.3
 */
public class PLFormCard extends VerticalLayout {

    private HorizontalLayout top, bottom;
    private VerticalLayout formCardCentre;

    /**
     * Initialises a new form card with a specific header title.
     *
     * @param titleText     The title to be displayed in the blue header bar.
     */
    public PLFormCard(String titleText) {
        super();
        this.setWidth("100%");
        this.setMaxWidth("1100px");
        this.setPadding(false);
        this.setSpacing(false);
        this.getStyle().set("border", "1px solid #d0d0d0");
        this.getStyle().set("border-radius", "8px");
        this.getStyle().set("background-color", "white");
        this.getStyle().set("overflow", "hidden");
        this.add(addTop(titleText), addCardCentre(), addBottom());
        this.setFlexGrow(1, formCardCentre);
    }

    /**
     * Adds one or more components (such as TextFields or Grids) to the centre body of the card.
     *
     * @param components    The components to be added to the main form area.
     */
    public void addComponents(Component... components) {
        for (Component current : components) {
            formCardCentre.add(current);
        }
    }

    /**
     * Adds buttons to the card's footer area.
     *
     * @param buttons   The {@link PLButton} instances to be placed in the bottom action bar.
     */
    public void addButtons(PLButton... buttons) {
        for (PLButton current : buttons) {
            bottom.add(current);
        }
    }

    /**
     * Internal helper to construct and style the header bar.
     *
     * @param titleText     The text to display in the header.
     * @return  A styled {@link HorizontalLayout} representing the header.
     */
    private HorizontalLayout addTop(String titleText) {
        HorizontalLayout topBar = new HorizontalLayout();
        topBar.setWidthFull();
        topBar.getStyle().set("padding", "8px 15px");
        topBar.getStyle().set("background-color", "#0D6EFD");
        Span title = new Span(titleText);
        title.getStyle().set("font-size", "1rem")
                .set("margin", "0")
                .set("color", "white")
                .set("font-weight", "400");
        topBar.add(title);
        this.top = topBar;
        return topBar;
    }

    /**
     * Internal helper to initialise the central content area.
     *
     * @param components    Optional initial components.
     * @return  A {@link VerticalLayout} representing the card body.
     */
    private VerticalLayout addCardCentre(Component... components) {
        VerticalLayout textfields = new VerticalLayout();
        textfields.setWidthFull();
        textfields.setPadding(true);
        for (Component current : components) {
            textfields.add(current);
        }
        formCardCentre = textfields;
        return textfields;
    }

    /**
     * Internal helper to construct and style the footer action bar.
     *
     * @param buttons   Optional initial buttons.
     * @return  A styled {@link HorizontalLayout} representing the footer.
     */
    private HorizontalLayout addBottom(PLButton... buttons) {
        HorizontalLayout bottomBar = new HorizontalLayout();
        bottomBar.setWidthFull();
        bottomBar.getStyle().set("padding", "3px 15px");
        bottomBar.getStyle().set("background-color", "#F8F8F8");
        bottomBar.getStyle().set("border-top", "1px solid #e0e0e0");
        bottomBar.setJustifyContentMode(JustifyContentMode.START);
        for (PLButton current : buttons) {
            bottomBar.add(current);
        }
        this.bottom = bottomBar;
        return bottomBar;
    }
}
