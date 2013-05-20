package ru.tomtrix.synch.example;

import java.util.*;
import ru.tomtrix.synch.structures.*;

@SuppressWarnings("unused")
public class Cashier2 extends Cashier1 {

    protected String isAvailable = "Cashier2Available";

    public Cashier2(String name) {
        super(name);
    }

    public void requestToSmoke(TimeEvent event) {
        Boolean agree = rand(1) > 0.4;
        List<TimeEvent> events = new ArrayList<>(Arrays.asList(
            new TimeEvent(event.t(), new AgentEvent(_name, "SuperMarket", "setVariable").withData(isAvailable + "#" + !agree)),
            new TimeEvent(event.t() + 1, new AgentEvent(_name, event.event().agens(), "responseToSmoke").withData(agree.toString()))));
        if (agree) events.add(new TimeEvent(event.t() + 2 + rand(7), new AgentEvent(_name, _name, "goBack")));
        addEvents(events);
    }
}
