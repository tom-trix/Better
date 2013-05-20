package ru.tomtrix.synch.simplebetter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import ru.tomtrix.synch.structures.*;

/**
 * Environment
 */
@SuppressWarnings("unused")
public abstract class Environment extends Agent {

    protected Map<String, Object> _variables = new ConcurrentHashMap<>();

    public Environment(String name) {
        super(name);
    }

    public void setVariable(TimeEvent event) {
        String arg = event.event().userdata().toString();
        _variables.put(arg.split("#")[0], arg.split("#")[1]);
    }

    public void checkVariable(TimeEvent event) {
        String arg = event.event().userdata().toString();
        addEvents(new TimeEvent(event.t(), new AgentEvent(_name, event.event().agens(), "variableIs").withData(String.format("%s#%s", arg, _variables.get(arg)))));
    }

    public void incVariable(TimeEvent event) {
        String arg = event.event().userdata().toString();
        _variables.put(arg, (int) _variables.get(arg) + 1);
    }

    public void decVariable(TimeEvent event) {
        String arg = event.event().userdata().toString();
        _variables.put(arg, (int) _variables.get(arg) - 1);
    }
}
