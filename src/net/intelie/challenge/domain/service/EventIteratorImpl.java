package net.intelie.challenge.domain.service;

import jdk.jshell.spi.ExecutionControl;
import net.intelie.challenge.domain.abstractions.service.EventIterator;
import net.intelie.challenge.domain.models.Event;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EventIteratorImpl implements EventIterator {
    private final ArrayList<Event> _events;
    private int index;

    public EventIteratorImpl(ArrayList<Event> events, Predicate<Event> eventPredicate) {
        this._events = (ArrayList<Event>) events.parallelStream().filter(eventPredicate).collect(Collectors.toList());
        index = -1;
        moveNext();
    }

    @Override
    public synchronized boolean moveNext() {
        index++;
        return _events.size() > index;
    }

    @Override
    public synchronized Event current() {
        if(_events.size() <= index) {
            throw new IllegalStateException();
        }

        return _events.get(index);
    }

    @Override
    public synchronized void remove() {
        _events.remove(index);
    }

    @Override
    public void close() throws Exception {
        throw new ExecutionControl.NotImplementedException("Method not supported yet.");
    }
}
