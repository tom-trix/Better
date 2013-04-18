package ru.tomtrix.synch.platform;

import java.util.Random;

/**
 * Agent
 */
public abstract class Agent {

    private static final Random _random = new Random(System.currentTimeMillis());

    public static boolean rand() {
        return _random.nextBoolean();
    }

    public static double rand(int n) {
        return _random.nextInt(n) + _random.nextDouble();
    }

    protected final AbstractModel _model;
    protected final String _name;

    public Agent(AbstractModel model, String name) {
        _model = model;
        _name = name;
    }

    public abstract void init(State state);
}
