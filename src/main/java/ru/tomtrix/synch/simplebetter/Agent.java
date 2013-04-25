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

    protected transient final AbstractModel _modelRef;
    public final String _name;
    public List<Event> _events = Collections.synchronizedList(new ArrayList<Event>());

    public abstract Agent cloneObject();

    public Agent(String name, AbstractModel model) {
        _name = name;
        _modelRef = model;
    }

    public void addEvents(Event ... events) {
        addEvents(Arrays.asList(events));
    }

    public void addEvents(Collection<Event> events) {
        synchronized (_modelRef) {
            _events.addAll(events);
            Collections.sort(_events);
            StringBuilder sb = new StringBuilder("Added events to ").append(_name).append(". Now: ");
            for (Event event : _events)
                sb.append(event.t).append(", ");
            _modelRef.logger().info(sb.toString());
        }
    }

    public Double getCurrentTimestamp() {
        synchronized (_modelRef) {
            return _events.isEmpty() ? null : _events.get(0).t;
        }
    }

    public Event popEvent() {
        synchronized (_modelRef) {
            return _events.remove(0);
        }
    }
}
