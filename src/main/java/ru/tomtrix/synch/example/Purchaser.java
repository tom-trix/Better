package ru.tomtrix.synch.example;

import java.util.*;
import ru.tomtrix.synch.simplebetter.*;

@SuppressWarnings("unused")
public class Purchaser extends Agent {

    private double _tasteForTheft = rand(1)/2;

    public Purchaser(String name) {
        super(name);
        addEvents(new Event(5 + rand(30), _name, "appear", "", _name));
    }

    public void appear(Event event) {
        _tasteForTheft = rand(1)/2;
        addEvents(new Event(event.t + rand(1), "SuperMarket", "incVariable", "TotalPurchasers", _name),
                new Event(event.t + rand(1), "SuperMarket", "incVariable", "Purchasers", _name),
                new Event(event.t + 1 + rand(5), _name, "bringGoods", "", _name));
    }

    public void bringGoods(Event event) {
        List<Event> events = new ArrayList<>(Arrays.asList(new Event(event.t + 1 + rand(5), _name, rand(1)>0.8 ? "goToCashdesk" : "bringGoods", "", _name)));
        if (rand(1)*_tasteForTheft > 0.35)
            events.addAll(Arrays.asList(
                    new Event(event.t + rand(1), "SuperMarket", "incVariable", "Thefts", _name),
                    new Event(event.t +1, "Guard", "suspect", "", _name)));
        addEvents(events);
    }

    public void goToCashdesk(Event event) {
        String cashier = String.format("Cashier%d", rand() ? 1 : 2);
        addEvents(new Event(event.t + rand(1), cashier, "servePurchaser", "cash" + (rand() ? "less" : ""), _name));
    }

    public void accepted(Event event) {
        addEvents(new Event(event.t + rand(1), "SuperMarket", "decVariable", "Purchasers", _name),
                new Event(event.t + 5 + rand(30), _name, "appear", "", _name));
    }
}
