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

    public Casher2(AbstractModel model, String name) {
        super(model, name);
    }

    @Override
    public void init(State state) {
        state.variables.put("Cashier2Available", true);
        state.addEvent(new Event(90 + _rand.nextInt(10), "Cashier2", "goWC"));
    }

    public void goBack(Double t) {
        _model.getState().variables.put("Cashier2Available", true);
    }

    public void requestToSmoke(Double t) {
        boolean agree = _rand.nextDouble() > 0.4;
        _model.getState().variables.put("Cashier2Available", !agree);
        _model.getState().addEvent(new Event(t + 1, "Guard", agree ? "yesToSmoke" : "noToSmoke"));
        if (agree) _model.getState().addEvent(new Event(t + 2 + _rand.nextInt(7), "Cashier2", "goBack"));
    }

    public void goWC(Double t) {
        _model.getState().variables.put("Cashier2Available", false);
        _model.getState().addEvent(new Event(t + 1, "Cashier2", "goBack"));
    }

    public void servePurchaser(Double t) {

    }
}
