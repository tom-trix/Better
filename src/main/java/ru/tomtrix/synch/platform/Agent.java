package ru.tomtrix.synch.platform;

import java.util.Random;

/**
 * Agent
 */
public abstract class Agent {
    protected final Random _rand = new Random(System.currentTimeMillis());
    protected final AbstractModel _model;
    protected final String _name;

    public Agent(AbstractModel model, String name) {
        _model = model;
        _name = name;
    }

    public abstract void init(State state);
}
