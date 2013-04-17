package ru.tomtrix.synch.example;

import ru.tomtrix.synch.platform.AbstractModel;
import ru.tomtrix.synch.platform.Agent;
import ru.tomtrix.synch.platform.Event;
import ru.tomtrix.synch.platform.State;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: tom-trix
 * Date: 4/17/13
 * Time: 11:56 PM
 */
@SuppressWarnings("unused")
public class Purchaser extends Agent {

    private double _tasteForTheft = _rand.nextDouble()/2;

    public Purchaser(AbstractModel model, String name) {
        super(model, name);
    }

    @Override
    public void init(State state) {
        state.addEvent(new Event(5 + _rand.nextInt(30), _name, "appear"));
    }

    public void appear(Double t) {
        _model.getState().addEvent(new Event(t + 1 + _rand.nextInt(5), _name, "bringGoods"));
    }

    public void bringGoods(Double t) {
        Map<String, Object> vars = _model.getState().variables;
        if (_rand.nextDouble()*_tasteForTheft > 0.3) {
            vars.put("Thefts", vars.containsKey("Thefts") ? (int)vars.get("Thefts") + 1 : 1);
            _model.getState().addEvent(new Event(t +1, "Guard", "suspect"));
        }
        boolean finish = _rand.nextDouble() > 0.8;
        _model.getState().addEvent(new Event(t + 1 + _rand.nextInt(5), _name, finish ? "goToCashdesk" : "bringGoods"));
    }

    public void goToCashdesk(Double t) {
        String cashier = String.format("Cashier%d", _rand.nextBoolean() ? 1 : 2);
        _model.getState().addEvent(new Event(t + 1, cashier, "servePurchaser"));
    }
}
