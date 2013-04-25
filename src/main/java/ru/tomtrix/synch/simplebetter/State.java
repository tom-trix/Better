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
        StringBuilder sb = new StringBuilder(String.format("State #%d; Agent events: ", fingerprint));
        for (Agent agent : agents.values())
            sb.append(agent._events.size()).append(", ");
        return sb.toString();
    }
}
