package ru.tomtrix.synch.platform;

import java.util.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Agent
 */
public abstract class Agent {

    private static final Random _random = new Random(System.currentTimeMillis());

    public static boolean rand() {
        return _random.nextBoolean();
    }

    public static double rand(int n) {
        return _random.nextInt(n) + BigDecimal.valueOf(_random.nextDouble()).setScale(2, RoundingMode.UP).doubleValue();
    }

    protected final AbstractModel _model;
    protected final String _name;
    protected List<Event> _events = Collections.synchronizedList(new ArrayList<Event>());

    public abstract Agent cloneObject();

    public Agent(AbstractModel  model, String name) {
        _model = model;
        _name = name;
    }

    public Map<String, Object> getVariables() {
        return _model.getState().variables;
    }

    synchronized public void addEvent(Event event) {
        _events.add(event);
        Collections.sort(_events);
    }

    synchronized public Double getCurrentTimestamp() {
        return _events.isEmpty() ? null : _events.get(0).t;
    }

    synchronized public Event popEvent() {
        return _events.remove(0);
    }
}
