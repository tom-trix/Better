package ru.tomtrix.synch.example;

import ru.tomtrix.synch.platform.AbstractModel;
import ru.tomtrix.synch.platform.Agent;
import ru.tomtrix.synch.platform.Event;
import ru.tomtrix.synch.platform.State;

import java.util.Map;

@SuppressWarnings("unused")
public class Purchaser extends Agent {

    private double _tasteForTheft = _rand.nextDouble()/2;

    public Purchaser(AbstractModel model, String name) {
        super(model, name);
    }

    @Override
    public void init(State state) {
        Map<String, Object> vars = _model.getState().variables;
        vars.put("Purchasers", vars.containsKey("Purchasers") ? (int)vars.get("Purchasers") + 1 : 1);
        state.addEvent(new Event(5 + _rand.nextInt(30), _name, "appear", _name));
    }

    public void appear(Double t, String sender) {
        _model.getState().addEvent(new Event(t + 1 + _rand.nextInt(5), _name, "bringGoods", _name));
    }

    public void bringGoods(Double t, String sender) {
        Map<String, Object> vars = _model.getState().variables;
        if (_rand.nextDouble()*_tasteForTheft > 0.3) {
            vars.put("Thefts", vars.containsKey("Thefts") ? (int)vars.get("Thefts") + 1 : 1);
            _model.getState().addEvent(new Event(t +1, "Guard", "suspect", _name));
        }
        boolean finish = _rand.nextDouble() > 0.8;
        _model.getState().addEvent(new Event(t + 1 + _rand.nextInt(5), _name, finish ? "goToCashdesk" : "bringGoods", _name));
    }

    public void goToCashdesk(Double t, String sender) {
        String cashier = String.format("Cashier%d", _rand.nextBoolean() ? 1 : 2);
        _model.getState().addEvent(new Event(t + 1, cashier, "servePurchaser", _name));
    }

    public void cashOrCashless(Double t, String sender) {
        _model.getState().addEvent(new Event(t + 1, sender, "cash" + (_rand.nextBoolean() ? "less" : ""), _name));
    }

    public void accepted(Double t, String sender) {
        _model.getState().variables.put("Purchasers", (int)_model.getState().variables.get("Purchasers") - 1);
        _model.getState().addEvent(new Event(t + 5 + _rand.nextInt(30), _name, "appear", _name));
    }
}
