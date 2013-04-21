package ru.tomtrix.synch.example;

import ru.tomtrix.synch.platform.AbstractModel;
import ru.tomtrix.synch.platform.Agent;
import ru.tomtrix.synch.platform.Event;
import ru.tomtrix.synch.platform.State;

import java.util.Map;

@SuppressWarnings("unused")
public class Purchaser extends Agent {

    private double _tasteForTheft = rand(1)/2;

    public Purchaser(AbstractModel model, String name) {
        super(model, name);
        getVariables().put("Purchasers", getVariables().containsKey("Purchasers") ? (int)getVariables().get("Purchasers") + 1 : 1);
        addEvent(new Event(5 + rand(30), _name, "appear", _name));
    }

    public void appear(Double t, String sender) {
        addEvent(new Event(t + 1 + rand(5), _name, "bringGoods", _name));
    }

    public void bringGoods(Double t, String sender) {
        if (rand(1)*_tasteForTheft > 0.3) {
            getVariables().put("Thefts", getVariables().containsKey("Thefts") ? (int)getVariables().get("Thefts") + 1 : 1);
            addEvent(new Event(t +1, "Guard", "suspect", _name));
        }
        boolean finish = rand(1) > 0.8;
        addEvent(new Event(t + 1 + rand(5), _name, finish ? "goToCashdesk" : "bringGoods", _name));
    }

    public void goToCashdesk(Double t, String sender) {
        String cashier = String.format("Cashier%d", rand() ? 1 : 2);
        addEvent(new Event(t + 1, cashier, "servePurchaser", _name));
    }

    public void cashOrCashless(Double t, String sender) {
        addEvent(new Event(t + 1, sender, "cash" + (rand() ? "less" : ""), _name));
    }

    public void accepted(Double t, String sender) {
        getVariables().put("Purchasers", (int)getVariables().get("Purchasers") - 1);
        addEvent(new Event(t + 5 + rand(30), _name, "appear", _name));
    }
}
