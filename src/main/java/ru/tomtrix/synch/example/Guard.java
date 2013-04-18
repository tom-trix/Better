package ru.tomtrix.synch.example;

import ru.tomtrix.synch.platform.*;

@SuppressWarnings("unused")
public class Guard extends Agent {

    public Guard(AbstractModel model, String name) {
        super(model, name);
    }

    @Override
    public void init(State state) {
        state.variables.put("Door", "closed");
        state.variables.put("GuardAvailable", true);
        state.addEvent(new Event(5d, _name, "openTheDoor", _name));
        state.addEvent(new Event(25 + _rand.nextInt(10), "Cashier2", "requestToSmoke", _name));
        state.addEvent(new Event(85 + _rand.nextInt(10), _name, "goWC", _name));
    }

    public void openTheDoor(Double t, String sender) {
        _model.getState().variables.put("Door", "open");
    }

    public void goBack(Double t, String sender) {
        _model.getState().variables.put("GuardAvailable", true);
    }

    public void yesToSmoke(Double t, String sender) {
        _model.getState().variables.put("GuardAvailable", false);
        _model.getState().addEvent(new Event(t + 2 + _rand.nextInt(7), _name, "goBack", _name));
        _model.getState().addEvent(new Event(t + 35 + _rand.nextInt(10), "Cashier2", "requestToSmoke", _name));
    }

    public void noToSmoke(Double t, String sender) {
        if (_rand.nextBoolean()) yesToSmoke(t, sender);
        else goBack(t, sender);
    }

    public void goWC(Double t, String sender) {
        _model.getState().variables.put("GuardAvailable", false);
        _model.getState().addEvent(new Event(t + 1, _name, "goBack", _name));
    }

    public void suspect(Double t, String sender) {

    }
}
