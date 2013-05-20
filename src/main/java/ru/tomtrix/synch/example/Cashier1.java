package ru.tomtrix.synch.example;

import ru.tomtrix.synch.structures.*;
import ru.tomtrix.synch.simplebetter.*;

@SuppressWarnings("unused")
public class Cashier1 extends Agent {

    protected String isAvailable = "Cashier1Available";

    public Cashier1(String name) {
        super(name);
    }

    @Override
    public void init() {
        addEvents(new TimeEvent(75 + rand(10), new AgentEvent(_name, _name, "goWC")));
    }

    public void goWC(TimeEvent event) {
        addEvents(new TimeEvent(event.t(), new AgentEvent(_name, "SuperMarket", "setVariable").withData(isAvailable + "#false")),
                  new TimeEvent(event.t() + rand(2), new AgentEvent(_name, _name, "goBack")));
    }

    public void goBack(TimeEvent event) {
        addEvents(new TimeEvent(event.t(), new AgentEvent(_name, "SuperMarket", "setVariable").withData(isAvailable + "#true")));
    }

    public void servePurchaser(TimeEvent event) {
        addEvents(new TimeEvent(event.t(), new AgentEvent(_name, "SuperMarket", "incVariable").withData(event.event().userdata().equals("cash") ? "TotalCash" : "TotalCashless")),
                  new TimeEvent(event.t() + 0.5f + rand(2), new AgentEvent(_name, event.event().agens(), "accepted")));
    }
}
