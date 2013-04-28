package ru.tomtrix.synch.simplebetter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import scala.Serializable;

/**
 * State
 */
public class State implements Serializable {

    public long fingerprint = 0;
    public Map<String, String> remoteAgents = new ConcurrentHashMap<>();
    public Map<String, Agent> agents = new ConcurrentHashMap<>();

    @Override
    public String toString() {
        int total = 0;
        for (Agent agent : agents.values())
            total += agent._events.size();
        return String.format("State #%d; Total events: %d", fingerprint, total);
    }
}
