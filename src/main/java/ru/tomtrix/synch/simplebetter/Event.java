package ru.tomtrix.synch.simplebetter;

import ru.tomtrix.synch.HashSerializable;
import scala.collection.mutable.ListBuffer;

/**
 * Event
 */
public class Event implements Comparable<Event>, HashSerializable {

    public final double t;
    public final String agent;
    public final String action;
    public final String arg;
    public final String author;

    public Event(double t, String agent, String action, String arg, String author) {
        this.t = t;
        this.agent = agent;
        this.action = action;
        this.arg = arg;
        this.author = author;
    }

    @Override
    public int compareTo(Event o) {
        return (int) Math.signum(t - o.t); //DON'T FORGET ABOUT SIGN(X)
    }

    @Override
    public String toString() {
        return String.format("%.2f: %s.%s(%s) by %s", t, agent, action, arg, author);
    }

    @Override
    public scala.collection.Seq<String> toSetOfHash() {
        //return new Java2Scala<String>().asSet(new TreeSet<>(Arrays.asList(entity(agent), action, "by_" + entity(author))));
        ListBuffer<String> buffer = new ListBuffer<>();
        buffer.$plus$eq(agent.substring(0, 1));
        buffer.$plus$eq(action.substring(0, 3));
        buffer.$plus$eq(author.substring(0, 1));
        return buffer;
    }

    public String entity(String s) {
        return s.startsWith("Purchaser") ? "Purchaser" : s;
    }

    public String toHash() {
        return String.format("%s.%s() by_%s", entity(agent), action, entity(author));
    }
}
