package ru.tomtrix.synch.platform;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: tom-trix
 * Date: 4/16/13
 * Time: 10:57 AM
 */
public class State {

    public List<Event> events = Collections.synchronizedList(new ArrayList<Event>());
    public Map<String, Object> variables = new ConcurrentHashMap<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Variables: ");
        for (Map.Entry<String, Object> e : variables.entrySet())
            sb.append(String.format("%s: %s; ", e.getKey(), e.getValue()));
        sb.append("Events: ");
        for (Event e : events)
            sb.append(String.format("%s; ", e));
        return sb.toString();
    }

    @SuppressWarnings("unused")
    public State cloneObject() {
        State result = new State();
        result.events = Collections.synchronizedList(events);
        result.variables = new ConcurrentHashMap<>(variables);
        return result;
    }
}
