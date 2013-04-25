package ru.tomtrix.synch.example;

import java.util.*;
import ru.tomtrix.synch.simplebetter.*;

@SuppressWarnings("unused")
public class Cashier1 extends Agent {

    protected String isAvailable = "Cashier1Available";

    @Override
    public Agent cloneObject() {
        Cashier1 result = new Cashier1(_name, _modelRef);
        result._events = Collections.synchronizedList(new ArrayList<>(_events));
        return result;
    }

    public Cashier1(String name, AbstractModel model) {
        super(name, model);
        addEvents(new Event(75 + rand(10), _name, "goWC", "", _name));
    }

    public void goWC(Event event) {
        addEvents(new Event(event.t, "SuperMarket", "setVariable", isAvailable + "#false", _name),
                  new Event(event.t + rand(2), _name, "goBack", "", _name));
    }

    public void goBack(Event event) {
        addEvents(new Event(event.t, "SuperMarket", "setVariable", isAvailable + "#true", _name));
    }

    public void servePurchaser(Event event) {
        addEvents(new Event(event.t, "SuperMarket", "incVariable", event.arg.equals("cash") ? "TotalCash" : "TotalCashless", _name),
                  new Event(event.t + 0.5 + rand(2), event.sender, "accepted", "", _name));
    }
}
