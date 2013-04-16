package ru.tomtrix.synch.example;

import ru.tomtrix.synch.platform.*;

/**
 * Created with IntelliJ IDEA.
 * User: tom-trix
 * Date: 4/16/13
 * Time: 12:56 PM
 */
@SuppressWarnings("unused")
public class Casher2 extends Agent {

    public Casher2(AbstractModel model) {
        super(model);
    }

    @Override
    public void init(State state) {}

    public void requestToSmoke(State state, Double t) {
        _model.scheduleEvent(new Event(t, "Guard", _rand.nextDouble() > 0.4 ? "yesToSmoke" : "noToSmoke"));
    }
}
