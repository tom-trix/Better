package ru.tomtrix.synch.example;

import ru.tomtrix.synch.platform.*;

/**
 * Created with IntelliJ IDEA.
 * User: tom-trix
 * Date: 4/16/13
 * Time: 12:52 PM
 */
@SuppressWarnings("unused")
public class Guard extends Agent {

    public Guard(AbstractModel model, String name) {
        super(model, name);
    }

    @Override
    public void init(State state) {
        state.variables.put("Door", "closed");
        state.variables.put("GuardAvailable", true);
        state.addEvent(new Event(5d, "Guard", "openTheDoor"));
        state.addEvent(new Event(25 + _rand.nextInt(10), "Cashier2", "requestToSmoke"));
        state.addEvent(new Event(85 + _rand.nextInt(10), "Guard", "goWC"));
    }

    public void openTheDoor(Double t) {
        _model.getState().variables.put("Door", "open");
    }

    public void goBack(Double t) {
        _model.getState().variables.put("GuardAvailable", true);
    }

    public void yesToSmoke(Double t) {
        _model.getState().variables.put("GuardAvailable", false);
        _model.getState().addEvent(new Event(t + 2 + _rand.nextInt(7), "Guard", "goBack"));
        _model.getState().addEvent(new Event(t + 35 + _rand.nextInt(10), "Cashier2", "requestToSmoke"));
    }

    public void noToSmoke(Double t) {
        if (_rand.nextBoolean()) yesToSmoke(t);
        else goBack(t);
    }

    public void goWC(Double t) {
        _model.getState().variables.put("GuardAvailable", false);
        _model.getState().addEvent(new Event(t + 1, "Guard", "goBack"));
    }

    public void suspect(Double t) {

    }
}
