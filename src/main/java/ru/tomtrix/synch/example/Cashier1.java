package ru.tomtrix.synch.example;

import ru.tomtrix.synch.simplebetter.*;

@SuppressWarnings("unused")
public class Cashier1 extends Agent {

    protected String isAvailable = "Cashier1Available";

    public Cashier1(String name) {
        super(name);
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
                  new Event(event.t + 0.5 + rand(2), event.author, "accepted", "", _name));
    }
}
