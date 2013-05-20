package ru.tomtrix.synch.simplebetter;

import java.util.*;

import ru.tomtrix.synch.structures.TimeEvent;
import scala.Serializable;

/**
 * Agent
 */
public abstract class Agent implements Serializable {

    private static final Random _random = new Random(System.currentTimeMillis());

    public static boolean rand() {
        return _random.nextBoolean();
    }

    public static float rand(int n) {
        return _random.nextInt(n) + _random.nextFloat();
    }

    public final String _name;
    public List<TimeEvent> _events = Collections.synchronizedList(new ArrayList<TimeEvent>());

    public Agent(String name) {
        _name = name;
    }

    public abstract void init();

    public void addEvents(TimeEvent ... events) {
        addEvents(Arrays.asList(events));
    }

    synchronized public void addEvents(Collection<TimeEvent> events) {
        _events.addAll(events);
        Collections.sort(_events);
    }

    synchronized public Float getCurrentTimestamp() {
        return _events.isEmpty() ? null : _events.get(0).t();
    }

    synchronized public TimeEvent popEvent() {
        return _events.remove(0);
    }

    synchronized public void flush() {
        _events.clear();
        init();
    }
}
