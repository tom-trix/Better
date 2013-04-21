package ru.tomtrix.synch.platform;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * State
 */
public class State {

    public long fingerprint = 0;
    public Map<String, Object> variables = new ConcurrentHashMap<>();
    public Map<String, Agent> agents = new ConcurrentHashMap<>();
    public Map<String, String> remoteAgents = new ConcurrentHashMap<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Variables: ");
        for (Map.Entry<String, Object> e : variables.entrySet())
            sb.append(String.format("%s -> %s; ", e.getKey(), e.getValue()));
        return sb.toString();
    }

    @SuppressWarnings("unused")
    public State cloneObject() {
        State result = new State();
        result.fingerprint = fingerprint;
        result.variables = new ConcurrentHashMap<>(variables);
        result.agents = new ConcurrentHashMap<>(agents);
        result.remoteAgents = new ConcurrentHashMap<>(remoteAgents);
        return result;
    }

    public void addEvent(Event event) {
        agents.get(event.agent).addEvent(event);
    }
}
