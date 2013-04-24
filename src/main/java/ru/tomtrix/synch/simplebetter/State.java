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
        return String.format("State #%d;", fingerprint);
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
