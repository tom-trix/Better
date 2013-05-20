package ru.tomtrix.synch.example;

import java.util.*;
import ru.tomtrix.synch.structures.*;
import ru.tomtrix.synch.simplebetter.*;

@SuppressWarnings("unused")
public class Cashier1 extends Agent {

    protected String isAvailable = "Cashier1Available";

    public Cashier1(String name) {
        super(name);
    }

    @Override
    public Collection<TimeEvent> init() {
        return Arrays.asList(new TimeEvent(75 + rand(10), new AgentEvent(name, name, "goWC")));
    }

    public Collection<TimeEvent> goWC(TimeEvent event) {
        return Arrays.asList(new TimeEvent(event.t(), new AgentEvent(name, "SuperMarket", "setVariable").withData(isAvailable + "#false")),
                             new TimeEvent(event.t() + rand(2), new AgentEvent(name, name, "goBack")));
    }

    public Collection<TimeEvent> goBack(TimeEvent event) {
        return Arrays.asList(new TimeEvent(event.t(), new AgentEvent(name, "SuperMarket", "setVariable").withData(isAvailable + "#true")));
    }

    public Collection<TimeEvent> servePurchaser(TimeEvent event) {
        return Arrays.asList(new TimeEvent(event.t(), new AgentEvent(name, "SuperMarket", "incVariable").withData(event.event().userdata().equals("cash") ? "TotalCash" : "TotalCashless")),
                             new TimeEvent(event.t() + 0.5f + rand(2), new AgentEvent(name, event.event().agens(), "accepted")));
    }
}
