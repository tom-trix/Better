package ru.tomtrix.synch.simplebetter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Environment
 */
@SuppressWarnings("unused")
public abstract class Environment extends Agent {

    protected Map<String, Object> _variables = new ConcurrentHashMap<>();

    public Environment(String name) {
        super(name);
    }

    public void setVariable(Event event) {
        _variables.put(event.arg.split("#")[0], event.arg.split("#")[1]);
    }

    public void checkVariable(Event event) {
        addEvents(new Event(event.t, event.sender, "variableIs", String.format("%s#%s", event.arg, _variables.get(event.arg)), _name));
    }

    public void incVariable(Event event) {
        _variables.put(event.arg, (int) _variables.get(event.arg) + 1);
    }

    public void decVariable(Event event) {
        _variables.put(event.arg, (int) _variables.get(event.arg) - 1);
    }
}
