package ru.tomtrix.synch.example;

import ru.tomtrix.synch.platform.*;

@SuppressWarnings("unused")
public class Guard extends Agent {

    public Guard(AbstractModel  model, String name) {
        super(model, name);
        addEvent(new Event(5d, _name, "openTheDoor", _name));
        addEvent(new Event(25 + rand(10), "Cashier2", "requestToSmoke", _name));
        addEvent(new Event(85 + rand(9), _name, "goWC", _name));
    }

    public void openTheDoor(Double t, String sender) {
        getVariables().put("Door", "open");
    }

    public void goBack(Double t, String sender) {
        getVariables().put("GuardAvailable", true);
    }

    public void yesToSmoke(Double t, String sender) {
        getVariables().put("GuardAvailable", false);
        addEvent(new Event(t + 2 + rand(7), _name, "goBack", _name));
        addEvent(new Event(t + 35 + rand(10), "Cashier2", "requestToSmoke", _name));
    }

    public void noToSmoke(Double t, String sender) {
        if (rand()) yesToSmoke(t, sender);
        else goBack(t, sender);
    }

    public void goWC(Double t, String sender) {
        getVariables().put("GuardAvailable", false);
        addEvent(new Event(t + 1, _name, "goBack", _name));
    }

    public void suspect(Double t, String sender) {

    }
}
