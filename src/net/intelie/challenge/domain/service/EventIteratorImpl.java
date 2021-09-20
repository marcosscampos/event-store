package net.intelie.challenge.domain.service;

import jdk.jshell.spi.ExecutionControl;
import net.intelie.challenge.domain.abstractions.service.EventIterator;
import net.intelie.challenge.domain.models.Event;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EventIteratorImpl implements EventIterator {
    private final ArrayList<Event> _events;
    private final Predicate<Event> predicate;

    public EventIteratorImpl(ArrayList<Event> events, Predicate<Event> eventPredicate) {
        this._events = (ArrayList<Event>) events.parallelStream().filter(eventPredicate).collect(Collectors.toList());
        this.predicate = eventPredicate;
    }

    @Override
    public synchronized boolean moveNext() {
        return _events.iterator().hasNext();
    }

    @Override
    public synchronized Event current() {
        if (!moveNext()) {
            throw new IllegalStateException("There is no next event.");
        } else {
            return _events.parallelStream().filter(predicate).findFirst().get();
        }
    }

    @Override
    public synchronized void remove() {
        Event event = _events.parallelStream().filter(predicate).findFirst().get();

        while (_events.iterator().hasNext()) {
            if (event == current()) {
                _events.iterator().remove();
            }
        }
    }

    @Override
    public void close() throws Exception {
        throw new ExecutionControl.NotImplementedException("Method not supported yet.");
    }
}
