package ca.xjyu.pls.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.security.AuthenticationContext;

/**
 * Main menu and layout for all pages. The top bar is hardcoded in.
 *
 * @author Jeff Yu
 * @version 0.3
 */
@Layout
@AnonymousAllowed
@CssImport("./styles/navbar-responsive.css")
public final class MainLayout extends AppLayout {

    /** The display title shown in the header. */
    private String title;
    private String loginText;

    /**
     * Initialises the main layout, including the header, navigation links, and
     * responsive components.
     *
     * @param authContext the security context (authentication)
     */
    MainLayout(AuthenticationContext authContext) {
        HorizontalLayout header = new HorizontalLayout();

        title = "UBCOW Gaming Expo";
        loginText = "Staff login";

        H1 logoText = new H1(title);
        logoText.getStyle()
                .set("font-size", "1.125rem")
                .set("margin", "0")
                .set("color", "white")
                .set("font-weight", "400")
                .set("white-space", "nowrap");
        RouterLink logoLink = new RouterLink();
        logoLink.setRoute(RaffleView.class);
        logoLink.add(logoText);
        header.add(logoLink);

        DrawerToggle toggle = new DrawerToggle();
        toggle.addClassName("mobile-toggle");
        toggle.getStyle().set("color", "white")
                .set("margin-left", "auto")
                .set("padding", "0");
        HorizontalLayout desktopNav = new HorizontalLayout();
        desktopNav.addClassName("desktop-nav");
        VerticalLayout drawerNav = new VerticalLayout();

        if (authContext.isAuthenticated()) {
            // Desktop Links (Top Navbar)
            RouterLink generatorLink = new RouterLink("Generate", GeneratorView.class);
            RouterLink winnerLink = new RouterLink("Draw", WinnerView.class);
            RouterLink entryLink = new RouterLink("Entries", EntryView.class);
            RouterLink passcodeLink = new RouterLink("Passcodes", PasscodesView.class);
            styleNav(generatorLink, winnerLink, entryLink, passcodeLink);
            desktopNav.add(generatorLink, winnerLink, entryLink, passcodeLink, getLogoutButton(authContext));
            desktopNav.getStyle().set("flex-grow", "1");

            // Mobile Links (Drawer)
            RouterLink drawerGenerator = new RouterLink("Generate", GeneratorView.class);
            RouterLink drawerWinner = new RouterLink("Draw", WinnerView.class);
            RouterLink drawerEntry = new RouterLink("Entries", EntryView.class);
            RouterLink drawerPasscode = new RouterLink("Passcodes", PasscodesView.class);
            styleDrawerNav(drawerGenerator, drawerWinner, drawerEntry, drawerPasscode);
            drawerNav.add(drawerGenerator, drawerWinner, drawerEntry, drawerPasscode, getDrawerLogoutButton(authContext));
            header.getStyle().set("max-height", "3.6rem");
            header.add(desktopNav, toggle);
        } else {
            header.add(getLoginButton());
        }

        header.setWidthFull();
        header.setPadding(true);
        header.getStyle().set("background-color", "#212529");
        header.getStyle().set("align-items", "center");

        addToNavbar(header);
        addToDrawer(drawerNav);
        setDrawerOpened(false);
    }

    /**
     * Modifies a {@code RouterLink} into the correct styling for the top navbar.
     *
     * @param links the sequence of links to be styled
     */
    private void styleNav(RouterLink... links) {
        for (RouterLink link : links) {
            link.getStyle()
                    .set("color", "#9B9D9E")
                    .set("text-decoration", "none")
                    .set("font-size", "1rem")
                    .set("font-weight", "400")
                    .set("padding", "0 10px");
        }
    }

    /**
     * Modifies a {@code RouterLink} into the correct styling for the mobile drawer.
     *
     * @param links the sequence of links to be styled
     */
    private void styleDrawerNav(RouterLink... links) {
        for (RouterLink link : links) {
            link.getStyle()
                    .set("color", "var(--lumo-body-text-color)")
                    .set("text-decoration", "none")
                    .set("font-size", "1rem")
                    .set("font-weight", "400")
                    .set("padding", "10px 0");
        }
    }

    /**
     * Creates and styles a logout button for the desktop view.
     *
     * @param authContext the security context used to trigger the logout process
     * @return a configured {@code Button} for logging out
     */
    private Button getLogoutButton(AuthenticationContext authContext) {
        Button logoutButton = new Button("Log out", e -> authContext.logout());
        logoutButton.getStyle()
                .set("margin-left", "auto")
                .set("text-decoration", "none")
                .set("font-size", "1rem")
                .set("font-weight", "400")
                .set("padding", "0 10px")
                .set("cursor", "pointer")
                .set("color", "#9B9D9E");
        logoutButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        return logoutButton;
    }

    /**
     * Creates and styles a logout button specifically formatted for the mobile
     * navigation drawer.
     *
     * @param authContext the security context used to trigger the logout process
     * @return a configured {@code Button} for logging out
     */
    private Button getDrawerLogoutButton(AuthenticationContext authContext) {
        Button logoutButton = new Button("Log out", e -> authContext.logout());
        logoutButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        logoutButton.getStyle()
                .set("color", "var(--lumo-body-text-color)")
                .set("text-decoration", "none")
                .set("font-size", "1rem")
                .set("font-weight", "400")
                .set("padding", "10px 0")
                .set("margin", "0") // Removes the auto margin
                .set("cursor", "pointer");
        return logoutButton;
    }

    /**
     * Creates a login link configured for Auth0 authentication.
     *
     * @return an {@code Anchor} pointing to the OAuth2 authorisation endpoint
     */
    private Anchor getLoginButton() {
        Anchor loginLink = new Anchor("/oauth2/authorization/auth0", loginText);
        loginLink.setRouterIgnore(true);
        loginLink.getStyle()
                .set("margin-left", "auto")
                .set("text-decoration", "none")
                .set("font-size", "1rem")
                .set("font-weight", "400")
                .set("padding", "0 10px")
                .set("color", "#9B9D9E");
        return loginLink;
    }
}
