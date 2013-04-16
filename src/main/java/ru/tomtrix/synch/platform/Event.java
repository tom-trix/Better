package ru.tomtrix.synch.platform;

import scala.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: tom-trix
 * Date: 4/16/13
 * Time: 11:06 AM
 */
public class Event implements Comparable<Event>, Serializable {

    final double t;
    final String agent;
    final String action;

    public Event(double t, String agent, String action) {
        this.t = t;
        this.agent = agent;
        this.action = action;
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
