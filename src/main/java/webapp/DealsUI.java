package webapp;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
public class DealsUI extends UI {

    private VerticalLayout root;

    @Autowired
    DealsLayout dealsLayout;

    @Override
    protected void init(VaadinRequest request) {
        setupLayout();
        addHeader();
        addDealList();
    }

    private void setupLayout() {
        root = new VerticalLayout();
        root.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setContent(root);
    }

    private void addHeader() {
        Label header = new Label("gameDealScraper v0.0.1-PRE");
        header.addStyleName(ValoTheme.LABEL_H1);
        root.addComponent(header);
    }

    private void addDealList() {
        dealsLayout.setWidth("80%");
        root.addComponent(dealsLayout);
    }

}
