package ru.tomtrix.synch.example;

import ru.tomtrix.synch.simplebetter.*;

@SuppressWarnings("unused")
public class Casher2 extends Casher1 {

    protected String isAvailable = "Cashier2Available";

    public Casher2(String name) {
        super(name);
    }

    public void requestToSmoke(Event event) {
        Boolean agree = rand(1) > 0.4;
        addEvent(new Event(event.t, "SuperMarket", "setVariable", isAvailable + "#" + !agree, _name));
        addEvent(new Event(event.t + 1, event.sender, "responseToSmoke", agree.toString(), _name));
        if (agree) addEvent(new Event(event.t + 2 + rand(7), _name, "goBack", "", _name));
    }
}
