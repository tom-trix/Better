package ru.tomtrix.synch.simplebetter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * State
 */
public class State {

    public long fingerprint = 0;
    public Map<String, String> remoteAgents = new ConcurrentHashMap<>();
    public Map<String, Agent> agents = new ConcurrentHashMap<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(String.format("State #%d; Agent events: ", fingerprint));
        for (Agent agent : agents.values())
            sb.append(agent._events.size()).append(", ");
        return sb.toString();
    }

    @SuppressWarnings("unused")
    public State cloneObject() {
        State result = new State();
        result.fingerprint = fingerprint;
        result.remoteAgents = new ConcurrentHashMap<>(remoteAgents);
        result.agents = new ConcurrentHashMap<>();
        for (Map.Entry<String, Agent> e : agents.entrySet())
            result.agents.put(e.getKey(), e.getValue().cloneObject());
        return result;
    }
}
