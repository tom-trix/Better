package ru.tomtrix.synch.platform;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * State
 */
public class State {

    public long fingerprint = 0;
    public Map<String, Object> variables = new ConcurrentHashMap<>();
    public Map<String, String> remoteAgents = new ConcurrentHashMap<>();
    public Map<String, Agent> agents = new ConcurrentHashMap<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(String.format("State #%d; Variables: ", fingerprint));
        for (Map.Entry<String, Object> e : variables.entrySet())
            sb.append(String.format("%s -> %s; ", e.getKey(), e.getValue()));
        return sb.toString();
    }

    @SuppressWarnings("unused")
    public State cloneObject() {
        State result = new State();
        result.fingerprint = fingerprint;
        result.variables = new ConcurrentHashMap<>(variables);
        result.remoteAgents = new ConcurrentHashMap<>(remoteAgents);
        result.agents = new ConcurrentHashMap<>();
        for (Map.Entry<String, Agent> e : agents.entrySet())
            result.agents.put(e.getKey(), e.getValue().cloneObject());
        return result;
    }

    public void addEvent(Event event) {
        agents.get(event.agent).addEvent(event);
    }
}
