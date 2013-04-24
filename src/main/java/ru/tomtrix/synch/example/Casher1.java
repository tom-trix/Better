package ru.tomtrix.synch.example;

import java.util.*;
import ru.tomtrix.synch.simplebetter.*;

@SuppressWarnings("unused")
public class Casher1 extends Agent {

    protected String isAvailable = "Cashier1Available";

    @Override
    public Agent cloneObject() {
        Casher1 result = new Casher1(_name);
        result._events = Collections.synchronizedList(new ArrayList<>(_events));
        return result;
    }

    public Casher1(String name) {
        super(name);
        addEvent(new Event(75 + rand(10), _name, "goWC", "", _name));
    }

    public void goWC(Event event) {
        addEvent(new Event(event.t, "SuperMarket", "setVariable", isAvailable + "#false", _name));
        addEvent(new Event(event.t + rand(2), _name, "goBack", "", _name));
    }

    public void goBack(Event event) {
        addEvent(new Event(event.t, "SuperMarket", "setVariable", isAvailable + "#true", _name));
    }

    public void servePurchaser(Event event) {
        addEvent(new Event(event.t, "SuperMarket", "incVariable", event.arg.equals("cash") ? "TotalCash" : "TotalCashless", _name));
        addEvent(new Event(event.t + 0.5 + rand(2), event.sender, "accepted", "", _name));
    }
}
