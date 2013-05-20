package ru.tomtrix.synch.example;

import java.util.*;
import ru.tomtrix.synch.structures.*;
import ru.tomtrix.synch.simplebetter.*;

@SuppressWarnings("unused")
public class Guard extends Agent {

    public Guard(String name) {
        super(name);
    }

    @Override
    public void init() {
        addEvents(new TimeEvent(5, new AgentEvent(_name, "SuperMarket", "setVariable").withData("Door#open")),
                  new TimeEvent(25 + rand(10), new AgentEvent(_name, "Cashier2", "requestToSmoke")),
                  new TimeEvent(65 + rand(9), new AgentEvent(_name, _name, "goWC")));
    }

    public void responseToSmoke(TimeEvent event) {
        List<TimeEvent> events = new ArrayList<>(Arrays.asList(new TimeEvent(event.t() + 35 + rand(10), new AgentEvent(_name, "Cashier2", "requestToSmoke"))));
        if (event.event().userdata().equals("true") || rand())
            events.addAll(Arrays.asList(
                new TimeEvent(event.t() + rand(1), new AgentEvent(_name, "SuperMarket", "setVariable").withData("GuardAvailable#false")),
                new TimeEvent(event.t() + 2 + rand(7), new AgentEvent(_name, _name, "goBack"))));
        addEvents(events);
    }

    public void goWC(TimeEvent event) {
        addEvents(new TimeEvent(event.t() + rand(1), new AgentEvent(_name, "SuperMarket", "setVariable").withData("GuardAvailable#false")),
                  new TimeEvent(event.t() + 1 + rand(3), new AgentEvent(_name, _name, "goBack")));
    }

    public void goBack(TimeEvent event) {
        addEvents(new TimeEvent(event.t(), new AgentEvent(_name, "SuperMarket", "setVariable").withData("GuardAvailable#true")));
    }

    public void suspect(TimeEvent event) {

    }
}
