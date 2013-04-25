package ru.tomtrix.synch.simplebetter;

import java.util.*;
import scala.Serializable;
import ru.tomtrix.synch.ApacheLogger;

/**
 * Agent
 */
public abstract class Agent implements Serializable {

    private static final Random _random = new Random(System.currentTimeMillis());

    public static boolean rand() {
        return _random.nextBoolean();
    }

    public static double rand(int n) {
        return _random.nextInt(n) + Double.valueOf(((Double)_random.nextDouble()).toString().substring(0, 4));
    }

    public final String _name;
    public List<Event> _events = Collections.synchronizedList(new ArrayList<Event>());

    public Agent(String name) {
        _name = name;
    }

    public void addEvents(Event ... events) {
        addEvents(Arrays.asList(events));
    }

    synchronized public void addEvents(Collection<Event> events) {
        _events.addAll(events);
        Collections.sort(_events);
        StringBuilder sb = new StringBuilder("Added events to ").append(_name).append(". Now: ");
        for (Event event : _events)
            sb.append(event.t).append(", ");
        ApacheLogger.logger().info(sb.toString());
    }

    synchronized public Double getCurrentTimestamp() {
        return _events.isEmpty() ? null : _events.get(0).t;
    }

    synchronized public Event popEvent() {
        return _events.remove(0);
    }
}
