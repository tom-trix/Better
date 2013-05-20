package ru.tomtrix.synch.simplebetter;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.*;
import scala.concurrent.duration.*;
import akka.actor.Cancellable;
import ru.tomtrix.synch.*;
import ru.tomtrix.synch.structures.*;

/**
 * Abstract Model
 */
public class AbstractModel extends JavaModel<State> {

    private transient Cancellable _timer;
    private volatile boolean _locked = false;
    protected transient Map<String, Agent> _agents = new TreeMap<>();
    protected transient Map<String, String> _remoteAgents = new TreeMap<>();

    @Override
    public State startModelling() {
        _locked = false;
        // запускаем таймер
        final Model<State> self = this;
        _timer = system().scheduler().schedule(Duration.Zero(), Duration.create(20, TimeUnit.MILLISECONDS), new Runnable() {
            @Override
            public void run() {
               synchronized (self) {
                   if (_locked) {logger().debug("I am locked!"); return;}

                   // поиск агента с минимальной временной меткой
                   Agent cur_agent = null;
                   for (Agent agent : getState().agents.values()) {
                       Float t = agent.getCurrentTimestamp();
                       if (t == null) continue;
                       if (cur_agent == null || t < cur_agent.getCurrentTimestamp())
                           cur_agent = agent;
                   }
                   Float t1 = cur_agent != null ? cur_agent.getCurrentTimestamp() : null;

                   // поглядим, есть ли что-то во входящей очереди
                   Float t2 = null;
                   try {
                       t2 = peekMessage().get().t();
                   } catch (Exception ignored) {}

                   // выборка события с меньшей временной меткой
                   TimeEvent event;
                   if (t1 == null && t2 == null) return;
                   else if (t1 == null) event = popMessage().get().timeevent();
                   else if (t2 == null) event = cur_agent.popEvent();
                   else if (t2 <= t1) event = popMessage().get().timeevent();
                   else event = cur_agent.popEvent();

                   // обработка события
                   boolean isRemote = getState().remoteAgents.containsKey(event.event().patiens());
                   String remoteActorname = getState().remoteAgents.get(event.event().patiens());
                   logger().info(String.format("Found event: %s", event));
                   statEventHandled();
                   if (isRemote)
                       sendMessage(remoteActorname, new EventMessage(actorname(), event));
                   else getState().agents.get(event.event().patiens()).addEvents(runAgent(event));
                   getState().fingerprint += 1;
                   registerEvent(event, isRemote, getState().remoteAgents.containsKey(event.event().agens()), remoteActorname);
                   addTime(event);
               }
            }
        }, system().dispatcher());
        // сброс агентов (вот почему запрещена инициализация в конструкторе: вдруг модель будет прогоняться 2 или более раз?)
        for (Agent agent : _agents.values())
            agent.flush();
        // создаём новый экземпляр State (важно, если модель прогоняется 2 или более раз)
        return new State(_agents, _remoteAgents);
    }

    /*@Override
    public AgentEvent convertToEvent(EventMessage m) {
        Event event = (Event) m.data();
        return new AgentEvent(event.author, event.agent, event.action);
    }

    @Override
    public String convertToActor(AgentEvent e) {
        return getState().remoteAgents.get(e.agent());
    }*/

    @Override
    synchronized public void suspendModelling(boolean suspend) {
        _locked = suspend;
    }

    @Override
    public TimeEvent[] simulateStep(TimeEvent e) {
        Collection<TimeEvent> newEvents = runAgent(e);
        return newEvents.toArray(new TimeEvent[newEvents.size()]);
    }

    /*@Override
    synchronized public void handleDeadlockMessage(DeadlockMessage m) {
        logger().debug(String.format("Found deadlock! otherLocked = %s", _otherLocked));
        if (!getState().locked) {
            if (m.isSuspended() && !_otherLocked) _otherLocked = true;
            else if (!m.isSuspended() && _otherLocked) _otherLocked = false;
            else logger().error(String.format("Wrong deadlock situation(1): m.suspended = %s, otherLocked = %s", m.isSuspended(), _otherLocked));
        }
        else logger().error("Wrong deadlock situation(2)");
    }*/

    @Override
    public scala.collection.immutable.Map<Category, Object> stopModelling() {
        _timer.cancel();
        return super.stopModelling();
    }

    @SuppressWarnings("unchecked")
    private Collection<TimeEvent> runAgent(TimeEvent event) {
        Collection<TimeEvent> result = Collections.emptyList();
        if (getState().agents.containsKey(event.event().patiens()))
            try {
                Agent recipient = getState().agents.get(event.event().patiens());
                Object newEvents = recipient.getClass().getMethod(event.event().predicate(), TimeEvent.class).invoke(recipient, event);
                result = (Collection<TimeEvent>) newEvents;
            } catch (Exception e) {logger().error("Error in reflection", e);}
        return result;
    }
}
