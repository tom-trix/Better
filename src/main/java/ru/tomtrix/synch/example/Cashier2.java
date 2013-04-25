package ru.tomtrix.synch.example;

import java.util.*;
import ru.tomtrix.synch.simplebetter.*;

@SuppressWarnings("unused")
public class Cashier2 extends Cashier1 {

    protected String isAvailable = "Cashier2Available";

    @Override
    public Agent cloneObject() {
        Cashier2 result = new Cashier2(_name, _modelRef);
        result._events = Collections.synchronizedList(new ArrayList<>(_events));
        return result;
    }

    public Cashier2(String name, AbstractModel model) {
        super(name, model);
    }

    public void requestToSmoke(Event event) {
        Boolean agree = rand(1) > 0.4;
        List<Event> events = new ArrayList<>(Arrays.asList(
                new Event(event.t, "SuperMarket", "setVariable", isAvailable + "#" + !agree, _name),
                new Event(event.t + 1, event.sender, "responseToSmoke", agree.toString(), _name)));
        if (agree) events.add(new Event(event.t + 2 + rand(7), _name, "goBack", "", _name));
        addEvents(events);
    }
}
