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

    public Guard(AbstractModel model) {
        super(model);
    }

    @Override
    public void init(State state) {
        state.variables.put("Door", "closed");
        state.variables.put("GuardAvailable", true);
        state.addEvent(new Event(5d, "Guard", "openTheDoor"));
        state.addEvent(new Event(25 + _rand.nextInt(10), "Cashier2", "requestToSmoke"));
    }

    public void openTheDoor(Double t) {
        _model.getState().variables.put("Door", "open");
    }

    public void yesToSmoke(Double t) {
        _model.getState().variables.put("GuardAvailable", false);
        _model.getState().addEvent(new Event(t + 2 + _rand.nextInt(7), "Guard", "goBack"));
    }

    public void noToSmoke(Double t) {
        if (_rand.nextBoolean()) yesToSmoke(t);
        else goBack(t);
    }

    public void goBack(Double t) {
        _model.getState().variables.put("GuardAvailable", true);
        _model.getState().addEvent(new Event(t + 25 + _rand.nextInt(10), "Cashier2", "requestToSmoke"));
    }
}
