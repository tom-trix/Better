package ru.tomtrix.synch.platform;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * State
 */
public class State {

    List<Event> events = Collections.synchronizedList(new ArrayList<Event>());
    public Map<String, Object> variables = new ConcurrentHashMap<>();
    public long fingerprint = 0;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(String.format("Fingerprint: %d; Variables: ", fingerprint));
        for (Map.Entry<String, Object> e : variables.entrySet())
            sb.append(String.format("%s -> %s; ", e.getKey(), e.getValue()));
        sb.append("Events: ");
        for (Event e : events)
            sb.append(String.format("%s; ", e));
        return sb.toString();
    }

    @SuppressWarnings("unused")
    public State cloneObject() {
        State result = new State();
        result.events = Collections.synchronizedList(new ArrayList<>(events));
        result.variables = new ConcurrentHashMap<>(variables);
        result.fingerprint = fingerprint;
        return result;
    }

    synchronized public void addEvent(Event event) {
        events.add(event);
        Collections.sort(events);
    }
}
