package ca.xjyu.pls.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.router.RouterLink;

/**
 * Main menu and layout for all pages. The top bar is hardcoded in.
 *
 * @author Jeff Yu
 * @version 0.1
 */
@Layout
public final class MainLayout extends AppLayout {

    private final String TITLE = "UBCEA Test";

    /**
     * Creates the main layout of the website.
     */
    MainLayout() {
        HorizontalLayout header = new HorizontalLayout();

        H1 logoText = new H1(TITLE);
        logoText.getStyle()
                .set("font-size", "1.125rem")
                .set("margin", "0")
                .set("color", "white")
                .set("font-weight", "400");
        RouterLink logoLink = new RouterLink();
        logoLink.setRoute(RaffleView.class);
        logoLink.add(logoText);
        header.add(logoLink);

        /*
        EDIT HERE FOR TOP NAV BAR LINKS:
         */
        RouterLink generatorLink = new RouterLink("Generate Passcode", GeneratorView.class);
        RouterLink winnerLink = new RouterLink("Get Winner", WinnerView.class);
        styleNav(generatorLink);
        styleNav(winnerLink);

        header.setWidthFull();
        header.setPadding(true);
        header.getStyle().set("background-color", "#212529");
        header.add(generatorLink, winnerLink);

        addToNavbar(header);
    }

    /**
     * Modifies a {@code RouterLink} into the correct styling for the top navbar.
     *
     * @param link  the link to be modified
     */
    private void styleNav(RouterLink link) {
        link.getStyle()
                .set("color", "#9B9D9E")
                .set("text-decoration", "none")
                .set("font-size", "1rem")
                .set("font-weight", "400")
                .set("padding", "0 10px");
    }
}
