package ru.tomtrix.synch.example;

import ru.tomtrix.synch.platform.*;

@SuppressWarnings("unused")
public class Casher2 extends Casher1 {

    protected String isAvailable = "Cashier2Available";

    public Casher2(AbstractModel model, String name) {
        super(model, name);
    }

    public void requestToSmoke(Double t, String sender) {
        boolean agree = rand(1) > 0.4;
        _model.getState().variables.put(isAvailable, !agree);
        _model.getState().addEvent(new Event(t + 1, sender, agree ? "yesToSmoke" : "noToSmoke", _name));
        if (agree) _model.getState().addEvent(new Event(t + 2 + rand(7), _name, "goBack", _name));
    }
}
