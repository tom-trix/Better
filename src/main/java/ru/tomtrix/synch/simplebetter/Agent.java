package ru.tomtrix.synch.simplebetter;

import java.util.*;

/**
 * Agent
 */
public abstract class Agent {

    private static final Random _random = new Random(System.currentTimeMillis());

    public static boolean rand() {
        return _random.nextBoolean();
    }

    public static double rand(int n) {
        return _random.nextInt(n) + Double.valueOf(((Double)_random.nextDouble()).toString().substring(0, 4));
    }

    public final String _name;
    public List<Event> _events = Collections.synchronizedList(new ArrayList<Event>());

    public abstract Agent cloneObject();

    public Agent(String name) {
        _name = name;
    }

    synchronized public void addEvent(Event event) {
        _events.add(event);
        Collections.sort(_events);
    }

    synchronized public Double getCurrentTimestamp() {
        return _events.isEmpty() ? null : _events.get(0).t;
    }

    synchronized public Event popEvent() {
        return _events.remove(0);
    }
}
