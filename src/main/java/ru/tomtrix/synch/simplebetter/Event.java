package ru.tomtrix.synch.simplebetter;

import scala.Serializable;

/**
 * Event
 */
public class Event implements Comparable<Event>, Serializable {

    public final double t;
    public final String agent;
    public final String action;
    public final String arg;
    public final String sender;

    public Event(double t, String agent, String action, String arg, String sender) {
        this.t = t;
        this.agent = agent;
        this.action = action;
        this.arg = arg;
        this.sender = sender;
    }

    @Override
    public int compareTo(Event o) {
        return (int) (t - o.t);
    }

    @Override
    public String toString() {
        return String.format("%.2f: %s.%s(%s)", t, agent, action, arg);
    }
}
