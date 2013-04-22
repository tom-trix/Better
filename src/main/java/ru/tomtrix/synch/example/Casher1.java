package ru.tomtrix.synch.example;

import java.util.*;
import ru.tomtrix.synch.platform.*;


@SuppressWarnings("unused")
public class Casher1 extends Agent {

    protected String isAvailable = "Cashier1Available";

    @Override
    public Agent cloneObject() {
        Casher1 result = new Casher1(_model, _name);
        result._events = Collections.synchronizedList(new ArrayList<>(_events));
        return result;
    }

    public Casher1(AbstractModel model, String name) {
        super(model, name);
        addEvent(new Event(75 + rand(10), _name, "goWC", _name));
    }

    public void goWC(Double t, String sender) {
        getVariables().put(isAvailable, false);
        addEvent(new Event(t + 1, _name, "goBack", _name));
    }

    public void goBack(Double t, String sender) {
        getVariables().put(isAvailable, true);
    }

    public void servePurchaser(Double t, String sender) {
        addEvent(new Event(t + rand(6), sender, "cashOrCashless", _name));
    }

    public void cash(Double t, String sender) {
        getVariables().put("TotalCash", (int)getVariables().get("TotalCash") + 1);
        addEvent(new Event(t + 0.5 + rand(2), sender, "accepted", _name));
    }

    public void cashless(Double t, String sender) {
        getVariables().put("TotalCashless", (int)getVariables().get("TotalCashless") + 1);
        addEvent(new Event(t + 0.7 + rand(3), sender, "accepted", _name));
    }
}
