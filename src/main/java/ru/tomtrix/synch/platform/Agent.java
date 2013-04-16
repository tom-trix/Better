package ru.tomtrix.synch.platform;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: tom-trix
 * Date: 4/15/13
 * Time: 12:16 PM
 */
public abstract class Agent {
    protected final Random _rand = new Random(System.currentTimeMillis());
    protected final AbstractModel _model;

    public Agent(AbstractModel model) {
        _model = model;
    }

    public abstract void init(State state);
}
