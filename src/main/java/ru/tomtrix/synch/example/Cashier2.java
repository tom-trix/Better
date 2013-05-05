package ru.tomtrix.synch.example;

import java.util.*;
import ru.tomtrix.synch.simplebetter.*;

@SuppressWarnings("unused")
public class Cashier2 extends Cashier1 {

    protected String isAvailable = "Cashier2Available";

    public Cashier2(String name) {
        super(name);
    }

    public void requestToSmoke(Event event) {
        Boolean agree = rand(1) > 0.4;
        List<Event> events = new ArrayList<>(Arrays.asList(
                new Event(event.t, "SuperMarket", "setVariable", isAvailable + "#" + !agree, _name),
                new Event(event.t + 1, event.author, "responseToSmoke", agree.toString(), _name)));
        if (agree) events.add(new Event(event.t + 2 + rand(7), _name, "goBack", "", _name));
        addEvents(events);
    }
}
