package ru.tomtrix.synch.simplebetter;

import java.util.concurrent.*;
import scala.concurrent.duration.Duration;
import akka.actor.Cancellable;
import ru.tomtrix.synch.*;

/**
 * Abstract Model
 */
public class AbstractModel extends JavaModel<State> {

    private transient Cancellable _timer;
    protected final transient State _state = new State();

    @Override
    public State startModelling() {
        _timer = system().scheduler().schedule(Duration.Zero(), Duration.create(20, TimeUnit.MILLISECONDS), new Runnable() {
            @Override
            public void run() {
                // поиск агента с минимальной временной меткой
                Agent cur_agent = null;
                for (Agent agent : getState().agents.values()) {
                    Double t = agent.getCurrentTimestamp();
                    if (t == null) continue;
                    if (cur_agent == null || t < cur_agent.getCurrentTimestamp())
                        cur_agent = agent;
                }
                if (cur_agent == null) return;

                // обработка события
                Event event = cur_agent.popEvent();
                logger().info(String.format("Found event: %s", event));
                if (getState().remoteAgents.containsKey(event.agent))
                    sendMessage(getState().remoteAgents.get(event.agent), new EventMessage(event.t, actorname(), event));
                else if (getState().agents.containsKey(event.agent))
                    try {
                        Agent receiver = getState().agents.get(event.agent);
                        receiver.getClass().getMethod(event.action, Event.class).invoke(receiver, event);
                    } catch (Exception e) {logger().error("Error in reflection", e);}
                else throw new RuntimeException(String.format("No agent found (%s)", event.agent));
                getState().fingerprint += 1;
                addTime(event.t - getTime());
            }
        }, system().dispatcher());
        return _state;
    }

    @Override
    public scala.collection.immutable.Map<Category, Object> stopModelling() {
        _timer.cancel();
        return super.stopModelling();
    }

    @Override
    public void onMessageReceived() {
        EventMessage m = (EventMessage) popMessage().get();
        Event e = (Event) m.data();
        getState().agents.get(e.agent).addEvent(e);
    }
}
