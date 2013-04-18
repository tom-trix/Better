package ru.tomtrix.synch.platform;

import scala.Serializable;

/**
 * Event
 */
public class Event implements Comparable<Event>, Serializable {

    final double t;
    final String agent;
    final String action;
    final String sender;

    public Event(double t, String agent, String action, String sender) {
        this.t = t;
        this.agent = agent;
        this.action = action;
        this.sender = sender;
    }

    @Override
    public int compareTo(Event o) {
        return (int) (t - o.t);
    }

    @Override
    public String toString() {
        return String.format("%.2f: %s.%s()", t, agent, action);
    }
}
