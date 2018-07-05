package webapp;

import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import domain.Deal;

public class DealItemLayout extends VerticalLayout {

    private Label title;
    private Image image;

    public DealItemLayout(Deal deal) {
        title.setValue("Game name");
        title.addStyleName(ValoTheme.LABEL_BOLD);
        image = new Image();

        addComponents(title, image);
    }
}
