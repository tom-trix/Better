package ru.tomtrix.synch.example;

import java.util.*;
import ru.tomtrix.synch.simplebetter.*;

@SuppressWarnings("unused")
public class Guard extends Agent {

    @Override
    public Agent cloneObject() {
        Guard result = new Guard(_name);
        result._events = Collections.synchronizedList(new ArrayList<>(_events));
        return result;
    }

    public Guard(String name) {
        super(name);
        addEvent(new Event(5d, "SuperMarket", "setVariable", "Door#open", _name));
        addEvent(new Event(25 + rand(10), "Cashier2", "requestToSmoke", "", _name));
        addEvent(new Event(65 + rand(9), _name, "goWC", "", _name));
    }

    public void responseToSmoke(Event event) {
        if (event.arg.equals("true") || rand()) {
            addEvent(new Event(event.t + rand(1), "SuperMarket", "setVariable", "GuardAvailable#false", _name));
            addEvent(new Event(event.t + 2 + rand(7), _name, "goBack", "", _name));
        }
        addEvent(new Event(event.t + 35 + rand(10), "Cashier2", "requestToSmoke", "", _name));
    }

    public void goWC(Event event) {
        addEvent(new Event(event.t + rand(1), "SuperMarket", "setVariable", "GuardAvailable#false", _name));
        addEvent(new Event(event.t + 1 + rand(3), _name, "goBack", "", _name));
    }

    public void goBack(Event event) {
        addEvent(new Event(event.t, "SuperMarket", "setVariable", "GuardAvailable#true", _name));
    }

    public void suspect(Event event) {

    }
}
