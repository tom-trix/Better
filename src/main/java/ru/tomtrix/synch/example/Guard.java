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
        state.events.add(new Event(5d, "Guard", "openTheDoor"));
        goBack(state, 5d);
    }

    public void openTheDoor(State state, Double t) {
        state.variables.put("Door", "open");
    }

    public void yesToSmoke(State state, Double t) {
        state.variables.put("GuardAvailable", false);
        _model.scheduleEvent(new Event(t + 2 + _rand.nextInt(7), "Guard", "goBack"));
    }

    public void noToSmoke(State state, Double t) {
        if (_rand.nextBoolean()) yesToSmoke(state, t);
        else goBack(state, t);
    }

    public void goBack(State state, Double t) {
        state.variables.put("GuardAvailable", true);
        _model.scheduleEvent(new Event(t + 25 + _rand.nextInt(10), "Cashier2", "requestToSmoke"));
    }
}
