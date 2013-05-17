package ru.tomtrix.synch.simplebetter;

import java.util.concurrent.*;

import ru.tomtrix.synch.algorithms.AgentEvent;
import scala.concurrent.duration.Duration;
import akka.actor.Cancellable;
import ru.tomtrix.synch.*;

/**
 * Abstract Model
 */
public class AbstractModel extends JavaModel<State> {

    private transient Cancellable _timer;

    protected final State _state = new State();

    @Override
    public State startModelling() {
        final Model<State> self = this;
        _timer = system().scheduler().schedule(Duration.Zero(), Duration.create(20, TimeUnit.MILLISECONDS), new Runnable() {
            @Override
            public void run() {
               synchronized (self) {
                   if (getState().locked) return;
                   if (getTime() > 200) {stopModelling(); return; }

                   // поиск агента с минимальной временной меткой
                   Agent cur_agent = null;
                   for (Agent agent : getState().agents.values()) {
                       Double t = agent.getCurrentTimestamp();
                       if (t == null) continue;
                       if (cur_agent == null || t < cur_agent.getCurrentTimestamp())
                           cur_agent = agent;
                   }
                   Double t1 = cur_agent != null ? cur_agent.getCurrentTimestamp() : null;

                   // поглядим, есть ли что-то во входящей очереди
                   Double t2 = null;
                   try {
                       t2 = peekMessage().get().t();
                   } catch (Exception ignored) {}

                   // выборка события с меньшей временной меткой
                   Event event;
                   if (t1 == null && t2 == null) return;
                   else if (t1 == null) event = (Event) popMessage().get().data();
                   else if (t2 == null) event = cur_agent.popEvent();
                   else if (t2 <= t1) event = (Event) popMessage().get().data();
                   else event = cur_agent.popEvent();

                   // обработка события
                   boolean isRemote = getState().remoteAgents.containsKey(event.agent);
                   logger().info(String.format("Found event: %s", event));
                   if (event.t < getTime()) throw new AssertionError(String.format("event.t (%.2f) < getTime (%.2f)", event.t, getTime()));
                   if (isRemote)
                       sendMessage(getState().remoteAgents.get(event.agent), new EventMessage(event.t, actorname(), event));
                   else if (getState().agents.containsKey(event.agent))
                       try {
                           Agent receiver = getState().agents.get(event.agent);
                           receiver.getClass().getMethod(event.action, Event.class).invoke(receiver, event);
                       } catch (Exception e) {logger().error("Error in reflection", e);}
                   else throw new RuntimeException(String.format("No agent found (%s)", event.agent));
                   getState().fingerprint += 1;
                   registerEvent(event.t, new AgentEvent(event.author, event.agent, event.action), isRemote, !getState().agents.containsKey(event.author));
                   addTime(event.t - getTime());

                   //
                   AgentEvent ae = getState().deadlockEvent;
                   if (ae != null)
                       if (event.author.equals(ae.agent()) && event.agent.equals(ae.recipient()) && event.action.equals(ae.action())) {
                           logger().debug(String.format("Deadlock event %s handled!", ae));
                           getState().deadlockEvent = null;
                       }
               }
            }
        }, system().dispatcher());
        return _state;
    }

    @Override
    public AgentEvent convertToEvent(EventMessage m) {
        Event event = (Event) m.data();
        return new AgentEvent(event.author, event.agent, event.action);
    }

    @Override
    public String convertToActor(AgentEvent e) {
        return getState().remoteAgents.get(e.agent());
    }

    @Override
    public void suspendModelling() {
        getState().locked = true;
    }

    @Override
    public void resumeModelling() {
        getState().locked = false;
    }

    @Override
    public void handleDeadlockMessage() {
        /*if (getState().deadlockEvent != null) throw new RuntimeException(String.format("Deadlock event = %s (expected NULL)", getState().deadlockEvent));
        getState().deadlockEvent = m.waitFor();*/
    }

    @Override
    public scala.collection.immutable.Map<Category, Object> stopModelling() {
        _timer.cancel();
        return super.stopModelling();
    }
}
