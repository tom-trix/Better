package ru.tomtrix.synch.example;

import java.util.*;
import ru.tomtrix.synch.structures.*;
import ru.tomtrix.synch.simplebetter.*;

@SuppressWarnings("unused")
public class Purchaser extends Agent {

    private double _tasteForTheft = rand(1)/2;

    public Purchaser(String name) {
        super(name);
    }

    @Override
    public void init() {
        addEvents(new TimeEvent(5 + rand(30), new AgentEvent(_name, _name, "appear")));
    }

    public void appear(TimeEvent event) {
        _tasteForTheft = rand(1)/2;
        addEvents(new TimeEvent(event.t() + rand(1), new AgentEvent(_name, "SuperMarket", "incVariable").withData("TotalPurchasers")),
                  new TimeEvent(event.t() + rand(1), new AgentEvent(_name, "SuperMarket", "incVariable").withData("Purchasers")),
                  new TimeEvent(event.t() + 1 + rand(5), new AgentEvent(_name, _name, "bringGoods")));
    }

    public void bringGoods(TimeEvent event) {
        List<TimeEvent> events = new ArrayList<>(Arrays.asList(new TimeEvent(event.t() + 1 + rand(5), new AgentEvent(_name, _name, rand(1)>0.8 ? "goToCashdesk" : "bringGoods"))));
        if (rand(1)*_tasteForTheft > 0.35)
            events.addAll(Arrays.asList(
                    new TimeEvent(event.t() + rand(1), new AgentEvent(_name, "SuperMarket", "incVariable").withData("Thefts")),
                    new TimeEvent(event.t() +1, new AgentEvent(_name, "Guard", "suspect"))));
        addEvents(events);
    }

    public void goToCashdesk(TimeEvent event) {
        String cashier = String.format("Cashier%d", rand() ? 1 : 2);
        addEvents(new TimeEvent(event.t() + rand(1), new AgentEvent(_name, cashier, "servePurchaser").withData("cash" + (rand() ? "less" : ""))));
    }

    public void accepted(TimeEvent event) {
        addEvents(new TimeEvent(event.t() + rand(1), new AgentEvent(_name, "SuperMarket", "decVariable").withData("Purchasers")),
                  new TimeEvent(event.t() + 5 + rand(30), new AgentEvent(_name, _name, "appear")));
    }
}
