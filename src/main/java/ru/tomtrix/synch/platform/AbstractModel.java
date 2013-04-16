package ru.tomtrix.synch.platform;

import java.util.*;
import java.util.concurrent.*;
import scala.runtime.AbstractFunction1;
import scala.concurrent.duration.Duration;
import akka.actor.Cancellable;
import ru.tomtrix.synch.*;

/**
 * Created with IntelliJ IDEA.
 * User: tom-trix
 * Date: 4/16/13
 * Time: 10:56 AM
 */
public class AbstractModel extends JavaModel<State> {

    protected transient final Map<String, Object> _agents = new ConcurrentHashMap<>();
    private transient Cancellable _timer;

    @Override
    public State startModelling() {
        _timer = system().scheduler().schedule(Duration.Zero(), Duration.create(30, TimeUnit.MILLISECONDS), new Runnable() {
            @Override
            public void run() {
                changeStateAndTime(new AbstractFunction1<State, Object>() {
                    @Override
                    public Object apply(State v1) {
                        if (v1.events.isEmpty()) return 0d;
                        Event event = v1.events.remove(0);
                        logger().info(String.format("Found event: %s", event));
                        try {
                            Object value = _agents.get(event.agent);
                            if (value instanceof Agent) {
                                Agent agent = (Agent) value;
                                agent.getClass().getMethod(event.action, v1.getClass(), Double.class).invoke(agent, v1, event.t);
                            }
                            else if (value instanceof String)
                                sendMessage(value.toString(), new EventMessage(event.t, actorname(), event));
                            else throw new RuntimeException(String.format("Value = %s", value));
                        } catch (Exception e) {logger().error("Error in reflection", e);}
                        return event.t - getTime();
                    }
                });
            }
        }, system().dispatcher());
        State state = new State();
        for (Object obj : _agents.values())
            if (obj instanceof Agent)
                ((Agent) obj).init(state);
        return state;
    }

    @Override
    public void onMessageReceived() {
        EventMessage m = (EventMessage) popMessage().get();
        scheduleEvent((Event) m.data());
    }

    @Override
    public scala.collection.immutable.Map<Category, Object> stopModelling() {
        _timer.cancel();
        return super.stopModelling();
    }

    public void scheduleEvent(final Event event) {
        changeStateAndTime(new AbstractFunction1<State, Object>() {
            @Override
            public Object apply(State v1) {
                v1.events.add(event);
                Collections.sort(v1.events);
                return 0d;
            }
        });
    }
}
