package webapp;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.VerticalLayout;
import domain.Deal;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@SpringComponent
public class DealsLayout extends VerticalLayout {

    @PostConstruct
    void init() {
//        List<Deal> d = ;
//        setDeals(d);
    }

    private void setDeals(List<Deal> deals) {
        removeAllComponents();

        deals.forEach(deal-> addComponent(new DealItemLayout(deal)));
    }

}
