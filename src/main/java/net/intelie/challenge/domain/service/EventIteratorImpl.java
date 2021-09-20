package net.intelie.challenge.domain.service;

import jdk.jshell.spi.ExecutionControl;
import net.intelie.challenge.domain.abstractions.service.EventIterator;
import net.intelie.challenge.domain.models.Event;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EventIteratorImpl implements EventIterator {
    private final List<Event> _events;
    private final Predicate<Event> predicate;

    public EventIteratorImpl(List<Event> events, Predicate<Event> eventPredicate) {
        this._events = events.parallelStream().filter(eventPredicate).collect(Collectors.toList());
        this.predicate = eventPredicate;
    }

    @Override
    public synchronized boolean moveNext() {
        // Checks if there is an event at the next index.
        return _events.iterator().hasNext();
    }

    @Override
    public synchronized Event current() {
        // If there is no event ahead of the current event,
        // it triggers an error stating that there is no event in the next index.
        // If it exists, it returns the current event.
        if (!moveNext()) {
            throw new IllegalStateException("There is no next event.");
        } else {
            return _events.parallelStream().filter(predicate).findFirst().get();
        }
    }

    @Override
    public synchronized void remove() {
        // Instantiate a variable filtering the current event
        // and loop through each event and check if the event is in fact the one to be removed.
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
