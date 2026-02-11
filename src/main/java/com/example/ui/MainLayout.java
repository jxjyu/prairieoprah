package com.example.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.router.RouterLink;

@Layout
public final class MainLayout extends AppLayout {

    MainLayout() {
        HorizontalLayout header = new HorizontalLayout();

        H1 logoText = new H1("PLStake UBCEA Test");
        logoText.getStyle()
                .set("font-size", "1.125rem")
                .set("margin", "0")
                .set("color", "white")
                .set("font-weight", "400");
        RouterLink logoLink = new RouterLink();
        logoLink.setRoute(RaffleView.class);
        logoLink.add(logoText);
        header.add(logoLink);

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

    private void styleNav(RouterLink link) {
        link.getStyle()
                .set("color", "#9B9D9E")
                .set("text-decoration", "none")
                .set("font-size", "1rem")
                .set("font-weight", "400")
                .set("padding", "0 10px");
    }
}
