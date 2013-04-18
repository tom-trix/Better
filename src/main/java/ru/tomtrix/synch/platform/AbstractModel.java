package ru.tomtrix.synch.platform;

import java.util.*;
import java.util.concurrent.*;
import scala.concurrent.duration.Duration;
import akka.actor.Cancellable;
import ru.tomtrix.synch.*;

/**
 * Abstract Model
 */
public class AbstractModel extends JavaModel<State> {

    protected transient final Map<String, Object> _agents = new ConcurrentHashMap<>();
    private transient Cancellable _timer;

    @Override
    public State startModelling() {
        _timer = system().scheduler().schedule(Duration.Zero(), Duration.create(30, TimeUnit.MILLISECONDS), new Runnable() {
            @Override
            public void run() {
                if (getState().events.isEmpty()) return;
                final Event event = getState().events.remove(0);
                logger().info(String.format("Found event: %s", event));
                final Object value = _agents.get(event.agent);
                if (value instanceof String)
                    sendMessage(value.toString(), new EventMessage(event.t, actorname(), event));
                else if (value instanceof Agent)
                    try {
                        Agent agent = (Agent) value;
                        agent.getClass().getMethod(event.action, Double.class, String.class).invoke(agent, event.t, event.sender);
                    } catch (Exception e) {logger().error("Error in reflection", e);}
                else throw new RuntimeException(String.format("Value = %s", value));
                getState().fingerprint += 1;
                addTime(event.t - getTime());
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
        getState().events.add((Event) m.data());
    }

    @Override
    public scala.collection.immutable.Map<Category, Object> stopModelling() {
        _timer.cancel();
        return super.stopModelling();
    }
}
