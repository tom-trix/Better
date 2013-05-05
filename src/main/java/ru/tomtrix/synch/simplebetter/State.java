package ru.tomtrix.synch.simplebetter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import ru.tomtrix.synch.*;
import scala.collection.mutable.ListBuffer;

/**
 * State
 */
public class State implements HashSerializable {

    public long fingerprint = 0;
    public Map<String, String> remoteAgents = new ConcurrentHashMap<>();
    public Map<String, Agent> agents = new ConcurrentHashMap<>();

    @Override
    public scala.collection.Seq<String> toSetOfHash() {
        /*Set<String> s = new HashSet<>();
        for (Agent agent : agents.values())
            for (Event event : agent._events)
                s.add(event.toHash());
        return new Java2Scala<String>().asSet(s);*/
        ListBuffer<String> buffer = new ListBuffer<>();
        for (Agent agent : agents.values())
            for (Event event : agent._events)
                buffer.$plus$eq(event.toHash());
        return buffer;
    }

    @Override
    public String toString() {
        int total = 0;
        for (Agent agent : agents.values())
            total += agent._events.size();
        return String.format("State #%d; Total events: %d", fingerprint, total);
    }
}
