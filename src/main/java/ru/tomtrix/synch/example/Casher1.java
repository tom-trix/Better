package ru.tomtrix.synch.example;

import ru.tomtrix.synch.platform.AbstractModel;
import ru.tomtrix.synch.platform.Agent;
import ru.tomtrix.synch.platform.Event;
import ru.tomtrix.synch.platform.State;

/**
 * Created with IntelliJ IDEA.
 * User: tom-trix
 * Date: 4/16/13
 * Time: 12:56 PM
 */
@SuppressWarnings("unused")
public class Casher1 extends Agent {

    public Casher1(AbstractModel model, String name) {
        super(model, name);
    }

    @Override
    public void init(State state) {
        state.variables.put("Cashier1Available", true);
        state.addEvent(new Event(75 + _rand.nextInt(10), "Cashier1", "goWC"));
    }

    public void goBack(Double t) {
        _model.getState().variables.put("Cashier1Available", true);
    }

    public void goWC(Double t) {
        _model.getState().variables.put("Cashier1Available", false);
        _model.getState().addEvent(new Event(t + 1, "Cashier1", "goBack"));
    }

    public void servePurchaser(Double t) {

    }
}
