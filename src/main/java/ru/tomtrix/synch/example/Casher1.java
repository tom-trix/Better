package ru.tomtrix.synch.example;

import ru.tomtrix.synch.platform.AbstractModel;
import ru.tomtrix.synch.platform.Agent;
import ru.tomtrix.synch.platform.Event;
import ru.tomtrix.synch.platform.State;

@SuppressWarnings("unused")
public class Casher1 extends Agent {

    protected String isAvailable = "Cashier1Available";

    public Casher1(AbstractModel model, String name) {
        super(model, name);
    }

    @Override
    public void init(State state) {
        state.variables.put(isAvailable, true);
        state.variables.put("TotalCash", 0);
        state.variables.put("TotalCashless", 0);
        state.addEvent(new Event(75 + rand(10), _name, "goWC", _name));
    }

    public void goWC(Double t, String sender) {
        _model.getState().variables.put(isAvailable, false);
        _model.getState().addEvent(new Event(t + 1, _name, "goBack", _name));
    }

    public void goBack(Double t, String sender) {
        _model.getState().variables.put(isAvailable, true);
    }

    public void servePurchaser(Double t, String sender) {
        _model.getState().addEvent(new Event(t + rand(6), sender, "cashOrCashless", _name));
    }

    public void cash(Double t, String sender) {
        _model.getState().variables.put("TotalCash", (int)_model.getState().variables.get("TotalCash") + 1);
        _model.getState().addEvent(new Event(t + 0.5 + rand(2), sender, "accepted", _name));
    }

    public void cashless(Double t, String sender) {
        _model.getState().variables.put("TotalCashless", (int)_model.getState().variables.get("TotalCashless") + 1);
        _model.getState().addEvent(new Event(t + 0.7 + rand(3), sender, "accepted", _name));
    }
}
